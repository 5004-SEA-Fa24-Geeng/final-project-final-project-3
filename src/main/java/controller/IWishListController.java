package controller;

import model.CharacterRecord;
import model.Response;
import java.util.Set;

public interface IWishListController extends IController {

    /**
     * Add the character corresponding to the id to the wish list.
     * @param id the id of the character to be added
     * @return a Response object
     */
    Response handleAddToWishList(int id);

    /**
     * Remove the character corresponding to the id from the wish list.
     * @param id the id of the character to be removed
     * @return a Response object
     */
    Response handleRemoveSingleCharacter(int id);

    /**
     * Clear the wish list.
     * @return a Response object
     */
    Response handleClearWishList();

    /**
     * Save the wish list to a JSON file.
     * @param fileName the name of the JSON file
     * @return a Response object
     */
    Response handleSaveWishList(String fileName);
}
