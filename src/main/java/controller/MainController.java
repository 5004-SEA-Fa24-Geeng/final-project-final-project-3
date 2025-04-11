package controller;

import model.CharactersCollection;
import model.WishList;
import view.MainFrame;
import view.CharacterListPanel;
import view.WishListPanel;

public class MainController {
    private final MainFrame mainFrame;
    private final CharactersCollection charactersCollection;
    private final WishList wishList;
    private final SortController sortController;
    private final FilterController filterController;
    private final WishListController wishListController;

    public MainController() {
        // 初始化模型
        charactersCollection = new CharactersCollection();
        wishList = new WishList();

        // 初始化视图
        mainFrame = new MainFrame();

        // 初始化控制器
        sortController = new SortController(charactersCollection, mainFrame.getSortPanel(), this::refreshCharacterList);
        filterController = new FilterController(charactersCollection, mainFrame.getFilterPanel(), this::refreshCharacterList);
        
        // 获取面板引用
        CharacterListPanel characterListPanel = mainFrame.getCharacterListPanel();
        WishListPanel wishListPanel = mainFrame.getWishListPanel();
        
        // 创建并设置WishListController
        wishListController = new WishListController(wishList, wishListPanel, characterListPanel, charactersCollection);
        
        // 验证监听器设置
        System.out.println("CharacterListPanel listener set: " + characterListPanel.isAddToWishListListenerSet());

        // 设置视图可见
        mainFrame.setVisible(true);
    }

    public void start() {
        // 初始化数据
        charactersCollection.loadData();
        wishList.initWishList();

        // 更新视图
        refreshCharacterList();
        sortController.updateView();
        filterController.updateView();
        wishListController.updateView();
    }

    private void refreshCharacterList() {
        mainFrame.getCharacterListPanel().setCharacterList(charactersCollection.getFilteredCharacters());
    }
} 