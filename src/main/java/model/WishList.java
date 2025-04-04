package model;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

public class WishList {

    /** Use a LinkedHashSet as a wish list. **/
    static Set<CharacterRecord> wishList = new LinkedHashSet();

    /* Get an instance of the CharactersCollection Class. **/
    private CharactersCollection charactersCollection = new CharactersCollection();

    /**
     * Count the number of the characters in the wish list.
     * @return the number of the characters in the wish list
     */
    public int countWishList() {
        return wishList.size();
    }

    /**
     * Add a character to the wish list.
     * @param id the character added by the user
     * @return if the character is successfully added to the wish list
     */
    public boolean addCharacter(int id) {
        List<CharacterRecord> filteredCharacters = charactersCollection.getFilteredCharacters();
        CharacterRecord newCharacter = filteredCharacters.stream().filter(character -> character.getId() == id).findFirst().get();
        return wishList.add(newCharacter);
    }

    /**
     * Remove a character from the wish list.
     * @param id the character removed by the user
     * @return if the character is successfully removed from the wish list
     */
    public boolean removeSingleCharacter(int id) {
        return wishList.removeIf(character -> character.getId() == id);
    }

    /**
     * Remove all characters from the wish list.
     * @return if all characters are successfully removed from the wish list
     */
    public boolean removeAllCharacters() {
        wishList.clear();
        return wishList.isEmpty();
    }

    /**
     * Get a character by its id.
     * @param id the id of the character
     * @return the character
     */
    public CharacterRecord getCharacterById(int id) {
        return wishList.stream().filter(character -> character.getId() == id).findFirst().orElse(null);
    }

    /**
     * Get the wish list.
     * @return the wish list
     */
    public Set<CharacterRecord> getWishList() {
        return wishList;
    }

    /**
     * Save the characters in the wish list to a Json file.
     * @param fileName the name of the file saved the wish list to
     * @return if the wish list is successfully saved to the file
     */
    public boolean saveToFile(String fileName) {
        try {
            JSONFileHandler.writeWishListToFile(fileName);
        } catch (IOException e) {
            return false;
        }
        return true;
    }
}
