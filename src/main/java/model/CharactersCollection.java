package model;

import java.util.*;
import java.util.stream.Collectors;

public class CharactersCollection {
    private List<CharacterRecord> allCharacters;
    private List<CharacterRecord> filteredCharacters;
    private Filter activeFilter = c -> true;
    private final String SOURCEFILE = "celebrities.json";

    public CharactersCollection() {
        this.allCharacters = JSONFileHandler.readJsonFile(SOURCEFILE);
        this.filteredCharacters = new ArrayList<>(allCharacters);
    }

    public void applyFilters(Filter filter) {
        this.activeFilter = filter;
        filteredCharacters = allCharacters.stream()
                .filter(activeFilter::matches)
                .collect(Collectors.toList());
    }

    public List<CharacterRecord> getFilteredCharacters() {
        return Collections.unmodifiableList(filteredCharacters);
    }

    public List<CharacterRecord> getAllCharacters() { return Collections.unmodifiableList(allCharacters); }

    public List<CharacterRecord> getSorted(List<CharacterRecord> characters, Comparator<CharacterRecord> comparator) {
        return characters.stream()
                .sorted(comparator)
                .collect(Collectors.toList());
    }

    public void loadData() {
        // 实现数据加载
        allCharacters = JSONFileHandler.readJsonFile(SOURCEFILE);
        filteredCharacters = new ArrayList<>(allCharacters);
    }

    public void setFilteredCharacters(List<CharacterRecord> characters) {
        filteredCharacters = new ArrayList<>(characters);
    }
}
