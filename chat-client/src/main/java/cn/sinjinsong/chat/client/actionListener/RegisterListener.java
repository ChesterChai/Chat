package cn.sinjinsong.chat.client.actionListener;

import cn.sinjinsong.chat.client.ChatClient;
import cn.sinjinsong.common.domain.Message;
import cn.sinjinsong.common.domain.MessageHeader;
import cn.sinjinsong.common.enumeration.MessageType;
import cn.sinjinsong.common.util.ProtoStuffUtil;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

public class RegisterListener implements ActionListener {
    private Charset charset = StandardCharsets.UTF_8;
    private JTextField jt;//账号输入框对象
    private JPasswordField jp;//密码输入框对象

    private JFrame login;//定义一个窗体对象
    public RegisterListener(JFrame login, JTextField jt, JPasswordField jp) {
        this.login=login;//获取登录界面
        this.jt=jt;//获取登录界面中的账号输入框对象
        this.jp=jp;//获取登录界面中的密码输入框对象
    }

    public void actionPerformed(ActionEvent actionEvent) {
        String username = jt.getText();
        String password = jp.getText();

        Message message = new Message(
                MessageHeader.builder()
                        .type(MessageType.REGISTER)
                        .sender(username)
                        .timestamp(System.currentTimeMillis())
                        .build(), password.getBytes(charset));
        try {
            ChatClient.clientChannel.write(ByteBuffer.wrap(ProtoStuffUtil.serialize(message)));
        } catch (IOException e) {
            e.printStackTrace();
        }
        ChatClient.setUsername(username);
        // 通过我们获取的登录界面对象，用dispose方法关闭它
        login.dispose();
    }
}
