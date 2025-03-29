/**
 * @author Yuchen
 * Menu Panel is the container to hold all menu components
 */

import javax.swing.*;
import java.awt.*;

public class MenuPanel extends JPanel {
    private static JDesktopPane contentPanel = new JDesktopPane();   // the default panel to show content
    // JPanel is a class in the Java Swing framework,
    // it serves as a generic container holds any number of components
    public MenuPanel() {
        JMenuBar menuBar = new JMenuBar();  // container for menubar
        this.setLayout(new BorderLayout());
        this.add(menuBar, BorderLayout.NORTH);  // set menuBar to be at the top of the frame

        this.add(contentPanel, BorderLayout.CENTER);
        contentPanel.removeAll();
        contentPanel.repaint(); // everytime we click the menu, content panel will repaint

        JMenu inputMenu = new JMenu("Files"); // input component
        menuBar.add(inputMenu);  // put input menu into menubar container


        JMenu table = new JMenu("Tools"); // Tools: plan to have (random generator/Find your celebrity avatar)
        menuBar.add(table);

        JMenu helpMenu = new JMenu("Help"); // Help: Program introduction / Github Link
        menuBar.add(helpMenu);
    }
}
