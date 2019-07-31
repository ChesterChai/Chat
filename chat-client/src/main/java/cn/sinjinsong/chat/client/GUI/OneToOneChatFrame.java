package cn.sinjinsong.chat.client.GUI;

import cn.sinjinsong.chat.client.ChatClient;
import cn.sinjinsong.chat.client.actionListener.OneToOneSendMassageListener;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class OneToOneChatFrame extends JFrame{

    //因为OneToOneChatFrame的创建需要有人给他发送第一条消息，firstMassage就是用来存放这第一条消息的
    //然后在创建了消息显示框后，将这第一条消息显示出来，如果是自己主动创建的话，直接将它设置为空字符串即可
    private String firstMassage;

    public OneToOneChatFrame(String userName, String firstMassage, int x, int y, int w, int h) {
        this.firstMassage = firstMassage;
        setTitle(userName);
        setBounds(x, y, w, h);
        setLayout(new BorderLayout());
        //setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                setVisible(false);
                ChatClient.oneToOneContentMap.remove(userName);
            }
        });
        setResizable(true);
        //消息显示框
        TextArea taContent = new TextArea();
        taContent.setEditable(false);
        taContent.setText(firstMassage);

        //消息输入框
        JPanel tfPanel = new JPanel();
        tfPanel.setLayout(new GridLayout(1, 1));
        tfPanel.setPreferredSize(new Dimension(0, 23));
        TextField tfText = new TextField();
        tfPanel.add(tfText);

        Container c = getContentPane();
        c.add(tfPanel, BorderLayout.SOUTH);
        c.add(taContent, BorderLayout.CENTER);

        tfText.addActionListener(new OneToOneSendMassageListener(userName, taContent));

        ChatClient.oneToOneContentMap.put(userName, taContent);
        setVisible(true);
    }
}
