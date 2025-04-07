package controller;

import model.CelebritiesModel;
import view.CelebritiesView;
import model.CelebrityItem;
import java.io.File;
import java.util.List;
import javax.swing.DefaultListModel;
import javax.swing.JOptionPane;
import java.util.ArrayList;
import java.util.function.Consumer;

public class CelebritiesController {
    private final CelebritiesModel model;
    private final CelebritiesView view;
    private String currentSortBy = "Alphabetical"; // 默认排序方式

    public CelebritiesController(CelebritiesModel model, CelebritiesView view) {
        this.model = model;
        this.view = view;
        
        // 设置搜索监听器
        view.getMenu().setSearchListener(params -> {
            // 先进行搜索
            List<CelebrityItem> filteredItems = model.search(
                params.query,
                params.selectedZodiacs,
                params.minAge,
                params.maxAge,
                params.maleSelected,
                params.femaleSelected,
                params.nonBinarySelected
            );
            // 然后按照当前排序方式排序
            List<CelebrityItem> sortedItems = model.sort(filteredItems, currentSortBy);
            // 更新视图
            view.updateCelebritiesList(sortedItems);
        });
        
        // 设置排序监听器
        System.out.println("Controller: Setting sort listener"); // 调试输出
        Consumer<String> sortListener = sortBy -> {
            System.out.println("\nController: Sort listener triggered with: " + sortBy); // 调试输出
            // 更新当前排序方式
            currentSortBy = sortBy;
            System.out.println("Controller: Current sort by updated to: " + currentSortBy); // 调试输出
            
            // 获取完整的名人列表
            List<CelebrityItem> items = model.getCelebrities();
            System.out.println("Controller: Retrieved " + items.size() + " celebrities"); // 调试输出
            
            // 对当前列表进行排序
            List<CelebrityItem> sortedItems = model.sort(items, sortBy);
            System.out.println("Controller: Sorted " + sortedItems.size() + " celebrities"); // 调试输出
            
            // 打印排序前后的第一个项目（如果有）
            if (!items.isEmpty() && !sortedItems.isEmpty()) {
                System.out.println("Controller: First item before sort: " + items.get(0).getCharacter().getName());
                System.out.println("Controller: First item after sort: " + sortedItems.get(0).getCharacter().getName());
            }
            
            // 更新视图
            view.updateCelebritiesList(sortedItems);
            System.out.println("Controller: View updated with sorted items"); // 调试输出
        };
        view.getMenu().setSortListener(sortListener);
        
        // 设置心愿单监听器
        view.getWishlistPanel().setAddListener(item -> {
            model.addToWishlist(item);
            view.updateWishlist(model.getWishlist());
        });
        
        view.getWishlistPanel().setRemoveListener(item -> {
            model.removeFromWishlist(item);
            view.updateWishlist(model.getWishlist());
        });
        
        view.getWishlistPanel().setSaveListener(file -> {
            try {
                model.saveWishlist(file);
            } catch (Exception e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(view.getRootPanel(),
                    "Error saving wishlist: " + e.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
            }
        });
    }

    public void loadInitialData() {
        System.out.println("\nController: Loading initial data"); // 调试输出
        // 先加载初始数据
        model.loadInitialData();
        
        // 获取当前排序方式
        String sortBy = view.getMenu().getSortBy();
        System.out.println("Controller: Initial sort by: " + sortBy); // 调试输出
        
        // 更新当前排序方式
        currentSortBy = sortBy;
        System.out.println("Controller: Current sort by set to: " + currentSortBy); // 调试输出
        
        // 获取完整的名人列表
        List<CelebrityItem> items = model.getCelebrities();
        System.out.println("Controller: Retrieved " + items.size() + " celebrities"); // 调试输出
        
        // 对数据进行排序
        List<CelebrityItem> sortedItems = model.sort(items, sortBy);
        System.out.println("Controller: Sorted " + sortedItems.size() + " celebrities"); // 调试输出
        
        // 打印排序前后的第一个项目（如果有）
        if (!items.isEmpty() && !sortedItems.isEmpty()) {
            System.out.println("Controller: First item before sort: " + items.get(0).getCharacter().getName());
            System.out.println("Controller: First item after sort: " + sortedItems.get(0).getCharacter().getName());
        }
        
        // 更新视图
        view.updateCelebritiesList(sortedItems);
        view.updateWishlist(model.getWishlist());
        System.out.println("Controller: View updated with initial data"); // 调试输出
    }
} 