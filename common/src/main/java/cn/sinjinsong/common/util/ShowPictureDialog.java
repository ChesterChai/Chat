package cn.sinjinsong.common.util;

import javax.swing.*;
import java.awt.*;

import static javax.swing.WindowConstants.DISPOSE_ON_CLOSE;

public class ShowPictureDialog {

    public static void show(Frame owner, String title, boolean modal,
                         String path) {
        JDialog jl = new JDialog(owner, title, modal);
        ImageIcon icon = new ImageIcon(path);
        JLabel lbPhoto = new JLabel(icon);
        Container c = jl.getContentPane();
        c.add(lbPhoto);
        jl.setSize(icon.getIconWidth(), icon.getIconHeight());
        jl.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        jl.setVisible(true);
    }
}
