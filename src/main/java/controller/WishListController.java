package controller;

import view.WishListPanel;
import view.CharacterListPanel;
import model.*;
import util.ImageCache;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class WishListController implements IWishListController {

    private final WishList wishListModel;
    private final WishListPanel wishListPanel;
    private final CharactersCollection charactersCollection;

    /** Constructor for this class. **/
    public WishListController(WishList wishList, WishListPanel panel, CharacterListPanel characterListPanel, CharactersCollection charactersCollection) {
        this.wishListModel = wishList;
        this.wishListPanel = panel;
        this.charactersCollection = charactersCollection;
        initListeners(characterListPanel);
        List<CharacterRecord> initWishList = new ArrayList<>(wishListModel.getWishList());
        wishListPanel.setWishList(initWishList);
    }

    private void initListeners(CharacterListPanel characterListPanel) {
        System.out.println("Initializing listeners in WishListController");
        
        // 设置CharacterListPanel的addToWishListListener
        characterListPanel.setAddToWishListListener(characterId -> {
            System.out.println("AddToWishListListener triggered for characterId: " + characterId);
            Response response = handleAddToWishList(characterId);
            if (response.getStatus() == 200) {
                updateView();
                JOptionPane.showMessageDialog(null, response.getMessage(), "Success", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(null, response.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        // 设置移除角色的监听器
        wishListPanel.setRemoveCharacterListener(characterId -> {
            Response result = handleRemoveSingleCharacter(characterId);
            if (result.getStatus() == 200) {
                updateWishListPanel();
                JOptionPane.showMessageDialog(null, result.getMessage(), "Success", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(null, result.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        // 设置清空心愿单的监听器
        wishListPanel.addClearWishListListener(e -> {
            Response result = handleClearWishList();
            if (result.getStatus() == 200) {
                updateWishListPanel();
                JOptionPane.showMessageDialog(null, result.getMessage(), "Success", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(null, result.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        // 设置保存到文件的监听器
        wishListPanel.addSaveToFileListener(e -> {
            saveToFile();
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
    private Set<CharacterRecord> handleGetWishList() {
        return wishListModel.getWishList();
    }

    /**
     * Add the character corresponding to the id to the wish list.
     * @param id the id of the character to be added
     * @return a Response object
     */
    @Override
    public Response handleAddToWishList(int id) {
        System.out.println("Handling add to wish list for ID: " + id);
        List<CharacterRecord> characters = charactersCollection.getFilteredCharacters();
        System.out.println("Number of filtered characters: " + characters.size());
        CharacterRecord newCharacter = characters.stream().filter(character -> character.getId() == id).findFirst().orElse(null);
        if (newCharacter == null) {
            System.out.println("Character not found with ID: " + id);
            return Response.failure("The character doesn't exist!");
        }
        if (wishListModel.addCharacter(id)) {
            System.out.println("Successfully added character with ID: " + id);
            // 立即更新视图
            List<CharacterRecord> updatedWishList = new ArrayList<>(wishListModel.getWishList());
            System.out.println("Updated wish list size: " + updatedWishList.size());
            wishListPanel.setWishList(updatedWishList);
            return Response.success("Successfully add the character!");
        }
        System.out.println("Failed to add character with ID: " + id);
        return Response.failure("Failed to add the character!");
    }

    /**
     * Remove the character corresponding to the id from the wish list.
     * @param id the id of the character to be removed
     * @return a Response object
     */
    @Override
    public Response handleRemoveSingleCharacter(int id) {
        CharacterRecord deleteCharacter = wishListModel.getCharacterById(id);
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
    @Override
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
    @Override
    public Response handleSaveWishList(String fileName) {
        if (wishListModel.saveToFile(fileName)) {
            return Response.success("Successfully save to the file!");
        }
        return Response.failure("Failed to save to the file!");
    }

    @Override
    public void updateView() {
        System.out.println("Updating wish list view");
        List<CharacterRecord> updatedWishList = new ArrayList<>(wishListModel.getWishList());
        System.out.println("Current wish list size: " + updatedWishList.size());
        wishListPanel.setWishList(updatedWishList);
    }

    private void saveToFile() {
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
                // 使用Jackson创建JSON
                ObjectMapper mapper = new ObjectMapper();
                ArrayNode jsonArray = mapper.createArrayNode();
                for (CharacterRecord character : wishListModel.getWishList()) {
                    ObjectNode jsonObject = mapper.createObjectNode();
                    jsonObject.put("id", character.getId());
                    jsonObject.put("name", character.getName());
                    jsonObject.put("age", character.getAge());
                    jsonObject.put("gender", character.getGender());
                    jsonObject.put("zodiacSign", character.getZodiacSign());
                    jsonObject.put("profile", character.getProfile());
                    jsonArray.add(jsonObject);
                }
                
                // 写入文件，使用漂亮打印
                mapper.writerWithDefaultPrettyPrinter().writeValue(fileToSave, jsonArray);
                
                JOptionPane.showMessageDialog(wishListPanel, 
                    "Wish list saved successfully!", 
                    "Success", 
                    JOptionPane.INFORMATION_MESSAGE);
            } catch (IOException e) {
                JOptionPane.showMessageDialog(wishListPanel, 
                    "Error saving file: " + e.getMessage(), 
                    "Error", 
                    JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}

