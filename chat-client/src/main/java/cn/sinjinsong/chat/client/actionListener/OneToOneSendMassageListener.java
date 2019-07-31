package cn.sinjinsong.chat.client.actionListener;

import cn.sinjinsong.chat.client.ChatClient;
import cn.sinjinsong.chat.client.GUI.OneToOneChatFrame;
import cn.sinjinsong.common.domain.*;
import cn.sinjinsong.common.enumeration.MessageType;
import cn.sinjinsong.common.enumeration.TaskType;
import cn.sinjinsong.common.util.DateTimeUtil;
import cn.sinjinsong.common.util.ProtoStuffUtil;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

public class OneToOneSendMassageListener implements ActionListener {
    private Charset charset = StandardCharsets.UTF_8;
    private String receiver;
    private TextArea massageShowText;

    public OneToOneSendMassageListener(String receiver, TextArea massageShowText){
        this.receiver = receiver;
        this.massageShowText = massageShowText;
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        TextField tfText = (TextField) actionEvent.getSource();
        String content = tfText.getText().trim();
        tfText.setText("");
        try {
            Message message;
            message = new Message(
                    MessageHeader.builder()
                            .type(MessageType.NORMAL)
                            .sender(ChatClient.username)
                            .receiver(receiver)
                            .timestamp(System.currentTimeMillis())
                            .build(), content.getBytes(charset));
            System.out.println(message);
            ChatClient.clientChannel.write(ByteBuffer.wrap(ProtoStuffUtil.serialize(message)));
            String oneToOneContent = formatMessage(massageShowText.getText(), message);
            massageShowText.setText(oneToOneContent);
            massageShowText.setCaretPosition(oneToOneContent.length());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 将原来单聊界面上的消息和自己发送的消息一起合成一个字符串
     * @param originalText
     * @param message
     * @return
     */
    private String formatMessage(String originalText, Message message) {
        MessageHeader header = message.getHeader();
        StringBuilder sb = new StringBuilder();
        sb.append(originalText)
                .append(header.getSender())
                .append(": ")
                .append(new String(message.getBody(), charset))
                .append("    ")
                .append(DateTimeUtil.formatLocalDateTime(header.getTimestamp()))
                .append("\n");
        return sb.toString();
    }
}
