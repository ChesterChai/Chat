package cn.sinjinsong.chat.client.GUI;

import cn.sinjinsong.chat.client.ChatClient;
import cn.sinjinsong.chat.client.actionListener.MainSendMassageListener;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class MainFrame extends JFrame {

    public MainFrame(int x, int y, int w, int h){
        setBounds(x, y, w, h);
        setLayout(new BorderLayout());
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                setVisible(false);
                ChatClient.disConnect();
                System.exit(0);
            }
        });
        setResizable(false);

        //消息显示框
        TextArea taContent = new TextArea();
        taContent.setEditable(false);

        //消息输入框
        JPanel tfPanel = new JPanel();
        tfPanel.setLayout(new GridLayout(1, 1));
        tfPanel.setPreferredSize(new Dimension(0, 23));
        TextField tfText = new TextField();
        tfPanel.add(tfText);

        //用户在线和用户朋友显示框
        JPanel userPanel = new JPanel();
        userPanel.setPreferredSize(new Dimension(100, 0));
        userPanel.setLayout(null);

        //在线用户标签
        JLabel onlineUserLab=new JLabel();
        onlineUserLab.setBounds(0, 0, 75, 30);
        onlineUserLab.setText("在线用户：");
        userPanel.add(onlineUserLab);

        //在线用户列表
        JTextArea onlineUserText = new JTextArea();
        JScrollPane onlineUserScrollpane = new JScrollPane();
        onlineUserScrollpane.setViewportView(onlineUserText);
        onlineUserText.setEditable(false);
        onlineUserScrollpane.setBounds(0, 30, 100, 150);
        userPanel.add(onlineUserScrollpane);

        //所有用户标签
        JLabel allUserLab=new JLabel();
        allUserLab.setBounds(0, 180, 75, 30);
        allUserLab.setText("所有用户：");
        userPanel.add(allUserLab);

        //所有用户列表
        JTextArea allUserText = new JTextArea();
        JScrollPane allUserScrollpane = new JScrollPane();
        allUserScrollpane.setViewportView(allUserText);
        allUserText.setEditable(false);
        allUserScrollpane.setBounds(0, 210, 100, 150);
        userPanel.add(allUserScrollpane);

        ChatClient.setUserOnlineText(onlineUserText);
        ChatClient.setAllUserText(allUserText);

        Container c = getContentPane();
        c.add(tfPanel, BorderLayout.SOUTH);
        c.add(taContent, BorderLayout.CENTER);
        c.add(userPanel, BorderLayout.EAST);


        tfText.addActionListener(new MainSendMassageListener());

        //pack();
        setVisible(true);
        ChatClient.setTaContent(taContent);
    }

}
