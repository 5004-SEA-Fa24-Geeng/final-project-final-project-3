package view;

import model.CelebrityItem;
import model.Character;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.File;

public class CelebrityCellRenderer extends JPanel implements ListCellRenderer<CelebrityItem> {
    private final JLabel imageLabel;
    private final JLabel nameLabel;
    private final JLabel infoLabel;

    public CelebrityCellRenderer() {
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        imageLabel = new JLabel();
        imageLabel.setPreferredSize(new Dimension(100, 150));
        imageLabel.setHorizontalAlignment(SwingConstants.CENTER);
        imageLabel.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200)));

        JPanel infoPanel = new JPanel(new BorderLayout(5, 5));
        nameLabel = new JLabel();
        nameLabel.setFont(new Font("Segoe UI", Font.BOLD, 16));
        infoLabel = new JLabel();
        infoLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        infoLabel.setForeground(new Color(100, 100, 100));

        infoPanel.add(nameLabel, BorderLayout.NORTH);
        infoPanel.add(infoLabel, BorderLayout.CENTER);

        add(imageLabel, BorderLayout.WEST);
        add(infoPanel, BorderLayout.CENTER);
    }

    @Override
    public Component getListCellRendererComponent(JList<? extends CelebrityItem> list,
                                                CelebrityItem value,
                                                int index,
                                                boolean isSelected,
                                                boolean cellHasFocus) {
        Character character = value.getCharacter();
        
        // 设置背景色
        if (isSelected) {
            setBackground(new Color(230, 240, 255));
            setForeground(list.getSelectionForeground());
        } else {
            setBackground(index % 2 == 0 ? Color.WHITE : new Color(250, 250, 250));
            setForeground(list.getForeground());
        }

        // 设置文本
        nameLabel.setText(character.getName());
        infoLabel.setText(String.format("Age: %d, %s, %s", 
            character.getAge(), 
            character.getSign(), 
            character.getGender()));

        // 从本地加载图片
        try {
            String imagePath = value.getImageUrl();
            if (imagePath != null) {
                File imageFile = new File(imagePath);
                if (imageFile.exists()) {
                    BufferedImage image = ImageIO.read(imageFile);
                    if (image != null) {
                        Image scaledImage = image.getScaledInstance(100, 150, Image.SCALE_SMOOTH);
                        imageLabel.setIcon(new ImageIcon(scaledImage));
                    } else {
                        imageLabel.setIcon(null);
                    }
                } else {
                    imageLabel.setIcon(null);
                }
            } else {
                imageLabel.setIcon(null);
            }
        } catch (Exception e) {
            imageLabel.setIcon(null);
        }

        return this;
    }
} 