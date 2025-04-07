package view;

import com.fasterxml.jackson.databind.ObjectMapper;
import model.CelebrityItem;
import model.ExtendedCharacter;

import javax.swing.*;
import java.awt.*;
import java.util.List;

/**
 * celebrities view
 * this class is used to create the view for the celebrities
 */
public class CelebritiesView {
    private final JPanel rootPanel;
    private final CelebritiesMenu menu;
    private final JList<CelebrityItem> characterList;
    private final DefaultListModel<CelebrityItem> characterListModel;
    private final WishlistPanel wishlistPanel;

    public CelebritiesView(ObjectMapper objectMapper) {
        // create root panel
        rootPanel = new JPanel(new BorderLayout(10, 10));
        rootPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        rootPanel.setBackground(new Color(245, 245, 245));

        // create top panel
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setBackground(new Color(245, 245, 245));
        
        // create title label
        JLabel titleLabel = new JLabel("Celebrity Wishlist", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 28));
        titleLabel.setForeground(new Color(51, 51, 51));
        titleLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 0));
        topPanel.add(titleLabel, BorderLayout.NORTH);

        // create menu
        menu = new CelebritiesMenu(null, null);
        topPanel.add(menu.getMenuPanel(), BorderLayout.CENTER);

        // create list panel
        JPanel listsPanel = new JPanel(new GridLayout(1, 2, 15, 0));
        listsPanel.setBackground(new Color(245, 245, 245));
        
        // create celebrities list
        characterListModel = new DefaultListModel<>();
        characterList = new JList<>(characterListModel);
        characterList.setCellRenderer(new CelebrityCellRenderer());
        JScrollPane characterScrollPane = new JScrollPane(characterList);
        characterScrollPane.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(new Color(200, 200, 200)),
            "Celebrities",
            javax.swing.border.TitledBorder.LEFT,
            javax.swing.border.TitledBorder.TOP,
            new Font("Segoe UI", Font.BOLD, 14),
            new Color(51, 51, 51)
        ));
        
        // create wishlist panel
        wishlistPanel = new WishlistPanel(rootPanel, objectMapper, characterList);
        
        // add list to panel
        listsPanel.add(characterScrollPane);
        listsPanel.add(wishlistPanel.getWishlistPanel());

        // add all panels to main panel
        rootPanel.add(topPanel, BorderLayout.NORTH);
        rootPanel.add(listsPanel, BorderLayout.CENTER);
    }

    /**
     * update celebrities list
     * this method is used to update the celebrities list
     * @param items
     */
    public void updateCelebritiesList(List<CelebrityItem> items) {
        System.out.println("\nView: Updating list with " + items.size() + " items"); // debug output
        
        // get current selected item (if any)
        CelebrityItem selectedItem = characterList.getSelectedValue();
        System.out.println("View: Current selected item: " + 
                        (selectedItem != null ? selectedItem.getCharacter().getName() : "none")); // debug output
        
        // create new list model
        DefaultListModel<CelebrityItem> model = new DefaultListModel<>();
        for (CelebrityItem item : items) {
            model.addElement(item);
        }
        
        // update list model
        characterList.setModel(model);
        
        // if there was a previously selected item, try to reselect it
        if (selectedItem != null) {
            for (int i = 0; i < model.size(); i++) {
                if (model.getElementAt(i).getCharacter().getName()
                        .equals(selectedItem.getCharacter().getName())) {
                    characterList.setSelectedIndex(i);
                    break;
                }
            }
        }
        

        for (int i = 0; i < Math.min(3, model.size()); i++) {
            CelebrityItem item = model.getElementAt(i);
            if (item.getCharacter() instanceof ExtendedCharacter) {
                ExtendedCharacter character = (ExtendedCharacter) item.getCharacter();
                System.out.println("View: Item " + i + ": " + character.getName() + 
                                " (Popularity: " + character.getPopularity() + ")");
            } else {
                System.out.println("View: Item " + i + ": " + item.getCharacter().getName());
            }
        }
        
        System.out.println("View: List updated"); // debug output
    }

    /**
     * update wishlist
     * this method is used to update the wishlist
     * @param items
     */
    public void updateWishlist(List<CelebrityItem> items) {
        wishlistPanel.getWishListModel().clear();
        for (CelebrityItem item : items) {
            wishlistPanel.getWishListModel().addElement(item);
        }
    }

    /**
     * get root panel
     * this method is used to get the root panel
     * @return root panel
     */
    public JPanel getRootPanel() {
        return rootPanel;
    }

    /**
     * get menu
     * this method is used to get the menu
     * @return menu
     */
    public CelebritiesMenu getMenu() {
        return menu;
    }

    /**
     * get celebrities list
     * this method is used to get the celebrities list
     * @return celebrities list
     */
    public JList<CelebrityItem> getCelebritiesList() {
        return characterList;
    }

    /**
     * get wishlist panel
     * this method is used to get the wishlist panel
     * @return wishlist panel
     */
    public WishlistPanel getWishlistPanel() {
        return wishlistPanel;
    }
} 