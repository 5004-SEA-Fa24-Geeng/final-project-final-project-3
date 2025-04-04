package view;

import javax.swing.*;

/**
 * @author  Yuchen Huang
 * this program is for the main frame, which shows the GUI.
 */

public class MainFrame {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                // 创建主窗口
                JFrame frame = new JFrame("Celebrity Wishing List");
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                
                // 创建并添加Celebrities面板
                Celebrities celebritiesPanel = new Celebrities();
                frame.setContentPane(celebritiesPanel.getRootPanel());
                    
                // 设置窗口大小和位置
                frame.setSize(1200, 800);
                frame.setLocationRelativeTo(null);
                frame.setVisible(true);
            }
        });
    }
}
