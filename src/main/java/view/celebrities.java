package view;

import javax.swing.*;
import java.awt.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import model.CelebritiesModel;
import controller.CelebritiesController;

/**
 * celebrities
 * this class is used to display the celebrities in the list
 */
public class Celebrities extends JFrame {
    private static final String TITLE = "Celebrity Wishlist";
    private static final int WIDTH = 1200;
    private static final int HEIGHT = 800;
    private static final String IMAGE_CACHE_DIR = "image_cache";

    /**
     * constructor
     */
    public Celebrities() {
        // set window properties
        setTitle(TITLE);
        setSize(WIDTH, HEIGHT);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        
        // create ObjectMapper
        ObjectMapper objectMapper = new ObjectMapper();
        
        // create Model
        CelebritiesModel model = new CelebritiesModel(objectMapper, IMAGE_CACHE_DIR);
        
        // create View
        CelebritiesView view = new CelebritiesView(objectMapper);
        
        // create Controller
        CelebritiesController controller = new CelebritiesController(model, view);
        
        // load initial data
        controller.loadInitialData();
        
        // add main panel to window
        add(view.getRootPanel());
    }

    public static void main(String[] args) {
        // use SwingUtilities.invokeLater to ensure running on the event dispatch thread
        SwingUtilities.invokeLater(() -> {
            Celebrities app = new Celebrities();
            app.setVisible(true);
        });
    }
}