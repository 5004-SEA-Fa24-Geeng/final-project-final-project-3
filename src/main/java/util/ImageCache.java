package util;

import javax.swing.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.URL;
import javax.imageio.ImageIO;
import java.util.HashMap;
import java.util.Map;

public class ImageCache {
    private static final String CACHE_DIR = "image_cache";
    private static final Map<String, ImageIcon> memoryCache = new HashMap<>();
    
    static {
        // create cache directory
        File cacheDir = new File(CACHE_DIR);
        if (!cacheDir.exists()) {
            cacheDir.mkdir();
        }
    }

    public static ImageIcon getImage(String imageUrl) {
        // first check memory cache
        if (memoryCache.containsKey(imageUrl)) {
            return memoryCache.get(imageUrl);
        }

        // generate cache file name
        String cacheFileName = CACHE_DIR + File.separator + imageUrl.hashCode() + ".png";
        File cacheFile = new File(cacheFileName);

        try {
            // if cache file exists, load from file
            if (cacheFile.exists()) {
                BufferedImage image = ImageIO.read(cacheFile);
                ImageIcon icon = new ImageIcon(image);
                memoryCache.put(imageUrl, icon);
                return icon;
            }

            // if cache file does not exist, download from network and save
            URL url = new URL(imageUrl);
            BufferedImage image = ImageIO.read(url);
            
            // save to cache file
            ImageIO.write(image, "png", cacheFile);
            
            // create icon and save to memory cache
            ImageIcon icon = new ImageIcon(image);
            memoryCache.put(imageUrl, icon);
            return icon;
        } catch (Exception e) {
            System.err.println("Error loading image: " + e.getMessage());
            return null;
        }
    }

    public static void clearCache() {
        // clear memory cache
        memoryCache.clear();
        
        // delete cache files
        File cacheDir = new File(CACHE_DIR);
        if (cacheDir.exists()) {
            File[] files = cacheDir.listFiles();
            if (files != null) {
                for (File file : files) {
                    file.delete();
                }
            }
        }
    }
} 