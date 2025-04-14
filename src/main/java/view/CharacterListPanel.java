package view;

import model.CharacterRecord;
import javax.swing.*;
import java.awt.*;
import java.util.List;

import util.ImageCache;  // cache the images to reduce loading time

/**
 * The CharacterListPanel class is responsible for displaying a list of characters in a scrollable panel.
 * It provides functionality to add characters to the wishlist and update the count of characters.
 */
public class CharacterListPanel extends JPanel {
    /** Panel that holds the list of character panels. */
    private final JPanel contentPanel = new JPanel();
    /** Listener for handling "add to wishlist" actions. */
    private AddToWishListListener addToWishListListener;
    /** Panel used to display sorting controls and character count. */
    private SortPanel sortPanel;

    /**
     * Constructs a CharacterListPanel with the given sort panel.
     * @param sortPanel the panel for sorting and displaying character count
     */
    public CharacterListPanel(SortPanel sortPanel) {
        this.sortPanel = sortPanel;
        setLayout(new BorderLayout());
        setPreferredSize(new Dimension(400, 138));
        setMinimumSize(new Dimension(400, 138));
        setMaximumSize(new Dimension(400, 138));
        setBackground(new Color(255,240,247));
        initComponents();
    }

    /**
     * Sets the listener for adding characters to the wishlist.
     * @param listener the listener to set
     */
    public void setAddToWishListListener(AddToWishListListener listener) {
        System.out.println("Setting addToWishListListener: " + (listener != null));
        this.addToWishListListener = listener;
    }

    /**
     * Checks if the addToWishListListener is set.
     * @return true if the listener is set, false otherwise
     */
    public boolean isAddToWishListListenerSet() {
        return addToWishListListener != null;
    }

    /**
     * Initializes the components of the panel.
     */
    private void initComponents() {
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
        contentPanel.setBackground(new Color(255,240,247));
        JScrollPane scrollPane = new JScrollPane(contentPanel);
        add(scrollPane, BorderLayout.CENTER);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.getViewport().setBackground(new Color(255,240,247)); // ✅ ADD THIS
        scrollPane.setBorder(BorderFactory.createLineBorder(new Color(204, 0, 102), 2));
    }

    /**
     * Sets the list of characters to display in the panel.
     * @param characters the list of characters to display
     */
    public void setCharacterList(List<CharacterRecord> characters) {
        contentPanel.removeAll();
        for (CharacterRecord character : characters) {
            addCharacterPanel(character);
        }
        contentPanel.revalidate();
        contentPanel.repaint();
        updateCount();
    }

