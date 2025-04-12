package view;

import java.util.List;
import javax.swing.*;
import model.CharacterRecord;
import java.awt.*;
import java.awt.event.ActionListener;
import util.ImageCache;

public class WishListPanel extends JPanel {
    private final JPanel contentPanel = new JPanel();
    private final JButton clearButton = new JButton("Clear");
    private final JButton saveButton = new JButton("Save to File");
    private RemoveCharacterListener removeCharacterListener;

    public WishListPanel() {
        setLayout(new BorderLayout());
        setPreferredSize(new Dimension(400, 0));
        initComponents();
    }

    public void setRemoveCharacterListener(RemoveCharacterListener listener) {
        this.removeCharacterListener = listener;
    }

    private void initComponents() {
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
        contentPanel.setAlignmentX(Component.LEFT_ALIGNMENT);  // 设置左对齐
        JScrollPane scrollPane = new JScrollPane(contentPanel);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        add(scrollPane, BorderLayout.CENTER);

        // 按钮面板
        JPanel buttonPanel = new JPanel(new GridLayout(1, 2));
        buttonPanel.add(clearButton);
        buttonPanel.add(saveButton);
        add(buttonPanel, BorderLayout.SOUTH);
    }

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

    private void addCharacterPanel(CharacterRecord character) {
        JPanel panel = new JPanel(new BorderLayout(0, 0));
        panel.setBorder(BorderFactory.createEmptyBorder(2, 2, 2, 2));
        panel.setMaximumSize(new Dimension(400, 138));  // set maximum height
        panel.setPreferredSize(new Dimension(400, 138));  // set preferred height
        panel.setMinimumSize(new Dimension(400, 138));  // set minimum height

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
        
        JLabel nameLabel = new JLabel("Name: " + character.getName());
        JLabel ageLabel = new JLabel("Age: " + character.getAge());
        
        // 创建性别标签
        JLabel genderLabel = new JLabel();
        String genderText = "Gender: " + getGenderString(character.getGender());
        ImageIcon genderIcon = null;
        
        // 根据性别设置图标
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
        
        // 调整图标大小
        if (genderIcon != null) {
            Image image = genderIcon.getImage();
            Image newimg = image.getScaledInstance(12, 12, Image.SCALE_SMOOTH);
            genderIcon = new ImageIcon(newimg);
            genderLabel.setIcon(genderIcon);
        }
        
        // 设置文字和图标位置
        genderLabel.setText(genderText);
        genderLabel.setHorizontalTextPosition(SwingConstants.LEFT);
        genderLabel.setIconTextGap(5); // 图标和文字之间的间距
        
        JLabel zodiacLabel = new JLabel("<html>Zodiac: " + 
            (character.getZodiacSign().equalsIgnoreCase("Aries") ? "♈ " :
             character.getZodiacSign().equalsIgnoreCase("Taurus") ? "♉ " :
             character.getZodiacSign().equalsIgnoreCase("Gemini") ? "♊ " :
             character.getZodiacSign().equalsIgnoreCase("Cancer") ? "♋ " :
             character.getZodiacSign().equalsIgnoreCase("Leo") ? "♌ " :
             character.getZodiacSign().equalsIgnoreCase("Virgo") ? "♍ " :
             character.getZodiacSign().equalsIgnoreCase("Libra") ? "♎ " :
             character.getZodiacSign().equalsIgnoreCase("Scorpio") ? "♏ " :
             character.getZodiacSign().equalsIgnoreCase("Sagittarius") ? "♐ " :
             character.getZodiacSign().equalsIgnoreCase("Capricorn") ? "♑ " :
             character.getZodiacSign().equalsIgnoreCase("Aquarius") ? "♒ " :
             character.getZodiacSign().equalsIgnoreCase("Pisces") ? "♓ " : "") + 
            character.getZodiacSign() + "</html>");
        
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
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 0, 0));
        buttonPanel.setPreferredSize(new Dimension(30, 92));
        buttonPanel.setMinimumSize(new Dimension(30, 92));
        buttonPanel.setMaximumSize(new Dimension(30, 92));
        JButton removeButton = new JButton("-");
        removeButton.setFont(new Font("Arial", Font.BOLD, 20));
        removeButton.setPreferredSize(new Dimension(25, 25));
        removeButton.setMargin(new Insets(0, 0, 0, 10));
        removeButton.setFocusable(false);
        removeButton.setOpaque(true);
        removeButton.setBackground(new Color(240, 240, 240));
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
        
        buttonPanel.add(removeButton);

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
            case 3: return "Other";
            default: return "Unknown";
        }
    }

    public void addClearWishListListener(ActionListener listener) {
        clearButton.addActionListener(listener);
    }

    public void addSaveToFileListener(ActionListener listener) {
        saveButton.addActionListener(listener);
    }

    public void updateView() {
        contentPanel.revalidate();
        contentPanel.repaint();
    }

    public interface RemoveCharacterListener {
        void onRemove(int characterId);
    }
}
