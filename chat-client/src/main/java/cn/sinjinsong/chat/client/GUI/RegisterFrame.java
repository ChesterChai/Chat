package cn.sinjinsong.chat.client.GUI;

import cn.sinjinsong.chat.client.actionListener.LoginListener;
import cn.sinjinsong.chat.client.actionListener.RegisterListener;

import javax.swing.*;
import java.awt.*;

public class RegisterFrame extends JFrame {
    private JFrame login;

    public RegisterFrame(){
        setTitle("注册界面");
        setSize(360,250);//只对顶级容器有效
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);//窗体关闭时结束程序
        setLocationRelativeTo(null);//居中
        setResizable(false);

        //选择布局类型，定义流式布局的对象,并且设置每个组件之间相隔
        FlowLayout fl=new FlowLayout(FlowLayout.CENTER,5,2);
        setLayout(fl);//设置顶级容器的布局为流式布局

        //设置格式大小
        Dimension dim2=new Dimension(70, 50);//标签的大小
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

        //添加“密码”标签
        JLabel labRepassword=new JLabel();
        labRepassword.setText("确认密码：");
        labRepassword.setPreferredSize(dim2);
        c.add(labRepassword);

        //添加密码输入框，并添加监控事件
        JPasswordField jrp=new JPasswordField();
        jrp.setPreferredSize(dim3);
        c.add(jrp);

        //添加登陆和注册按钮
        JButton registerButton=new JButton();
        registerButton.setText("注册");
        registerButton.setPreferredSize(dim4);
        c.add(registerButton);

        JButton cancelRegisterButton=new JButton();
        cancelRegisterButton.setText("取消");
        cancelRegisterButton.setPreferredSize(dim4);
        c.add(cancelRegisterButton);

        setVisible(true);

        //对当前窗体按钮添加监听方法
        registerButton.addActionListener(new RegisterListener(this, textname, jp, jrp));
        cancelRegisterButton.addActionListener((actionEvent) -> {
            new LoginFrame();
            this.dispose();
        });
    }
}