    /**
     * Adds a character panel to the content panel.
     * @param character the character to add
     */
    private void addCharacterPanel(CharacterRecord character) {
        JPanel panel = new JPanel(new BorderLayout(0, 0));
        panel.setBackground(new Color(255,240,247));
        panel.setBorder(BorderFactory.createEmptyBorder(2, 2, 2, 2));

        // add the image panel to the left
        panel.add(createImagePanel(character), BorderLayout.WEST);

        // add the info panel to the center
        panel.add(createInfoPanel(character), BorderLayout.CENTER);

        // add the button panel to the right
        panel.add(createButtonPanel(character), BorderLayout.EAST);

        // add the separator line
        panel.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.LIGHT_GRAY));

        contentPanel.add(panel);
    }

    /**
     * create the image panel on the left
     * @param character the character information
     * @return the image panel
     */
    private JPanel createImagePanel(CharacterRecord character) {
        JPanel imagePanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        imagePanel.setBackground(new Color(255,240,247)); // ✅
        String imageUrl = "https://image.tmdb.org/t/p/w92" + character.getProfile();
        ImageIcon icon = ImageCache.getImage(imageUrl);
        if (icon != null) {
            imagePanel.add(new JLabel(icon));
        } else {
            imagePanel.add(new JLabel("No Image"));
        }

        // middle info panel
        JPanel infoPanel = new JPanel();
        infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS));
        infoPanel.setPreferredSize(new Dimension(200, 92));
        infoPanel.setMinimumSize(new Dimension(200, 92));
        infoPanel.setMaximumSize(new Dimension(200, 92));
        infoPanel.setBackground(Color.WHITE);
        infoPanel.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 0));
        
        JLabel nameLabel = new JLabel("Name: " + character.getName());
        JLabel ageLabel = new JLabel("Age: " + character.getAge());
        
        // create gender label
        JLabel genderLabel = new JLabel();
        String genderText = "Gender: " + getGenderString(character.getGender());
        ImageIcon genderIcon = null;
        
        // set gender icon based on gender
        switch (character.getGender()) {
            case 1:
                genderIcon = new ImageIcon("src/main/resources/female.png");
                break;
            case 2:
                genderIcon = new ImageIcon("src/main/resources/male.png");
                break;
            case 3:
                genderIcon = new ImageIcon("src/main/resources/other.png");
                break;
        }
        
        // adjust icon size
        if (genderIcon != null) {
            Image image = genderIcon.getImage();
            Image newimg = image.getScaledInstance(12, 12, Image.SCALE_SMOOTH);
            genderIcon = new ImageIcon(newimg);
            genderLabel.setIcon(genderIcon);
        }
        
        // set text and icon position
        genderLabel.setText(genderText);
        genderLabel.setHorizontalTextPosition(SwingConstants.LEFT);
        genderLabel.setIconTextGap(5); // icon and text gap
        
        JLabel zodiacLabel = new JLabel("<html>Zodiac: " + getZodiacSign(character.getZodiacSign()) + "</html>");
        JLabel occupationLabel = new JLabel("Occupation: " + character.getOccupation());
        
        // set font size and alignment
        Font labelFont = new Font("Arial", Font.PLAIN, 14);
        nameLabel.setFont(labelFont);
        ageLabel.setFont(labelFont);
        genderLabel.setFont(labelFont);
        zodiacLabel.setFont(labelFont);
        occupationLabel.setFont(labelFont);
        
        // set alignment
        nameLabel.setHorizontalAlignment(SwingConstants.LEFT);
        ageLabel.setHorizontalAlignment(SwingConstants.LEFT);
        genderLabel.setHorizontalAlignment(SwingConstants.LEFT);
        zodiacLabel.setHorizontalAlignment(SwingConstants.LEFT);
        occupationLabel.setHorizontalAlignment(SwingConstants.LEFT);
        
        // set each label's fixed height
        Dimension labelSize = new Dimension(200, 15);
        nameLabel.setPreferredSize(labelSize);
        ageLabel.setPreferredSize(labelSize);
        genderLabel.setPreferredSize(labelSize);
        zodiacLabel.setPreferredSize(labelSize);
        occupationLabel.setPreferredSize(labelSize);

        // set label margin
        nameLabel.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
        ageLabel.setBorder(BorderFactory.createEmptyBorder(1, 0, 10, 0));
        genderLabel.setBorder(BorderFactory.createEmptyBorder(1, 0, 10, 0));
        zodiacLabel.setBorder(BorderFactory.createEmptyBorder(1, 0, 10, 0));
        occupationLabel.setBorder(BorderFactory.createEmptyBorder(1, 0, 1, 0));
        
        infoPanel.add(nameLabel);
        infoPanel.add(ageLabel);
        infoPanel.add(genderLabel);
        infoPanel.add(zodiacLabel);
        infoPanel.add(occupationLabel);

        // right button panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 10)); // Aligns top-ish
        buttonPanel.setPreferredSize(new Dimension(30, 92));
        buttonPanel.setMinimumSize(new Dimension(30, 92));
        buttonPanel.setMaximumSize(new Dimension(30, 92));
        buttonPanel.setBackground(Color.WHITE);

        ImageIcon heartIcon = new ImageIcon("src/main/resources/heart.png");
        Image scaled = heartIcon.getImage().getScaledInstance(24, 24, Image.SCALE_SMOOTH);
        JButton addButton = new JButton(new ImageIcon(scaled));

        addButton.setBorderPainted(false);
        addButton.setContentAreaFilled(false);
        addButton.setFocusPainted(false);
        addButton.setOpaque(true);
        addButton.setToolTipText("Add to Wishlist");
        addButton.setFont(new Font("Arial", Font.BOLD, 20));
        addButton.setPreferredSize(new Dimension(25, 25));
        addButton.setMargin(new Insets(0, 0, 0, 10));
        addButton.setFocusable(false);
        addButton.setBackground(Color.WHITE);
        addButton.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(200, 200, 200)),
                BorderFactory.createEmptyBorder(0, 0, 0, 0)
        ));
        addButton.setCursor(new Cursor(Cursor.HAND_CURSOR));

        addButton.addActionListener(e -> {
            System.out.println("Button clicked for character: " + character.getName());
            if (addToWishListListener != null) {
                System.out.println("Adding character with ID: " + character.getId());
                addToWishListListener.onAdd(character.getId());
            } else {
                System.out.println("addToWishListListener is null");
            }
        });
        buttonPanel.add(addButton);

        // add panel to main panel
        panel.add(imagePanel, BorderLayout.WEST);
        panel.add(infoPanel, BorderLayout.CENTER);
        panel.add(buttonPanel, BorderLayout.EAST);

        // add separator
        panel.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.LIGHT_GRAY));

        contentPanel.add(panel);
    }

    /**
     * Gets the gender string for a given gender code.
     * @param gender the gender code
     * @return the gender string
     */
    private String getGenderString(int gender) {
        switch (gender) {
            case 1: return "Female";
            case 2: return "Male";
            case 3: return "Non-binary";
            default: return "Unknown";
        }
    }

    /**
     * Gets the zodiac sign for a given zodiac sign.
     * @param zodiac the zodiac sign
     * @return the zodiac sign
     */
    private String getZodiacSign(String zodiac) {
        switch (zodiac.toLowerCase()) {
            case "aries": return "♈ " + zodiac;
            case "taurus": return "♉ " + zodiac;
            case "gemini": return "♊ " + zodiac;
            case "cancer": return "♋ " + zodiac;
            case "leo": return "♌ " + zodiac;
            case "virgo": return "♍ " + zodiac;
            case "libra": return "♎ " + zodiac;
            case "scorpio": return "♏ " + zodiac;
            case "sagittarius": return "♐ " + zodiac;
            case "capricorn": return "♑ " + zodiac;
            case "aquarius": return "♒ " + zodiac;
            case "pisces": return "♓ " + zodiac;
            default: return zodiac;
        }
    }

    /**
     * Updates the count of characters in the panel.
     */
    private void updateCount() {
        if (sortPanel != null) {
            sortPanel.setCount(contentPanel.getComponentCount());
        }
    }

    /**
     * Updates the view of the panel.
     */
    public void updateView() {
        updateCount();
        contentPanel.repaint();
    }

    /**
     * Adds a character to the wishlist.
     */
    public interface AddToWishListListener {
        void onAdd(int characterId);
    }
} 