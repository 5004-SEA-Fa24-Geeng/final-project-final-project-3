package view;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for the MainFrame class.
 * 
 * This class contains unit tests for the MainFrame class.
 * It tests the getSortPanel, getWishListPanel, getFilterPanel, and getCharacterListPanel methods.
 */
class MainFrameTest {

    @Test
    // test the getSortPanel method
    void getSortPanel() {
        MainFrame frame = new MainFrame();
        assertNotNull(frame.getSortPanel());
    }

    @Test
    // test the getWishListPanel method
    void getWishListPanel() {
        MainFrame frame = new MainFrame();
        assertNotNull(frame.getWishListPanel());
    }

    @Test
    // test the getFilterPanel method
    void getFilterPanel() {
        MainFrame frame = new MainFrame();
        assertNotNull(frame.getFilterPanel());
    }

    @Test
    // test the getCharacterListPanel method
    void getCharacterListPanel() {
        MainFrame frame = new MainFrame();
        assertNotNull(frame.getCharacterListPanel());
    }
}