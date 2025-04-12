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
        // initialize models
        charactersCollection = new CharactersCollection();
        wishList = new WishList();

        // initialize views
        mainFrame = new MainFrame();

        // initialize controllers
        sortController = new SortController(charactersCollection, mainFrame.getSortPanel(), this::refreshCharacterList);
        filterController = new FilterController(charactersCollection, mainFrame.getFilterPanel(), this::refreshCharacterList);
        
        // get panel references
        CharacterListPanel characterListPanel = mainFrame.getCharacterListPanel();
        WishListPanel wishListPanel = mainFrame.getWishListPanel();
        
        // create and set WishListController
        wishListController = new WishListController(wishList, wishListPanel, characterListPanel, charactersCollection);
        
        // verify listener settings
        System.out.println("CharacterListPanel listener set: " + characterListPanel.isAddToWishListListenerSet());

        // set view visible
        mainFrame.setVisible(true);
    }

    public void start() {
        // initialize data
        charactersCollection.loadData();
        wishList.initWishList();

        // update views
        refreshCharacterList();
        sortController.updateView();
        filterController.updateView();
        wishListController.updateView();
    }

    private void refreshCharacterList() {
        mainFrame.getCharacterListPanel().setCharacterList(charactersCollection.getFilteredCharacters());
    }
} 