package cn.sinjinsong.chat.client.GUI;

import cn.sinjinsong.chat.client.ChatClient;
import cn.sinjinsong.chat.client.actionListener.LoginListener;
import cn.sinjinsong.chat.client.actionListener.RegisterListener;

import javax.swing.*;
import java.awt.*;

public class LoginFrame extends JFrame {

    public LoginFrame(){
        setTitle("登录界面");
        setSize(340,200);//只对顶级容器有效
        setDefaultCloseOperation(3);//窗体关闭时结束程序
        setLocationRelativeTo(null);//居中
        setResizable(false);

        //选择布局类型，定义流式布局的对象,并且设置每个组件之间相隔5cm
        FlowLayout fl=new FlowLayout(FlowLayout.CENTER,4,2);
        setLayout(fl);//设置顶级容器的布局为流式布局

        //设置格式大小
        Dimension dim2=new Dimension(50, 50);//标签的大小
        Dimension dim3=new Dimension(250, 30);//输入框的大小
        Dimension dim4=new Dimension(100, 40);//按钮的大小

        Container c = getContentPane();

        //添加组件
        //添加“账号”标签
        JLabel labname=new JLabel();
        labname.setText("账号：");
        labname.setPreferredSize(dim2);
        c.add(labname);

        //添加账号输入框，并添加监控事件
        JTextField textname=new JTextField();
        textname.setPreferredSize(dim3);
        c.add(textname);

        //添加“密码”标签
        JLabel labpassword=new JLabel();
        labpassword.setText("密码：");
        labpassword.setPreferredSize(dim2);
        c.add(labpassword);

        //添加密码输入框，并添加监控事件
        JPasswordField jp=new JPasswordField();
        jp.setPreferredSize(dim3);
        c.add(jp);

        //添加登陆和注册按钮
        javax.swing.JButton loginButton=new javax.swing.JButton();
        loginButton.setText("登录");
        loginButton.setPreferredSize(dim4);
        c.add(loginButton);

        javax.swing.JButton registerButton=new javax.swing.JButton();
        registerButton.setText("注册");
        registerButton.setPreferredSize(dim4);
        c.add(registerButton);

        setVisible(true);

        //对当前窗体添加监听方法
        loginButton.addActionListener(new LoginListener(this,textname,jp));//监控按钮
        registerButton.addActionListener(new RegisterListener(this,textname,jp));
    }
}
