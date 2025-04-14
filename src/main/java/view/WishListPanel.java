package view;

import java.util.List;
import javax.swing.*;
import model.CharacterRecord;
import java.awt.*;
import java.awt.event.ActionListener;
import util.ImageCache;

/**
 * Panel that displays and manages a user's character wish list.
 * Supports adding, removing, and saving characters.
 */
public class WishListPanel extends JPanel {
    /** Panel that holds all the character panels in the wishlist. */
    private final JPanel contentPanel = new JPanel();
    /** Button used to clear all characters from the wishlist. */
    private final JButton clearButton = new JButton("Clear");
    /** Button used to save the current wishlist to a file. */
    private final JButton saveButton = new JButton("Save to File");
    /** Listener that handles character removal from the wishlist. */
    private RemoveCharacterListener removeCharacterListener;

    /**
     * Constructs the WishListPanel UI.
     */
    public WishListPanel() {
        setLayout(new BorderLayout());
        setPreferredSize(new Dimension(400, 0));
        setBorder(BorderFactory.createLineBorder(new Color(223, 0, 102), 2));
        initComponents();
    }

    /**
     * Sets the listener for remove button actions.
     *
     * @param listener the listener to be notified when a character is removed
     */
    public void setRemoveCharacterListener(RemoveCharacterListener listener) {
        this.removeCharacterListener = listener;
    }

    private void initComponents() {
        // White background
        setBackground(Color.WHITE);
        setOpaque(true);
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
        contentPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
        contentPanel.setBackground(Color.WHITE);
        contentPanel.setOpaque(true);

        // Scroll pane setup
        JScrollPane scrollPane = new JScrollPane(contentPanel);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setBorder(null); // Remove border
        scrollPane.setOpaque(false);
        scrollPane.getViewport().setOpaque(false);

        add(scrollPane, BorderLayout.CENTER);

        // Button panel
        JPanel buttonPanel = new JPanel(new GridLayout(1, 2));
        buttonPanel.setBackground(Color.WHITE);
        buttonPanel.add(clearButton);
        buttonPanel.add(saveButton);
        add(buttonPanel, BorderLayout.SOUTH);
    }

    /**
     * Sets the characters to display in the wishlist panel.
     *
     * @param characters list of CharacterRecord objects
     */
    public void setWishList(List<CharacterRecord> characters) {
        System.out.println("Setting wish list with " + characters.size() + " characters");
        contentPanel.removeAll();
        for (CharacterRecord character : characters) {
            addCharacterPanel(character);
        }
        contentPanel.revalidate();
        contentPanel.repaint();

        clearButton.setEnabled(!characters.isEmpty());
        saveButton.setEnabled(!characters.isEmpty());
    }

