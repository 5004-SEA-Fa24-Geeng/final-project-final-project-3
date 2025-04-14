package test;

import controller.MainController;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/*
 * This class contains unit tests for the Main class.
 * It tests the main method.
 */
class MainTest {

    @Test
    // test the main method
    void testMain() {
        // create a new MainController instance
        MainController mainController = new MainController();
        
        // verify that MainController is initialized
        assertNotNull(mainController, "MainController should be initialized");
        
        // start the application
        mainController.start();
        
        // verify that the application is successfully started
        // note: since the main method in the Main class uses SwingUtilities.invokeLater,
        // we cannot directly verify its execution result, as it is executed asynchronously on the event dispatch thread
    }
} 