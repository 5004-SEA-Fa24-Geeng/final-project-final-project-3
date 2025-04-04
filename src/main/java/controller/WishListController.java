package controller;

import model.*;

import java.util.List;
import java.util.Set;

public class WishListController {

    /** Get an instance of WishList class. **/
    private WishList wishListModel = new WishList();

    /** Get an instance of CharactersCollection class. **/
    private CharactersCollection charactersCollection = new CharactersCollection();

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
        if (wishListModel.getWishList().size() == 0) {
            return Response.disabled();
        }
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
        if (wishListModel.getWishList().size() == 0) {
            return Response.disabled();
        }
        if (wishListModel.saveToFile(fileName)) {
            return Response.success("Successfully save to the file!");
        }
        return Response.failure("Failed to save to the file!");
    }
}
