package view;

import javax.swing.*;
import java.awt.*;
import java.io.File;

/**
 * Main application window for the Character Management System.
 * Assembles and lays out all primary panels including sorting, filtering, listing, and wish list.
 */
public class MainFrame extends JFrame {
    /** Panel for sorting character list. */
    private SortPanel sortPanel;
    /** Panel for displaying the wish list of characters. */
    private WishListPanel wishListPanel;
    /** Panel for filtering character list based on criteria. */
    private FilterPanel filterPanel;
    /** Panel for showing the list of characters. */
    private CharacterListPanel characterListPanel;

    /**
     * Constructor of the main frame and initializes all UI components.
     */
    public MainFrame() {
        setTitle("Character Management System");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1200, 800);
        setLocationRelativeTo(null);
        initComponents();
    }

    /**
     * Initializes and lays out all components in the main application frame.
     */
    private void initComponents() {
        // create main panel
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBackground(new Color(255,240,247));

        // create upper panel
        JPanel upperPanel = new JPanel();
        upperPanel.setPreferredSize(new Dimension(0, 150));
        upperPanel.setBackground(new Color(255,240,247));
        upperPanel.setLayout(null);
        JLabel logoLabel = new JLabel("Celebrity Wishlist");
        try {
            Font fancyFont = Font.createFont(Font.TRUETYPE_FONT,
                    new File("src/main/resources/GreatVibes-Regular.ttf")).deriveFont(90f);

            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            ge.registerFont(fancyFont);

            logoLabel.setFont(fancyFont);
            logoLabel.setForeground(new Color(204, 0, 102)); // romantic red-pink
            logoLabel.setHorizontalAlignment(SwingConstants.CENTER);
            logoLabel.setPreferredSize(new Dimension(1000, 130));
        } catch (Exception e) {
            e.printStackTrace();
            logoLabel.setFont(new Font("Serif", Font.BOLD, 36)); // fallback
        }
        logoLabel.setBounds(200, 20, 800, 120);
        TitleHeartAnimationPanel heartOverlay = new TitleHeartAnimationPanel(getWidth(), 150); // matches upperPanel height
        heartOverlay.setBounds(0, 0, getWidth(), 150);
        upperPanel.setLayout(null); // allow absolute positioning
        upperPanel.add(logoLabel);  // keep existing label
        upperPanel.add(heartOverlay); // layer hearts on top
        upperPanel.add(logoLabel);

        // create left panel (filter)
        JPanel leftPanel = new JPanel(new BorderLayout());
        leftPanel.setPreferredSize(new Dimension(350, Integer.MAX_VALUE));
        leftPanel.setBackground(new Color(255,240,247));
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
        rightPanel.setBackground(new Color(255,242,247));
        rightPanel.setOpaque(true);
        rightPanel.setBorder(null);

        // create top panel (sort)
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setPreferredSize(new Dimension(0, 30));
        sortPanel = new SortPanel();
        topPanel.add(sortPanel, BorderLayout.WEST);

        // create middle panel (character list)
        characterListPanel = new CharacterListPanel(sortPanel);
        JScrollPane listScrollPane = new JScrollPane(characterListPanel);
        listScrollPane.setPreferredSize(new Dimension(600, 0));
        listScrollPane.setBorder(null);

        // create right panel (wish list)
        wishListPanel = new WishListPanel();

        // assemble right panel
        rightPanel.add(topPanel, BorderLayout.NORTH);
        rightPanel.add(listScrollPane, BorderLayout.CENTER);
        rightPanel.add(wishListPanel, BorderLayout.EAST);

        // assemble main panel
        mainPanel.add(leftPanel, BorderLayout.WEST);
        mainPanel.add(rightPanel, BorderLayout.CENTER);
        mainPanel.add(upperPanel, BorderLayout.NORTH);

        // add main panel to window
        add(mainPanel);
    }

    /**
     * Returns the sort panel.
     *
     * @return the SortPanel instance
     */
    public SortPanel getSortPanel() {
        return sortPanel;
    }

    /**
     * Returns the wish list panel.
     *
     * @return the WishListPanel instance
     */
    public WishListPanel getWishListPanel() {
        return wishListPanel;
    }

    /**
     * Returns the filter panel.
     *
     * @return the FilterPanel instance
     */
    public FilterPanel getFilterPanel() {
        return filterPanel;
    }

    /**
     * Returns the character list panel.
     *
     * @return the CharacterListPanel instance
     */
    public CharacterListPanel getCharacterListPanel() {
        return characterListPanel;
    }

} 