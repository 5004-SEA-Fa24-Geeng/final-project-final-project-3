import controller.MainController;

/**
 * Main class to start the application
 * @author Yuchen Huang
 * @version 1.0
 * @since 2025-04-14
 */
public class Main {
    public static void main(String[] args) {
        // use SwingUtilities to ensure running on the event dispatch thread
        javax.swing.SwingUtilities.invokeLater(() -> {
            MainController mainController = new MainController();
            mainController.start();
        });
    }
} 