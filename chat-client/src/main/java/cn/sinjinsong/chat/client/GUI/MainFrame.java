package cn.sinjinsong.chat.client.GUI;

import cn.sinjinsong.chat.client.ChatClient;
import cn.sinjinsong.chat.client.actionListener.MainSendMassageListener;
import org.springframework.orm.jpa.JpaDialect;

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

        //消息显示框
        TextArea taContent = new TextArea();
        taContent.setEditable(false);

        //消息输入框
        JPanel tfPanel = new JPanel();
        tfPanel.setLayout(new GridLayout(1, 1));
        tfPanel.setPreferredSize(new Dimension(0, 23));
        TextField tfText = new TextField();
        tfPanel.add(tfText);

        //用户显示框
        JPanel userPanel = new JPanel();
        userPanel.setPreferredSize(new Dimension(100, 0));
        userPanel.setLayout(new GridLayout(1, 1));
        JScrollPane scrollpane = new JScrollPane();
        JTextArea userStateText = new JTextArea();
        scrollpane.setViewportView(userStateText);
        userStateText.setEditable(false);
        userPanel.add(scrollpane);
        ChatClient.setUserStateText(userStateText);



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
