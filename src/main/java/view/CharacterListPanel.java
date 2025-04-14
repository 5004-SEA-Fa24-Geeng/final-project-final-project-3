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
        scrollPane.getViewport().setBackground(new Color(255,240,247));
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
        imagePanel.setBackground(Color.WHITE);
        
        String imageUrl = "https://image.tmdb.org/t/p/w92" + character.getProfile();
        ImageIcon icon = ImageCache.getImage(imageUrl);
        if (icon != null) {
            imagePanel.add(new JLabel(icon));
        } else {
            imagePanel.add(new JLabel("No Image"));
        }
        
        return imagePanel;
    }

    /**
     * create the info panel in the center
     * @param character the character information
     * @return the info panel
     */
    private JPanel createInfoPanel(CharacterRecord character) {
        JPanel infoPanel = new JPanel();
        infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS));
        infoPanel.setPreferredSize(new Dimension(200, 92));
        infoPanel.setMinimumSize(new Dimension(200, 92));
        infoPanel.setMaximumSize(new Dimension(200, 92));
        infoPanel.setBackground(Color.WHITE);
        infoPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 0, 0));
        
        // create the labels
        JLabel nameLabel = createLabel("Name: " + character.getName());
        JLabel ageLabel = createLabel("Age: " + character.getAge());
        JLabel genderLabel = createGenderLabel(character.getGender());
        JLabel zodiacLabel = createLabel("<html>Zodiac: " + getZodiacSign(character.getZodiacSign()) + "</html>");
        JLabel occupationLabel = createLabel("Occupation: " + character.getOccupation());
        
        // add the labels to the panel
        infoPanel.add(nameLabel);
        infoPanel.add(ageLabel);
        infoPanel.add(genderLabel);
        infoPanel.add(zodiacLabel);
        infoPanel.add(occupationLabel);
        
        return infoPanel;
    }

    /**
     * create the common label
     * @param text the text of the label
     * @return the configured label
     */
    private JLabel createLabel(String text) {
        JLabel label = new JLabel(text);
        Font labelFont = new Font("Arial", Font.PLAIN, 14);
        label.setFont(labelFont);
        label.setHorizontalAlignment(SwingConstants.LEFT);
        label.setPreferredSize(new Dimension(200, 15));
        label.setBorder(BorderFactory.createEmptyBorder(5, 0, 10, 0));
        return label;
    }

    /**
     * create the gender label
     * @param gender the gender code
     * @return the configured gender label
     */
    private JLabel createGenderLabel(int gender) {
        JLabel genderLabel = new JLabel();
        String genderText = "Gender: " + getGenderString(gender);
        ImageIcon genderIcon = getGenderIcon(gender);
        
        if (genderIcon != null) {
            Image image = genderIcon.getImage();
            Image newimg = image.getScaledInstance(12, 12, Image.SCALE_SMOOTH);
            genderIcon = new ImageIcon(newimg);
            genderLabel.setIcon(genderIcon);
        }
        
        genderLabel.setText(genderText);
        genderLabel.setHorizontalTextPosition(SwingConstants.LEFT);
        genderLabel.setIconTextGap(5);
        
        Font labelFont = new Font("Arial", Font.PLAIN, 14);
        genderLabel.setFont(labelFont);
        genderLabel.setHorizontalAlignment(SwingConstants.LEFT);
        genderLabel.setPreferredSize(new Dimension(200, 15));
        genderLabel.setBorder(BorderFactory.createEmptyBorder(5, 0, 10, 0));
        
        return genderLabel;
    }

    /**
     * get the gender icon
     * @param gender the gender code
     * @return the gender icon
     */
    private ImageIcon getGenderIcon(int gender) {
        switch (gender) {
            case 1: return new ImageIcon("src/main/resources/female.png");
            case 2: return new ImageIcon("src/main/resources/male.png");
            case 3: return new ImageIcon("src/main/resources/other.png");
            default: return null;
        }
    }

    /**
     * create the button panel on the right
     * @param character the character information
     * @return the button panel
     */
    private JPanel createButtonPanel(CharacterRecord character) {
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 10));
        buttonPanel.setPreferredSize(new Dimension(30, 92));
        buttonPanel.setMinimumSize(new Dimension(30, 92));
        buttonPanel.setMaximumSize(new Dimension(30, 92));
        buttonPanel.setBackground(Color.WHITE);

        ImageIcon heartIcon = new ImageIcon("src/main/resources/heart.png");
        Image scaled = heartIcon.getImage().getScaledInstance(24, 24, Image.SCALE_SMOOTH);
        JButton addButton = new JButton(new ImageIcon(scaled));

        configureAddButton(addButton);
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
        return buttonPanel;
    }

    /**
     * configure the add button
     * @param button the button to configure
     */
    private void configureAddButton(JButton button) {
        button.setBorderPainted(false);
        button.setContentAreaFilled(false);
        button.setFocusPainted(false);
        button.setOpaque(true);
        button.setToolTipText("Add to Wishlist");
        button.setFont(new Font("Arial", Font.BOLD, 20));
        button.setPreferredSize(new Dimension(25, 25));
        button.setMargin(new Insets(0, 0, 0, 10));
        button.setFocusable(false);
        button.setBackground(Color.WHITE);
        button.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(200, 200, 200)),
                BorderFactory.createEmptyBorder(0, 0, 0, 0)
        ));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
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