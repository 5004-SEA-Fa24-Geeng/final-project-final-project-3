package view;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MainFrameTest {

    @Test
    void getSortPanel() {
        MainFrame frame = new MainFrame();
        assertNotNull(frame.getSortPanel());
    }

    @Test
    void getWishListPanel() {
        MainFrame frame = new MainFrame();
        assertNotNull(frame.getWishListPanel());
    }

    @Test
    void getFilterPanel() {
        MainFrame frame = new MainFrame();
        assertNotNull(frame.getFilterPanel());
    }

    @Test
    void getCharacterListPanel() {
        MainFrame frame = new MainFrame();
        assertNotNull(frame.getCharacterListPanel());
    }
}