package view;

import model.CharacterRecord;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;
import java.net.URL;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import util.ImageCache;  // cache the images to reduce loading time

public class CharacterListPanel extends JPanel {
    private final JPanel contentPanel = new JPanel();
    private AddToWishListListener addToWishListListener;
    private SortPanel sortPanel;

    public CharacterListPanel(SortPanel sortPanel) {
        this.sortPanel = sortPanel;
        setLayout(new BorderLayout());
        setPreferredSize(new Dimension(400, 138));
        setMinimumSize(new Dimension(400, 138));
        setMaximumSize(new Dimension(400, 138));
        initComponents();
    }

    public void setAddToWishListListener(AddToWishListListener listener) {
        System.out.println("Setting addToWishListListener: " + (listener != null));
        this.addToWishListListener = listener;
    }

    public boolean isAddToWishListListenerSet() {
        return addToWishListListener != null;
    }

    private void initComponents() {
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
        JScrollPane scrollPane = new JScrollPane(contentPanel);
        add(scrollPane, BorderLayout.CENTER);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
    }

    public void setCharacterList(List<CharacterRecord> characters) {
        contentPanel.removeAll();
        for (CharacterRecord character : characters) {
            addCharacterPanel(character);
        }
        contentPanel.revalidate();
        contentPanel.repaint();
        updateCount();
    }

    private void addCharacterPanel(CharacterRecord character) {
        JPanel panel = new JPanel(new BorderLayout(0, 0));
        panel.setBorder(BorderFactory.createEmptyBorder(2, 2, 2, 2));

        // left image panel
        JPanel imagePanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        String imageUrl = "https://image.tmdb.org/t/p/w92" + character.getProfile();
        ImageIcon icon = ImageCache.getImage(imageUrl);
        if (icon != null) {
            imagePanel.add(new JLabel(icon));
        } else {
            imagePanel.add(new JLabel("No Image"));
        }

        // middle info panel
        JPanel infoPanel = new JPanel(new GridLayout(4, 1, 0, 2));
        infoPanel.setPreferredSize(new Dimension(200, 92));
        infoPanel.setMinimumSize(new Dimension(200, 92));
        infoPanel.setMaximumSize(new Dimension(200, 92));
        infoPanel.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 0));
        JLabel nameLabel = new JLabel("Name: " + character.getName());
        JLabel ageLabel = new JLabel("Age: " + character.getAge());
        JLabel genderLabel = new JLabel("Gender: " + getGenderString(character.getGender()));
        JLabel zodiacLabel = new JLabel("Zodiac: " + character.getZodiacSign());
        
        // set font size
        Font labelFont = new Font("Arial", Font.PLAIN, 12);
        nameLabel.setFont(labelFont);
        ageLabel.setFont(labelFont);
        genderLabel.setFont(labelFont);
        zodiacLabel.setFont(labelFont);
        
        infoPanel.add(nameLabel);
        infoPanel.add(ageLabel);
        infoPanel.add(genderLabel);
        infoPanel.add(zodiacLabel);

        // right button panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 0, 0));
        JButton addButton = new JButton("+");
        buttonPanel.setPreferredSize(new Dimension(30, 92));
        buttonPanel.setMinimumSize(new Dimension(30, 92));
        buttonPanel.setMaximumSize(new Dimension(30, 92));
        addButton.setFont(new Font("Arial", Font.BOLD, 20));
        addButton.setPreferredSize(new Dimension(25, 25));
        addButton.setMargin(new Insets(0, 0, 0, 10));
        addButton.setFocusable(false);
        addButton.setOpaque(true);
        addButton.setBackground(new Color(240, 240, 240));
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

    private String getGenderString(int gender) {
        switch (gender) {
            case 1: return "Female";
            case 2: return "Male";
            case 3: return "Non-binary";
            default: return "Unknown";
        }
    }

    private void updateCount() {
        if (sortPanel != null) {
            sortPanel.setCount(contentPanel.getComponentCount());
        }
    }

    public void updateView() {
        updateCount();
        contentPanel.repaint();
    }

    public interface AddToWishListListener {
        void onAdd(int characterId);
    }
} 