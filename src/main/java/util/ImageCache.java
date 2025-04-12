package util;

import javax.swing.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.URL;
import javax.imageio.ImageIO;
import java.util.HashMap;
import java.util.Map;

/**
 * Utility class for caching images downloaded from URLs.
 * Supports both in-memory and on-disk caching to improve performance
 * and reduce redundant network calls.
 */
public class ImageCache {

    /**
     * The directory where image files are cached on disk.
     */
    private static final String CACHE_DIR = "image_cache";

    /**
     * In-memory map from image URLs to cached ImageIcons.
     */
    private static final Map<String, ImageIcon> memoryCache = new HashMap<>();

    // Static initializer to ensure the cache directory exists
    static {
        File cacheDir = new File(CACHE_DIR);
        if (!cacheDir.exists()) {
            cacheDir.mkdir();
        }
    }

    /**
     * Returns a cached ImageIcon for the specified image URL.
     * First checks memory cache, then disk cache, and finally downloads from the network.
     *
     * @param imageUrl the URL of the image
     * @return the corresponding ImageIcon, or {@code null} if the image could not be loaded
     */
    public static ImageIcon getImage(String imageUrl) {
        // Check memory cache
        if (memoryCache.containsKey(imageUrl)) {
            return memoryCache.get(imageUrl);
        }

        // Generate a unique cache file name based on the image URL hash
        String cacheFileName = CACHE_DIR + File.separator + imageUrl.hashCode() + ".png";
        File cacheFile = new File(cacheFileName);

        try {
            // Load from disk cache if it exists
            if (cacheFile.exists()) {
                BufferedImage image = ImageIO.read(cacheFile);
                ImageIcon icon = new ImageIcon(image);
                memoryCache.put(imageUrl, icon);
                return icon;
            }

            // Otherwise, download from the network
            URL url = new URL(imageUrl);
            BufferedImage image = ImageIO.read(url);

            // Save the downloaded image to disk cache
            ImageIO.write(image, "png", cacheFile);

            // Save to memory cache and return
            ImageIcon icon = new ImageIcon(image);
            memoryCache.put(imageUrl, icon);
            return icon;
        } catch (Exception e) {
            System.err.println("Error loading image: " + e.getMessage());
            return null;
        }
    }

    /**
     * Clears both the memory and disk caches.
     * Useful when resetting cached data or cleaning up resources.
     */
    public static void clearCache() {
        // Clear in-memory cache
        memoryCache.clear();

        // Delete all files in the disk cache directory
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