    /**
     * Adds a visual representation of a character to the wishlist panel.
     *
     * @param character the character to display
     */
    private void addCharacterPanel(CharacterRecord character) {
        JPanel panel = new JPanel(new BorderLayout(0, 0));
        panel.setBorder(BorderFactory.createEmptyBorder(2, 2, 2, 2));
        panel.setMaximumSize(new Dimension(380, 138));
        panel.setPreferredSize(new Dimension(380, 138));
        panel.setMinimumSize(new Dimension(380, 138));

        // left image panel
        JPanel imagePanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        imagePanel.setMaximumSize(new Dimension(92, 138));
        imagePanel.setPreferredSize(new Dimension(92, 138));
        imagePanel.setMinimumSize(new Dimension(92, 138));
        String imageUrl = "https://image.tmdb.org/t/p/w92" + character.getProfile();
        ImageIcon icon = ImageCache.getImage(imageUrl);
        if (icon != null) {
            JLabel imageLabel = new JLabel(icon);
            imageLabel.setMaximumSize(new Dimension(92, 138));
            imageLabel.setPreferredSize(new Dimension(92, 138));
            imageLabel.setMinimumSize(new Dimension(92, 138));
            imageLabel.setVerticalAlignment(JLabel.TOP);
            imageLabel.setAlignmentY(Component.TOP_ALIGNMENT);
            imagePanel.add(imageLabel);
        } else {
            JLabel noImageLabel = new JLabel("No Image");
            noImageLabel.setMaximumSize(new Dimension(92, 138));
            noImageLabel.setPreferredSize(new Dimension(92, 138));
            noImageLabel.setMinimumSize(new Dimension(92, 138));
            noImageLabel.setVerticalAlignment(JLabel.TOP);
            noImageLabel.setAlignmentY(Component.TOP_ALIGNMENT);
            imagePanel.add(noImageLabel);
        }

        // middle info panel
        JPanel infoPanel = new JPanel();
        infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS));
        infoPanel.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 0));
        infoPanel.setMaximumSize(new Dimension(250, 138));
        infoPanel.setPreferredSize(new Dimension(250, 138));
        infoPanel.setMinimumSize(new Dimension(250, 138));
        infoPanel.setBackground(Color.WHITE);
        
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
        
        JLabel zodiacLabel = new JLabel("Zodiac: " + character.getZodiacSign());
        JLabel occupationLabel = new JLabel("Occupation: " + character.getOccupation());
        
        // set label font
        Font labelFont = new Font("Dialog", Font.PLAIN, 12);
        nameLabel.setFont(labelFont);
        ageLabel.setFont(labelFont);
        genderLabel.setFont(labelFont);
        zodiacLabel.setFont(labelFont);
        occupationLabel.setFont(labelFont);
        // set label margin
        nameLabel.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
        ageLabel.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
        genderLabel.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
        zodiacLabel.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
        occupationLabel.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
        
        // set label minimum and maximum height
        nameLabel.setMinimumSize(new Dimension(0, 15));
        nameLabel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 15));
        ageLabel.setMinimumSize(new Dimension(0, 15));
        ageLabel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 15));
        genderLabel.setMinimumSize(new Dimension(0, 15));
        genderLabel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 15));
        zodiacLabel.setMinimumSize(new Dimension(0, 15));
        zodiacLabel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 15));
        occupationLabel.setMinimumSize(new Dimension(0, 15));
        occupationLabel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 15));
        
        // add label to panel
        infoPanel.add(nameLabel);
        infoPanel.add(ageLabel);
        infoPanel.add(genderLabel);
        infoPanel.add(zodiacLabel);
        infoPanel.add(occupationLabel);

        // right button panel
        JPanel buttonPanel = new JPanel(new BorderLayout());
        buttonPanel.setPreferredSize(new Dimension(57, 92));
        buttonPanel.setMinimumSize(new Dimension(57, 92));
        buttonPanel.setMaximumSize(new Dimension(57, 92));
        buttonPanel.setBackground(Color.WHITE);
        JPanel topRightWrapper = new JPanel(new FlowLayout(FlowLayout.RIGHT, 0, 0)); // 🔧 aligns to top-right
        topRightWrapper.setOpaque(false); // Let background show through
        topRightWrapper.setBorder(BorderFactory.createEmptyBorder(8, 0, 0, 12)); // 🔧 8px from top
        JButton removeButton = new JButton("-");
        removeButton.setFont(new Font("Arial", Font.BOLD, 20));
        removeButton.setPreferredSize(new Dimension(25, 25));
        removeButton.setMargin(new Insets(0, 0, 0, 10));
        removeButton.setFocusable(false);
        removeButton.setOpaque(true);
        removeButton.setBackground(Color.WHITE);
        removeButton.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(200, 200, 200)),
            BorderFactory.createEmptyBorder(0, 0, 0, 0)
        ));
        removeButton.setCursor(new Cursor(Cursor.HAND_CURSOR));

        removeButton.addActionListener(e -> {
            System.out.println("Remove button clicked for character: " + character.getName());
            if (removeCharacterListener != null) {
                System.out.println("Removing character with ID: " + character.getId());
                removeCharacterListener.onRemove(character.getId());
            } else {
                System.out.println("removeCharacterListener is null");
            }
        });

        // Add button to wrapper, then wrapper to top of button panel
        topRightWrapper.add(removeButton);
        buttonPanel.add(topRightWrapper, BorderLayout.NORTH); // 🔧 key: add to NORTH

        // add panel to main panel
        panel.add(imagePanel, BorderLayout.WEST);
        panel.add(infoPanel, BorderLayout.CENTER);
        panel.add(buttonPanel, BorderLayout.EAST);

        // add separator
        panel.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.LIGHT_GRAY));

        contentPanel.add(panel);
    }

    /**
     * Converts a gender code to its corresponding string.
     *
     * @param gender the integer gender code (1 = Female, 2 = Male, 3 = Other)
     * @return a string representing the gender
     */
    private String getGenderString(int gender) {
        switch (gender) {
            case 1: return "Female";
            case 2: return "Male";
            case 3: return "Other";
            default: return "Unknown";
        }
    }

    /**
     * Adds a listener to handle clearing the wishlist.
     *
     * @param listener the action listener for the clear button
     */
    public void addClearWishListListener(ActionListener listener) {
        clearButton.addActionListener(listener);
    }

    /**
     * Adds a listener to handle saving the wishlist to a file.
     *
     * @param listener the action listener for the save button
     */
    public void addSaveToFileListener(ActionListener listener) {
        saveButton.addActionListener(listener);
    }

    /**
     * Refreshes the view to reflect the latest changes in the panel.
     */
    public void updateView() {
        contentPanel.revalidate();
        contentPanel.repaint();
    }

    /**
     * Listener interface for handling character removal actions.
     */
    public interface RemoveCharacterListener {
        void onRemove(int characterId);
    }
}
