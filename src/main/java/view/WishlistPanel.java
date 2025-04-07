package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.function.Consumer;

import model.CelebrityItem;
import model.Character;
import model.ExtendedCharacter;

public class WishlistPanel {
    private final JPanel wishlistPanel;
    private final JList<CelebrityItem> wishList;
    private final DefaultListModel<CelebrityItem> wishListModel;
    private final ObjectMapper objectMapper;
    private final JPanel rootPanel;
    private final JList<CelebrityItem> characterList;
    private Consumer<CelebrityItem> addListener;
    private Consumer<CelebrityItem> removeListener;
    private Consumer<File> saveListener;

    public WishlistPanel(JPanel rootPanel, ObjectMapper objectMapper, JList<CelebrityItem> characterList) {
        this.rootPanel = rootPanel;
        this.objectMapper = objectMapper;
        this.characterList = characterList;
        
        // create the wishlist panel
        wishlistPanel = new JPanel(new BorderLayout());
        wishlistPanel.setBackground(new Color(245, 245, 245));
        
        // create the wishlist list
        wishListModel = new DefaultListModel<>();
        wishList = new JList<>(wishListModel);
        wishList.setCellRenderer(new CelebrityCellRenderer());
        JScrollPane wishScrollPane = new JScrollPane(wishList);
        wishScrollPane.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(new Color(200, 200, 200)),
            "Wishlist",
            javax.swing.border.TitledBorder.LEFT,
            javax.swing.border.TitledBorder.TOP,
            new Font("Segoe UI", Font.BOLD, 14),
            new Color(51, 51, 51)
        ));
        
        // create the button panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        buttonPanel.setBackground(new Color(245, 245, 245));
        
        // create the buttons
        JButton addButton = createStyledButton("Add to Wishlist");
        JButton removeButton = createStyledButton("Remove from Wishlist");
        JButton saveButton = createStyledButton("Save Wishlist");
        
        // add the buttons to the panel
        buttonPanel.add(addButton);
        buttonPanel.add(removeButton);
        buttonPanel.add(saveButton);
        
        // add the event listeners
        addButton.addActionListener(e -> addToWishlist());
        removeButton.addActionListener(e -> removeFromWishlist());
        saveButton.addActionListener(e -> saveWishlist());
        
        // add the components to the wishlist panel
        wishlistPanel.add(wishScrollPane, BorderLayout.CENTER);
        wishlistPanel.add(buttonPanel, BorderLayout.SOUTH);
    }
    
    public void setAddListener(Consumer<CelebrityItem> listener) {
        this.addListener = listener;
    }

    public void setRemoveListener(Consumer<CelebrityItem> listener) {
        this.removeListener = listener;
    }

    public void setSaveListener(Consumer<File> listener) {
        this.saveListener = listener;
    }

    private void addToWishlist() {
        CelebrityItem selectedItem = characterList.getSelectedValue();
        if (selectedItem != null && addListener != null) {
            addListener.accept(selectedItem);
        }
    }

    private void removeFromWishlist() {
        CelebrityItem selectedItem = wishList.getSelectedValue();
        if (selectedItem != null && removeListener != null) {
            removeListener.accept(selectedItem);
        }
    }

    private void saveWishlist() {
        if (saveListener != null) {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setDialogTitle("Save Wishlist");
            fileChooser.setFileFilter(new javax.swing.filechooser.FileNameExtensionFilter(
                "JSON Files", "json"));
            fileChooser.setSelectedFile(new File("wishlist.json")); // set default file name
            
            if (fileChooser.showSaveDialog(rootPanel) == JFileChooser.APPROVE_OPTION) {
                File file = fileChooser.getSelectedFile();
                if (!file.getName().toLowerCase().endsWith(".json")) {
                    file = new File(file.getPath() + ".json");
                }
                saveListener.accept(file);
                
                // show save success message
                JOptionPane.showMessageDialog(rootPanel, 
                    "Wishlist saved successfully to: " + file.getPath(), 
                    "Success", 
                    JOptionPane.INFORMATION_MESSAGE);
            }
        }
    }
    
    private JButton createStyledButton(String text) {
        JButton button = new JButton(text);
        button.setFont(new Font("Segoe UI", Font.BOLD, 14));
        button.setBackground(new Color(66, 133, 244));
        button.setForeground(Color.WHITE);
        button.setBorderPainted(false);
        button.setFocusPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        return button;
    }
    
    public JPanel getWishlistPanel() {
        return wishlistPanel;
    }
    
    public DefaultListModel<CelebrityItem> getWishListModel() {
        return wishListModel;
    }
} 