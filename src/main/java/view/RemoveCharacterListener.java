package view;

/**
 * A functional interface for removing a character from the wish list.
 * 
 * This interface defines a method for removing a character from the wish list
 * based on a character ID.
 */
@FunctionalInterface
public interface RemoveCharacterListener {
    void onRemove(int characterId);  // receive character ID
}
