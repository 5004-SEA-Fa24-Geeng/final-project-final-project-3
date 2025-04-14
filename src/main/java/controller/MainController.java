package controller;

import model.CharactersCollection;
import model.WishList;
import view.MainFrame;
import view.CharacterListPanel;
import view.WishListPanel;

/**
 * MainController serves as the central coordinator of the application.
 * It initializes models, views, and sub-controllers, and manages view updates and event wiring.
 */
public class MainController {

    /**
     * The main application frame that contains all UI components.
     */
    private final MainFrame mainFrame;

    /**
     * The model that manages all character data and filtering.
     */
    private final CharactersCollection charactersCollection;

    /**
     * The model that stores the user's wish list.
     */
    private final WishList wishList;

    /**
     * Controller responsible for sorting logic and events.
     */
    private final SortController sortController;

    /**
     * Controller responsible for filtering logic and events.
     */
    private final FilterController filterController;

    /**
     * Controller responsible for wish list operations and synchronization with UI.
     */
    private final WishListController wishListController;

    /**
     * Constructs and initializes the MainController.
     * Sets up all models, views, controllers, event listeners, and displays the main UI.
     */
    public MainController() {
        // Initialize models
        charactersCollection = new CharactersCollection();
        wishList = new WishList();

        // Initialize main frame
        mainFrame = new MainFrame();

        // Initialize sort and filter controllers with refresh callbacks
        sortController = new SortController(
                charactersCollection,
                mainFrame.getSortPanel(),
                this::refreshCharacterList
        );

        filterController = new FilterController(
                charactersCollection,
                mainFrame.getFilterPanel(),
                this::refreshCharacterList
        );

        // Access sub-panels from main frame
        CharacterListPanel characterListPanel = mainFrame.getCharacterListPanel();
        WishListPanel wishListPanel = mainFrame.getWishListPanel();

        // Initialize wish list controller
        wishListController = new WishListController(
                wishList,
                wishListPanel,
                characterListPanel,
                charactersCollection
        );

        // Debug: verify if listener is set on character panel
        System.out.println("CharacterListPanel listener set: " + characterListPanel.isAddToWishListListenerSet());

        // Display UI
        mainFrame.setVisible(true);
    }

    /**
     * Constructs a MainController with injected dependencies.
     * This constructor is primarily used for testing.
     *
     * @param mainFrame The main application frame
     * @param charactersCollection The characters collection model
     * @param wishList The wish list model
     * @param sortController The sort controller
     * @param filterController The filter controller
     * @param wishListController The wish list controller
     */
    public MainController(MainFrame mainFrame,
                         CharactersCollection charactersCollection,
                         WishList wishList,
                         SortController sortController,
                         FilterController filterController,
                         WishListController wishListController) {
        this.mainFrame = mainFrame;
        this.charactersCollection = charactersCollection;
        this.wishList = wishList;
        this.sortController = sortController;
        this.filterController = filterController;
        this.wishListController = wishListController;
        
        // Display UI
        mainFrame.setVisible(true);
    }

    /**
     * Starts the application logic by loading data and initializing views.
     * This method should be called after the controller is constructed.
     */
    public void start() {
        charactersCollection.loadData();
        refreshCharacterList();
        sortController.updateView();
        filterController.updateView();
        wishListController.updateView();
    }

    /**
     * Updates the character list panel with the currently filtered characters.
     * Used as a callback after filtering or sorting.
     */
    private void refreshCharacterList() {
        mainFrame.getCharacterListPanel().setCharacterList(
                charactersCollection.getFilteredCharacters()
        );
    }
}
