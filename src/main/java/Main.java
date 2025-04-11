import controller.MainController;

public class Main {
    public static void main(String[] args) {
        // 使用SwingUtilities确保在事件调度线程中运行
        javax.swing.SwingUtilities.invokeLater(() -> {
            MainController mainController = new MainController();
            mainController.start();
        });
    }
} 