package cn.sinjinsong.chat.client;

import cn.sinjinsong.chat.client.GUI.LoginFrame;
import cn.sinjinsong.chat.client.GUI.MainFrame;
import cn.sinjinsong.chat.client.GUI.RegisterFrame;
import cn.sinjinsong.common.domain.*;
import cn.sinjinsong.common.enumeration.MessageType;
import cn.sinjinsong.common.enumeration.ResponseCode;
import cn.sinjinsong.common.enumeration.TaskType;
import cn.sinjinsong.common.util.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.ConnectException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Iterator;

public class ChatClient extends Frame {

    public static final int DEFAULT_BUFFER_SIZE = 1024;

    public static SocketChannel clientChannel;
    private static TextArea taContent;
    private static JTextArea userStateText;
    private static ReceiverHandler listener;
    public static String username;
    public static boolean isLogin = false;
    private static boolean isConnected = false;
    private static Charset charset = StandardCharsets.UTF_8;
    private ByteBuffer buf;
    private Selector selector;


    public ChatClient(String name, int x, int y, int w, int h) {
        super(name);
        new MainFrame(x, y, w, h);
        initNetWork();
    }

    /**
     * 初始化网络模块
     */
    private void initNetWork() {
        try {
            selector = Selector.open();
            clientChannel = SocketChannel.open(new InetSocketAddress("127.0.0.1", 9000));
            //设置客户端为非阻塞模式
            clientChannel.configureBlocking(false);
            clientChannel.register(selector, SelectionKey.OP_READ);
            buf = ByteBuffer.allocate(DEFAULT_BUFFER_SIZE);
            new LoginFrame();
            isConnected = true;
        } catch (ConnectException e) {
            JOptionPane.showMessageDialog(this, "无法连接到服务器");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void launch() {
        this.listener = new ReceiverHandler();
        new Thread(listener).start();
    }

    public static void disConnect() {
        try {
            logout();
            if (!isConnected) {
                return;
            }
            listener.shutdown();
            //如果发送消息后马上断开连接，那么消息可能无法送达
            Thread.sleep(500);
            clientChannel.socket().close();
            clientChannel.close();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private static void logout() {
        if (!isLogin) {
            return;
        }
        System.out.println("客户端发送下线请求");
        Message message = new Message(
                MessageHeader.builder()
                        .type(MessageType.LOGOUT)
                        .sender(username)
                        .timestamp(System.currentTimeMillis())
                        .build(), null);
        try {
            clientChannel.write(ByteBuffer.wrap(ProtoStuffUtil.serialize(message)));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 用于接收信息的线程
     */
    private class ReceiverHandler implements Runnable {
        private volatile boolean connected = true;

        public void shutdown() {
            connected = false;
        }

        public void run() {
            try {
                while (connected) {
                    int size = 0;
                    selector.select();
                    for (Iterator<SelectionKey> it = selector.selectedKeys().iterator(); it.hasNext(); ) {
                        SelectionKey selectionKey = it.next();
                        it.remove();
                        if (selectionKey.isReadable()) {
                            ByteArrayOutputStream baos = new ByteArrayOutputStream();
                            while ((size = clientChannel.read(buf)) > 0) {
                                buf.flip();
                                baos.write(buf.array(), 0, size);
                                buf.clear();
                            }
                            byte[] bytes = baos.toByteArray();
                            baos.close();
                            Response response = ProtoStuffUtil.deserialize(bytes, Response.class);
                            handleResponse(response);
                        }
                    }
                }
            } catch (IOException e) {
                JOptionPane.showMessageDialog(null, "服务器关闭，请重新尝试连接");
                isLogin = false;
            }
        }

        private void handleResponse(Response response) {
            System.out.println(response);
            ResponseHeader header = response.getHeader();
            switch (header.getType()) {
                case PROMPT:
                    String info = new String(response.getBody(), charset);
                    JOptionPane.showMessageDialog(ChatClient.this, info);
                    if (header.getResponseCode() != null) {
                        ResponseCode code = ResponseCode.fromCode(header.getResponseCode());
                        switch (code){
                            case LOGIN_SUCCESS:
                                isLogin = true;
                                System.out.println("登陆成功");
                                break;
                            case LOGOUT_SUCCESS:
                                System.out.println("下线成功");
                                break;
                            case LOGIN_FAILURE:
                                System.out.println("登陆失败");
                                new LoginFrame();
                                break;
                            case REGISTER_FAILURE:
                                System.out.println("注册失败");
                                new RegisterFrame();
                                break;
                            case REGISTER_SUCCESS:
                                new LoginFrame();
                                System.out.println("注册成功");
                                break;
                        }
                    }
                    break;
                case SERVERMASSAGE:
                    if (header.getResponseCode() != null) {
                        ResponseCode code = ResponseCode.fromCode(header.getResponseCode());
                        switch (code){
                            case USER_STATE:
                                String userOnline = new String(response.getBody(), charset);
                                userStateText.setText(userOnline);
                                break;
                        }
                    }
                    break;
                case NORMAL:
                    String content = formatMessage(taContent.getText(), response);
                    taContent.setText(content);
                    taContent.setCaretPosition(content.length());
                    break;
                case FILE:
                    try {

                        File filePath = SelectFilesAndDir.select();
                        String path = filePath.getAbsolutePath() + JOptionPane.showInputDialog("请输入保存文件名");
                        byte[] buf = response.getBody();
                        FileUtil.save(path, buf);
                        if(path.endsWith("jpg")){
                            //显示该图片
                            ShowPictureDialog.show(ChatClient.this, "图片", false, path);
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                default:
                    break;
            }
        }

        /**
         * 将原来客户端界面上的消息和现在接收到的消息一起合成一个字符串
         * @param originalText
         * @param response
         * @return
         */
        private String formatMessage(String originalText, Response response) {
            ResponseHeader header = response.getHeader();
            StringBuilder sb = new StringBuilder();
            sb.append(originalText)
                    .append(header.getSender())
                    .append(": ")
                    .append(new String(response.getBody(), charset))
                    .append("    ")
                    .append(DateTimeUtil.formatLocalDateTime(header.getTimestamp()))
                    .append("\n");
            return sb.toString();
        }
    }

    public static void setUsername(String un){
        username = un;
    }

    public static void setTaContent(TextArea tc){
        taContent = tc;
    }

    public static void setUserStateText(JTextArea ta){
        userStateText = ta;
    }

    public static void main(String[] args) {
        System.out.println("Initialing...");
        ChatClient client = new ChatClient("Client", 200, 200, 600, 400);
        client.launch();
    }
}

