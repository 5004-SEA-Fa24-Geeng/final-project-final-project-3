package model;

/**
 * item of celebrity in the list
 */
public class CelebrityItem {
    private final Character character;
    private final String imageUrl;

    /**
     * constructor
     * @param character
     * @param imageUrl
     */
    public CelebrityItem(Character character, String imageUrl) {
        this.character = character;
        this.imageUrl = imageUrl;
    }

    /**
     * get character
     * @return character
     */ 
    public Character getCharacter() {
        return character;
    }

    /**
     * get image url
     * @return image url
     */
    public String getImageUrl() {
        return imageUrl;
    }
} 