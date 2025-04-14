package view;

import javax.swing.*;
import java.awt.*;
import java.io.File;

/**
 * The main frame of the application.
 * 
 * This class extends JFrame and is the main window of the application.
 * It contains the main panel, which is a BorderLayout panel with a top panel,
 * a left panel, a right panel, and a bottom panel.
 */
public class MainFrame extends JFrame {
    private SortPanel sortPanel;
    private WishListPanel wishListPanel;
    private FilterPanel filterPanel;
    private CharacterListPanel characterListPanel;

    /**
     * Constructor for the MainFrame class.
     * 
     * This constructor initializes the main frame with a title, default close operation,
     * size, and location. It also initializes the components of the main frame.
     */
    public MainFrame() {
        setTitle("Character Management System");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1200, 800);
        setLocationRelativeTo(null);
        initComponents();
    }

    /**
     * Initializes the components of the main frame.
     * 
     * This method creates the main panel, upper panel, left panel, right panel,
     * and adds them to the main frame.
     */
    private void initComponents() {
        // create main panel
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBackground(new Color(252,236,222));

        // create upper panel
        JPanel upperPanel = new JPanel();
        upperPanel.setPreferredSize(new Dimension(0, 150));
        upperPanel.setBackground(new Color(252,236,222));
        JLabel logoLabel = new JLabel("Romantic Wishlist");
        try {
            Font fancyFont = Font.createFont(Font.TRUETYPE_FONT,
                    new File("src/main/resources/GreatVibes-Regular.ttf")).deriveFont(100f);

            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            ge.registerFont(fancyFont);

            logoLabel.setFont(fancyFont);
            logoLabel.setForeground(new Color(204, 0, 102)); // romantic red-pink
            logoLabel.setHorizontalAlignment(SwingConstants.CENTER);
        } catch (Exception e) {
            e.printStackTrace();
            logoLabel.setFont(new Font("Serif", Font.BOLD, 36)); // fallback
        }

        upperPanel.add(logoLabel);
        mainPanel.add(upperPanel, BorderLayout.NORTH);

        // create left panel (filter)
        JPanel leftPanel = new JPanel(new BorderLayout());
        leftPanel.setPreferredSize(new Dimension(350, Integer.MAX_VALUE));
        leftPanel.setBackground(new Color(252,236,222));
        leftPanel.setBorder(BorderFactory.createEmptyBorder(0, 15, 0, 0));
        filterPanel = new FilterPanel();
        JScrollPane filterScrollPane = new JScrollPane(filterPanel);
        filterScrollPane.setBorder(null);
        filterScrollPane.setOpaque(false);
        filterScrollPane.getViewport().setOpaque(false);
        filterScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        leftPanel.add(filterScrollPane, BorderLayout.CENTER);

        // create right panel
        JPanel rightPanel = new JPanel(new BorderLayout(10, 10));

        // create top panel (sort)
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setPreferredSize(new Dimension(0, 30));
        sortPanel = new SortPanel();
        topPanel.add(sortPanel, BorderLayout.WEST);

        // create middle panel (character list)
        characterListPanel = new CharacterListPanel(sortPanel);
        JScrollPane listScrollPane = new JScrollPane(characterListPanel);
        listScrollPane.setPreferredSize(new Dimension(600, 0));

        // create right panel (wish list)
        wishListPanel = new WishListPanel();

        // assemble right panel
        rightPanel.add(topPanel, BorderLayout.NORTH);
        rightPanel.add(listScrollPane, BorderLayout.CENTER);
        rightPanel.add(wishListPanel, BorderLayout.EAST);

        // assemble main panel
        mainPanel.add(leftPanel, BorderLayout.WEST);
        mainPanel.add(rightPanel, BorderLayout.CENTER);

        // add main panel to window
        add(mainPanel);
    }

    /**
     * Getter method for the sort panel.
     * 
     * This method returns the sort panel.
     * 
     * @return the sort panel
     */
    public SortPanel getSortPanel() {
        return sortPanel;
    }

    /**
     * Getter method for the wish list panel.
     * 
     * This method returns the wish list panel.
     * 
     * @return the wish list panel
     */
    public WishListPanel getWishListPanel() {
        return wishListPanel;
    }

    /**
     * Getter method for the filter panel.
     * 
     * This method returns the filter panel.
     * 
     * @return the filter panel
     */
    public FilterPanel getFilterPanel() {
        return filterPanel;
    }

    /**
     * Getter method for the character list panel.
     * 
     * This method returns the character list panel.
     * 
     * @return the character list panel
     */
    public CharacterListPanel getCharacterListPanel() {
        return characterListPanel;
    }
} 