package cn.sinjinsong.common.util;

import java.io.File;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.filechooser.FileSystemView;

public class SelectFilesAndDir {
    public static File select(){
        JFileChooser jfc=new JFileChooser();
        //设置当前路径为桌面路径,否则将我的文档作为默认路径
        FileSystemView fsv = FileSystemView .getFileSystemView();
        jfc.setCurrentDirectory(fsv.getHomeDirectory());
        //JFileChooser.FILES_AND_DIRECTORIES 选择路径和文件
        jfc.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES );
        //弹出的提示框的标题
        jfc.showDialog(new JLabel(), "确定");
        //用户选择的路径或文件
        File file=jfc.getSelectedFile();
        return file;
    }
}
