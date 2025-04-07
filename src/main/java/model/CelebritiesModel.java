package model;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import java.util.stream.Collectors;
import util.GenderUtils;
import util.ImageUtils;
import java.io.FileNotFoundException;
import com.fasterxml.jackson.core.type.TypeReference;
import util.AgeUtils;
import util.ZodiacUtils;

/**
 * celebrities model    
 * this class is used to manage the celebrities data
 */
public class CelebritiesModel {
    private final List<CelebrityItem> celebrities;
    private final List<CelebrityItem> wishlist;
    private final ObjectMapper objectMapper;
    private final String imageCacheDir;

    /**
     * constructor
     * @param objectMapper
     * @param imageCacheDir
     */
    public CelebritiesModel(ObjectMapper objectMapper, String imageCacheDir) {
        this.objectMapper = objectMapper;
        this.imageCacheDir = imageCacheDir;
        this.celebrities = new ArrayList<>();
        this.wishlist = new ArrayList<>();
    }

    /**
     * load initial data
     */
    public void loadInitialData() {
        try {
            // load celebrities.json from the current directory
            File jsonFile = new File("celebrities.json");
            if (!jsonFile.exists()) {
                jsonFile = new File("src/main/resources/celebrities.json");
            }
            if (!jsonFile.exists()) {
                jsonFile = new File("final-project-final-project-3/src/main/resources/celebrities.json");
            }
            if (!jsonFile.exists()) {
                throw new FileNotFoundException("Could not find celebrities.json in any of the expected locations");
            }

            // load JSON data
            List<JsonCelebrity> jsonCelebrities = objectMapper.readValue(
                jsonFile, 
                new TypeReference<List<JsonCelebrity>>() {}
            );

            // clear existing data
            celebrities.clear();
            wishlist.clear();

            // convert JSON data to Character objects
            for (JsonCelebrity jsonCelebrity : jsonCelebrities) {
                String name = jsonCelebrity.getName();
                int age = AgeUtils.calculateAge(jsonCelebrity.getBirthday());
                String sign = ZodiacUtils.getZodiacSign(jsonCelebrity.getBirthday());
                String gender = GenderUtils.convertGender(jsonCelebrity.getGender());
                String imageUrl = jsonCelebrity.getProfile_path();
                
                // download and cache image
                String cachedImagePath = ImageUtils.downloadAndCacheImage(imageUrl, name, imageCacheDir);
                
                // create extended Character class, including popularity information
                ExtendedCharacter character = new ExtendedCharacter(
                    name, age, sign, gender, jsonCelebrity.getPopularity()
                );
                CelebrityItem item = new CelebrityItem(character, cachedImagePath);
                celebrities.add(item);
            }
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Error loading celebrities data: " + e.getMessage(), e);
        }
    }

    /**
     * search celebrities
     * @param query
     * @param selectedZodiacs
     * @param minAge
     * @param maxAge
     * @param maleSelected
     * @param femaleSelected
     * @param nonBinarySelected
     * @return
     */
    public List<CelebrityItem> search(String query, List<String> selectedZodiacs, 
                                    int minAge, int maxAge, boolean maleSelected, 
                                    boolean femaleSelected, boolean nonBinarySelected) {
        return celebrities.stream()
            .filter(item -> {
                Character character = item.getCharacter();
                String name = character.getName().toLowerCase();
                boolean nameMatch = query.isEmpty() || name.contains(query.toLowerCase());
                
                boolean ageMatch = true;
                if (minAge != -1 && character.getAge() < minAge) ageMatch = false;
                if (maxAge != -1 && character.getAge() > maxAge) ageMatch = false;
                
                boolean zodiacMatch = selectedZodiacs.isEmpty() || 
                                    selectedZodiacs.contains(character.getSign());
                
                boolean genderMatch = true;
                String gender = character.getGender();
                if (maleSelected || femaleSelected || nonBinarySelected) {
                    genderMatch = false;
                    if (maleSelected && "Male".equals(gender)) genderMatch = true;
                    if (femaleSelected && "Female".equals(gender)) genderMatch = true;
                    if (nonBinarySelected && "Non-binary".equals(gender)) genderMatch = true;
                }
                
                return nameMatch && ageMatch && zodiacMatch && genderMatch;
            })
            .collect(Collectors.toList());
    }

    /**
     * sort celebrities
     * @param items
     * @param sortBy
     * @return
     */
    public List<CelebrityItem> sort(List<CelebrityItem> items, String sortBy) {
        if (items == null) {
            return new ArrayList<>();
        }
        
        System.out.println("Sorting by: " + sortBy);
        System.out.println("Items to sort: " + items.size());
        
        List<CelebrityItem> sortedItems = new ArrayList<>(items);
        
        if ("Alphabetical".equals(sortBy)) {
            sortedItems.sort((a, b) -> a.getCharacter().getName().compareTo(b.getCharacter().getName()));
            System.out.println("Sorted alphabetically");
        } else if ("Popularity".equals(sortBy)) {
            sortedItems.sort((a, b) -> {
                try {
                    ExtendedCharacter charA = (ExtendedCharacter) a.getCharacter();
                    ExtendedCharacter charB = (ExtendedCharacter) b.getCharacter();
                    return Double.compare(charB.getPopularity(), charA.getPopularity());
                } catch (ClassCastException e) {
                    return 0;
                }
            });
            System.out.println("Sorted by popularity");
        }
        
        System.out.println("Sorted items count: " + sortedItems.size());
        return sortedItems;
    }

    /**
     * add to wishlist
     * @param item
     */
    public void addToWishlist(CelebrityItem item) {
        if (item != null && !wishlist.contains(item)) {
            wishlist.add(item);
        }
    }

    /**
     * remove from wishlist
     * @param item
     */
    public void removeFromWishlist(CelebrityItem item) {
        wishlist.remove(item);
    }

    /**
     * save wishlist
     * @param file
     * @throws IOException
     */
    public void saveWishlist(File file) throws IOException {
        try {
            // prepare data to save
            List<Map<String, Object>> wishlistData = new ArrayList<>();
            for (CelebrityItem item : wishlist) {
                Character character = item.getCharacter();
                
                Map<String, Object> celebrityData = new HashMap<>();
                celebrityData.put("name", character.getName());
                celebrityData.put("age", character.getAge());
                celebrityData.put("sign", character.getSign());
                celebrityData.put("gender", character.getGender());
                if (character instanceof ExtendedCharacter) {
                    celebrityData.put("popularity", ((ExtendedCharacter) character).getPopularity());
                }
                celebrityData.put("imagePath", item.getImageUrl());
                
                wishlistData.add(celebrityData);
            }
            
            // write JSON file with pretty formatting
            objectMapper.writerWithDefaultPrettyPrinter().writeValue(file, wishlistData);
        } catch (IOException e) {
            throw new IOException("Error saving wishlist: " + e.getMessage(), e);
        }
    }

    public List<CelebrityItem> getCelebrities() {
        return new ArrayList<>(celebrities);
    }

    public List<CelebrityItem> getWishlist() {
        return new ArrayList<>(wishlist);
    }
} 