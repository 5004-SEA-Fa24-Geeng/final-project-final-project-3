import javax.swing.*;

/**
 * @author  Yuchen Huang
 * this program is for the main frame, which shows the GUI.
 */

public class MainFrame {
    public static void main(String[] args) {
        JFrame frame = new JFrame("Celebrity Wishing List");
        frame.setSize(900, 600);       // set frame size
        frame.setLocationRelativeTo(null);          // set location to be in the center
        frame.setContentPane(new MenuPanel());      // show menuPanel in the frame
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  // click X button will exit
    }
}
