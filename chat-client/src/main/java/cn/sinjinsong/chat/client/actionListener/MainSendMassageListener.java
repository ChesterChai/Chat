package cn.sinjinsong.chat.client.actionListener;

import cn.sinjinsong.chat.client.ChatClient;
import cn.sinjinsong.common.domain.Message;
import cn.sinjinsong.common.domain.MessageHeader;
import cn.sinjinsong.common.domain.TaskDescription;
import cn.sinjinsong.common.enumeration.MessageType;
import cn.sinjinsong.common.enumeration.TaskType;
import cn.sinjinsong.common.util.ProtoStuffUtil;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

public class MainSendMassageListener implements ActionListener {
    private Charset charset = StandardCharsets.UTF_8;

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        TextField tfText = (TextField) actionEvent.getSource();
        String content = tfText.getText().trim();
        tfText.setText("");
        if (!ChatClient.isLogin) {
            JOptionPane.showMessageDialog(null, "尚未登录");
            return;
        }
        try {
            Message message;
            //普通模式
            if (content.startsWith("@")) {
                String[] slices = content.split(":");
                String receiver = slices[0].substring(1);
                message = new Message(
                        MessageHeader.builder()
                                .type(MessageType.NORMAL)
                                .sender(ChatClient.username)
                                .receiver(receiver)
                                .timestamp(System.currentTimeMillis())
                                .build(), slices[1].getBytes(charset));
            } else if (content.startsWith("task")) {
                String info = content.substring(content.indexOf('.') + 1);
                int split = info.indexOf(':');
                TaskDescription taskDescription = new TaskDescription(TaskType.valueOf(info.substring(0,split).toUpperCase()), info.substring(split+1));
                //处理不同的Task类型
                message = new Message(
                        MessageHeader.builder()
                                .type(MessageType.TASK)
                                .sender(ChatClient.username)
                                .timestamp(System.currentTimeMillis())
                                .build(), ProtoStuffUtil.serialize(taskDescription));
            } else {
                //广播模式
                message = new Message(
                        MessageHeader.builder()
                                .type(MessageType.BROADCAST)
                                .sender(ChatClient.username)
                                .timestamp(System.currentTimeMillis())
                                .build(), content.getBytes(charset));
            }
            System.out.println(message);
            ChatClient.clientChannel.write(ByteBuffer.wrap(ProtoStuffUtil.serialize(message)));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
