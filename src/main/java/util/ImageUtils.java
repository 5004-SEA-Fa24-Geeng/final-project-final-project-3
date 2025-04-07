package util;

import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.File;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * image utils
 */
public class ImageUtils {
    /**
     * download and cache image
     * this method is used to download and cache the image of a celebrity to reduce the number of requests to the server
     * @param imageUrl
     * @param name
     * @param cacheDir
     * @return image path
     */
    public static String downloadAndCacheImage(String imageUrl, String name, String cacheDir) {
        if (imageUrl == null || imageUrl.isEmpty()) {
            return null;
        }

        try {
            // replace all non-alphanumeric characters with underscores
            String fileName = name.replaceAll("[^a-zA-Z0-9]", "_") + ".jpg";
            Path imagePath = Paths.get(cacheDir, fileName);

            // if the image is already cached, return the path
            if (Files.exists(imagePath)) {
                return imagePath.toString();
            }

            // download the image
            URL url = new URL("https://image.tmdb.org/t/p/w500" + imageUrl);
            BufferedImage image = ImageIO.read(url);
            if (image != null) {
                // save the image to the cache directory
                ImageIO.write(image, "jpg", imagePath.toFile());
                return imagePath.toString();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void createImageCacheDirectory(String cacheDir) {
        try {
            Path cachePath = Paths.get(cacheDir);
            if (!Files.exists(cachePath)) {
                Files.createDirectories(cachePath);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
} 