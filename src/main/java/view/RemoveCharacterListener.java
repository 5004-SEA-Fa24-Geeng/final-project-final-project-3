package View;

@FunctionalInterface
public interface RemoveCharacterListener {
    void onRemove(int characterId);  // 接收角色的 ID
}
