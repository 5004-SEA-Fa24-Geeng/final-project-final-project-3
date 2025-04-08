package controller;

import View.WishListPanel;
import model.*;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class WishListController {

    /** Get an instance of WishList class. **/
    private WishList wishListModel = new WishList();

    /** Get an instance of WishListPanel class. **/
    private WishListPanel wishListPanel = new WishListPanel();

    /** Get an instance of CharactersCollection class. **/
    private CharactersCollection charactersCollection = new CharactersCollection();

    /** Constructor for this class. **/
    public WishListController() {
        initListeners();
        List<CharacterRecord> initWishList = new ArrayList<>(wishListModel.getWishList());
        wishListPanel.setWishList(initWishList);
    }

    private void initListeners() {
        // the controller registers itself as the observer and implements the notification method
        // and the panel keeps the reference for future use
        wishListPanel.setRemoveCharacterListener(characterId -> {
            Response result = handleRemoveSingleCharacter(characterId);
            if (result.getStatus() == 200) {
                updateWishListPanel();
                JOptionPane.showMessageDialog(null, result.getMessage(), "Success", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(null, result.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        wishListPanel.addClearWishListListener(e -> {
            Response result = handleClearWishList();
            if (result.getStatus() == 200) {
                updateWishListPanel();
                JOptionPane.showMessageDialog(null, result.getMessage(), "Success", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(null, result.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        wishListPanel.addSaveToFileListener(e -> {
            String fileName = JOptionPane.showInputDialog("Enter file name:");
            if (fileName != null && !fileName.trim().isEmpty()) {
                Response result = handleSaveWishList(fileName.trim()); // 获取保存操作的结果
                if (result.getStatus() == 200) {
                    JOptionPane.showMessageDialog(null, result.getMessage(), "Success", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(null, result.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
    }

    private void updateWishListPanel() {
        List<CharacterRecord> updatedWishList = new ArrayList<>(handleGetWishList());
        wishListPanel.setWishList(updatedWishList);
    }

    /**
     * Get the wish list.
     * @return a set of characters in the wish list
     */
    public Set<CharacterRecord> handleGetWishList() {
        return wishListModel.getWishList();
    }

    /**
     * Add the character corresponding to the id to the wish list.
     * @param id the id of the character to be added
     * @return a Response object
     */
    public Response handleAddToWishList(int id) {
        List<CharacterRecord> characters = charactersCollection.getFilteredCharacters();
        CharacterRecord newCharacter = characters.stream().filter(character -> character.getId() == id).findFirst().orElse(null); // check if the character corresponding to this id exists in the filtered list
        if (newCharacter == null) {
            return Response.failure("The character doesn't exist!");
        }
        if (wishListModel.addCharacter(id)) {
            return Response.success("Successfully add the character!");
        }
        return Response.failure("Failed to add the character!");
    }

    /**
     * Remove the character corresponding to the id from the wish list.
     * @param id the id of the character to be removed
     * @return a Response object
     */
    public Response handleRemoveSingleCharacter(int id) {
        CharacterRecord deleteCharacter = wishListModel.getCharacterById(id); // check if the character corresponding to this id exists in the wish list
        if (deleteCharacter == null) {
            return Response.failure("The character doesn't exist in the wish list!");
        }
        if (wishListModel.removeSingleCharacter(id)) {
            return Response.success("Successfully remove the character!");
        }
        return Response.failure("Failed to remove the character!");
    }

    /**
     * Clear the wish list.
     * @return a Response object
     */
    public Response handleClearWishList() {
        if (wishListModel.removeAllCharacters()) {
            return Response.success("Successfully clear the wish list!");
        }
        return Response.failure("Failed to clear the wish list!");
    }

    /**
     * Save the wish list to a JSON file.
     * @param fileName the name of the JSON file
     * @return a Response object
     */
    public Response handleSaveWishList(String fileName) {
        if (wishListModel.saveToFile(fileName)) {
            return Response.success("Successfully save to the file!");
        }
        return Response.failure("Failed to save to the file!");
    }
}

