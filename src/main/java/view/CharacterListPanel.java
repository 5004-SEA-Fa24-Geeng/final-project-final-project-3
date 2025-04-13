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
        setBackground(new Color(252,236,222));
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
        contentPanel.setBackground(new Color(252,236,222));
        JScrollPane scrollPane = new JScrollPane(contentPanel);
        add(scrollPane, BorderLayout.CENTER);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.getViewport().setBackground(new Color(252, 236, 222)); // ✅ ADD THIS
        scrollPane.setBorder(BorderFactory.createEmptyBorder()); // (optional for seamless look)

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
        panel.setBackground(new Color(252, 236, 222)); // ✅ to match
        panel.setBorder(BorderFactory.createEmptyBorder(2, 2, 2, 2));

        // left image panel
        JPanel imagePanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        imagePanel.setBackground(new Color(252, 236, 222)); // ✅
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
        infoPanel.setBackground(new Color(252,236,222));
        infoPanel.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 0));
        
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
        
        JLabel zodiacLabel = new JLabel("<html>Zodiac: " + getZodiacSign(character.getZodiacSign()) + "</html>");
        JLabel occupationLabel = new JLabel("Occupation: " + character.getOccupation());
        
        // set font size and alignment
        Font labelFont = new Font("Arial", Font.PLAIN, 12);
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
        JPanel buttonPanel = new JPanel(new GridBagLayout());
        buttonPanel.setPreferredSize(new Dimension(30, 92));
        buttonPanel.setMinimumSize(new Dimension(30, 92));
        buttonPanel.setMaximumSize(new Dimension(30, 92));
        buttonPanel.setBackground(new Color(252, 236, 222));
        ImageIcon heartIcon = new ImageIcon("src/main/resources/heart.png");
        Image scaled = heartIcon.getImage().getScaledInstance(24, 24, Image.SCALE_SMOOTH);
        JButton addButton = new JButton(new ImageIcon(scaled));

        addButton.setBorderPainted(false);
        addButton.setContentAreaFilled(false);
        addButton.setFocusPainted(false);
        addButton.setOpaque(false);
        addButton.setToolTipText("Add to Wishlist");
        // lets background show through




        buttonPanel.setPreferredSize(new Dimension(30, 92));
        buttonPanel.setMinimumSize(new Dimension(30, 92));
        buttonPanel.setMaximumSize(new Dimension(30, 92));
        buttonPanel.setBackground(new Color(252,236,222));
        addButton.setFont(new Font("Arial", Font.BOLD, 20));
        addButton.setPreferredSize(new Dimension(25, 25));
        addButton.setMargin(new Insets(0, 0, 0, 10));
        addButton.setFocusable(false);
        addButton.setOpaque(true);
        addButton.setBackground(new Color(252, 236, 222));
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