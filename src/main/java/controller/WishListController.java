package controller;

import view.WishListPanel;
import view.CharacterListPanel;
import model.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.io.File;
import java.io.IOException;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 * Controller for the wish list.
 */
public class WishListController implements IWishListController {

    /**
     * Get an instance of WishList.
     **/
    private final WishList wishListModel;
    /**
     * Get an instance of WishListPanel.
     **/
    private final WishListPanel wishListPanel;
    /**
     * Get an instance of CharactersCollection.
     **/
    private final CharactersCollection charactersCollection;

    /**
     * Constructor for this controller.
     **/
    public WishListController(WishList wishList, WishListPanel panel, CharacterListPanel characterListPanel, CharactersCollection charactersCollection) {
        this.wishListModel = wishList;
        this.wishListPanel = panel;
        this.charactersCollection = charactersCollection;
        initListeners(characterListPanel);
    }

    /**
     * Initializes all listeners between the CharacterListPanel and WishListPanel.
     * These listeners handle user interactions such as adding/removing characters,
     * clearing the wish list, and saving the list to a file.
     *
     * @param characterListPanel the panel that displays all available characters
     */
    private void initListeners(CharacterListPanel characterListPanel) {

        // Set the listener for adding a character to the wish list from CharacterListPanel
        characterListPanel.setAddToWishListListener(characterId -> {
            Response response = handleAddToWishList(characterId);
            if (response.getStatus() == 200) {
                updateView();
                JOptionPane.showMessageDialog(null, response.getMessage(), "Success", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(null, response.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        // Set the listener for removing a character from the wish list in WishListPanel
        wishListPanel.setRemoveCharacterListener(characterId -> {
            Response result = handleRemoveSingleCharacter(characterId);
            if (result.getStatus() == 200) {
                updateView();
                JOptionPane.showMessageDialog(null, result.getMessage(), "Success", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(null, result.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        // Set the listener for clearing the entire wish list in WishListPanel
        wishListPanel.addClearWishListListener(e -> {
            Response result = handleClearWishList();
            if (result.getStatus() == 200) {
                updateView();
                JOptionPane.showMessageDialog(null, result.getMessage(), "Success", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(null, result.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        // Set the listener for saving the wish list to a file from WishListPanel
        wishListPanel.addSaveToFileListener(e -> {
            saveToFile();
        });
    }

    /**
     * update the view.
     */
    @Override
    public void updateView() {
        List<CharacterRecord> updatedWishList = new ArrayList<>(getWishList());
        wishListPanel.setWishList(updatedWishList);
    }

    /**
     * Get the wish list.
     *
     * @return a set of characters in the wish list
     */
    private Set<CharacterRecord> getWishList() {
        return wishListModel.getWishList();
    }

    /**
     * Add the character corresponding to the id to the wish list.
     *
     * @param id the id of the character to be added
     * @return a Response object
     */
    @Override
    public Response handleAddToWishList(int id) {
        List<CharacterRecord> characters = charactersCollection.getFilteredCharacters();
        CharacterRecord newCharacter = characters.stream().filter(character -> character.getId() == id).findFirst().orElse(null);
        if (newCharacter == null) {
            return Response.failure("The character doesn't exist!");
        }
        if (wishListModel.addCharacter(id)) {
            return Response.success("Successfully Add The Character!");
        }
        return Response.failure("Character Already Exists!");
    }

    /**
     * Remove the character corresponding to the id from the wish list.
     *
     * @param id the id of the character to be removed
     * @return a Response object
     */
    @Override
    public Response handleRemoveSingleCharacter(int id) {
        CharacterRecord deleteCharacter = wishListModel.getCharacterById(id);
        if (deleteCharacter == null) {
            return Response.failure("The character doesn't exist!");
        }
        if (wishListModel.removeSingleCharacter(id)) {
            return Response.success("Successfully Remove The Character!");
        }
        return Response.failure("Failed To Remove The Character!");
    }

    /**
     * Clear the wish list.
     *
     * @return a Response object
     */
    @Override
    public Response handleClearWishList() {
        if (wishListModel.getWishList().isEmpty()) {
            return Response.failure("The wish list is empty!");
        }
        if (wishListModel.removeAllCharacters()) {
            return Response.success("Successfully Clear The Wish List!");
        }
        return Response.failure("Failed To Clear The Wish List!");
    }

    /**
     * Save the wish list to a file.
     */
    @Override
    public void saveToFile() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Save Wish List");
        fileChooser.setFileFilter(new FileNameExtensionFilter("JSON Files", "json"));
        fileChooser.setSelectedFile(new File("wishlist.json"));

        int userSelection = fileChooser.showSaveDialog(wishListPanel);
        if (userSelection == JFileChooser.APPROVE_OPTION) {
            File fileToSave = fileChooser.getSelectedFile();
            if (!fileToSave.getName().toLowerCase().endsWith(".json")) {
                fileToSave = new File(fileToSave.getAbsolutePath() + ".json");
            }

            try {
                // call the saveWishListToFile method in the JSONFileHandler util class
                JSONFileHandler.saveWishListToFile(fileToSave.getAbsolutePath(), wishListModel.getWishList());
                JOptionPane.showMessageDialog(wishListPanel,
                        "Wish List Saved Successfully!",
                        "Success",
                        JOptionPane.INFORMATION_MESSAGE);
            } catch (IOException e) {
                JOptionPane.showMessageDialog(wishListPanel,
                        "Failed To Save The File: " + e.getMessage(),
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}

