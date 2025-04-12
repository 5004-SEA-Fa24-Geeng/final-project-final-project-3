package view;

@FunctionalInterface
public interface RemoveCharacterListener {
    void onRemove(int characterId);  // receive character ID
}
