package view;

import javax.swing.*;
import java.awt.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import model.CelebritiesModel;
import controller.CelebritiesController;

/**
 * @author  Yuchen Huang
 * this program is for the main frame, which shows the GUI.
 */

public class MainFrame extends JFrame {
    private static final String IMAGE_CACHE_DIR = "image_cache";
    
    public MainFrame() {
        setTitle("Celebrity Wishlist");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1200, 800);
        setLocationRelativeTo(null);
        
        // 创建ObjectMapper
        ObjectMapper objectMapper = new ObjectMapper();
        
        // 创建Model
        CelebritiesModel model = new CelebritiesModel(objectMapper, IMAGE_CACHE_DIR);
        
        // 创建View
        CelebritiesView view = new CelebritiesView(objectMapper);
        
        // 创建Controller
        CelebritiesController controller = new CelebritiesController(model, view);
        
        // 加载初始数据
        controller.loadInitialData();
        
        // 设置内容面板
        setContentPane(view.getRootPanel());
    }

    public static void main(String[] args) {
        try {
            // 设置系统外观
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }

        SwingUtilities.invokeLater(() -> {
            try {
                // 创建主窗口
                MainFrame frame = new MainFrame();
                frame.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(null,
                    "Error initializing application: " + e.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
            }
        });
    }
}
