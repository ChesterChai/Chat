package cn.sinjinsong.chat.client.GUI;

import cn.sinjinsong.chat.client.ChatClient;
import cn.sinjinsong.chat.client.actionListener.MainSendMassageListener;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class MainFrame extends JFrame {

    public MainFrame(int x, int y, int w, int h){
        TextField tfText = new TextField();
        TextArea taContent = new TextArea();
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
        taContent.setEditable(false);
        ChatClient.setTaContent(taContent);
        Container c = getContentPane();
        c.add(tfText, BorderLayout.SOUTH);
        c.add(taContent, BorderLayout.NORTH);
        tfText.addActionListener(new MainSendMassageListener());
        pack();
        setVisible(true);
    }

}
