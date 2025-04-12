import controller.MainController;

public class Main {
    public static void main(String[] args) {
        // use SwingUtilities to ensure running on the event dispatch thread
        javax.swing.SwingUtilities.invokeLater(() -> {
            MainController mainController = new MainController();
            mainController.start();
        });
    }
} 