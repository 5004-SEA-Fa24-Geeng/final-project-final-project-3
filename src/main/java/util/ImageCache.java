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
        // 创建缓存目录
        File cacheDir = new File(CACHE_DIR);
        if (!cacheDir.exists()) {
            cacheDir.mkdir();
        }
    }

    public static ImageIcon getImage(String imageUrl) {
        // 首先检查内存缓存
        if (memoryCache.containsKey(imageUrl)) {
            return memoryCache.get(imageUrl);
        }

        // 生成缓存文件名
        String cacheFileName = CACHE_DIR + File.separator + imageUrl.hashCode() + ".png";
        File cacheFile = new File(cacheFileName);

        try {
            // 如果缓存文件存在，直接从文件加载
            if (cacheFile.exists()) {
                BufferedImage image = ImageIO.read(cacheFile);
                ImageIcon icon = new ImageIcon(image);
                memoryCache.put(imageUrl, icon);
                return icon;
            }

            // 如果缓存文件不存在，从网络下载并保存
            URL url = new URL(imageUrl);
            BufferedImage image = ImageIO.read(url);
            
            // 保存到缓存文件
            ImageIO.write(image, "png", cacheFile);
            
            // 创建图标并存入内存缓存
            ImageIcon icon = new ImageIcon(image);
            memoryCache.put(imageUrl, icon);
            return icon;
        } catch (Exception e) {
            System.err.println("Error loading image: " + e.getMessage());
            return null;
        }
    }

    public static void clearCache() {
        // 清空内存缓存
        memoryCache.clear();
        
        // 删除缓存文件
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