# Design Documents

You may have multiple design documents for this project. Place them all in this folder. File naming is up to you, but it should be clear what the document is about. At the bare minimum, you will want a pre/post UML diagram for the project. 

```mermaid
classDiagram
direction LR
class AddToWishListListener {
<<Interface>>
+ onAdd(int) void
  }
  class CharacterListPanel {
+ CharacterListPanel(SortPanel)
- AddToWishListListener addToWishListListener
- createGenderLabel(int) JLabel
- createButtonPanel(CharacterRecord) JPanel
- getGenderString(int) String
- configureAddButton(JButton) void
- getZodiacSign(String) String
- createImagePanel(CharacterRecord) JPanel
- getGenderIcon(int) ImageIcon?
- updateCount() void
+ updateView() void
- createLabel(String) JLabel
- createInfoPanel(CharacterRecord) JPanel
- initComponents() void
- addCharacterPanel(CharacterRecord) void
  boolean addToWishListListenerSet
  AddToWishListListener addToWishListListener
  List~CharacterRecord~ characterList
  }
  class CharacterRecord {
+ CharacterRecord(Integer, String, Integer, Integer, double, String, String, String, String, String)
+ CharacterRecord()
- String nationality
- Integer gender
- String occupation
- String birthday
- Integer age
- Integer id
- String name
- String zodiacSign
- String profile
- double popularity
+ toString() String
  double popularity
  String name
  String profile
  Integer age
  String occupation
  Integer gender
  String nationality
  Integer id
  String birthday
  String genderString
  String zodiacSign
  }
  class CharactersCollection {
+ CharactersCollection()
- List~CharacterRecord~ allCharacters
- List~CharacterRecord~ filteredCharacters
+ applyFilters(Filter) void
+ loadData() void
+ getSorted(List~CharacterRecord~, Comparator~CharacterRecord~) List~CharacterRecord~
  List~CharacterRecord~ filteredCharacters
  List~CharacterRecord~ allCharacters
  }
  class Filter {
  <<Interface>>
+ matches(CharacterRecord) boolean
+ and(Filter) Filter
  }
  class FilterController {
+ FilterController(CharactersCollection, FilterPanel, Runnable)
- buildCompositeFilter() Filter
- parseAge(String, int) int
+ updateView() void
- initEventHandlers() void
- buildAgeFilter() Filter?
+ onReset(ActionEvent) void
+ onSearch(ActionEvent) void
  }
  class FilterPanel {
+ FilterPanel()
- addSection(JComponent) void
+ addResetListener(ActionListener) void
- setupNumberFilter() void
+ resetFilters() void
- createGbc() GridBagConstraints
+ addSearchListener(ActionListener) void
- addGenderPanel() void
- addStatusPanel() void
- addZodiacPanel() void
+ setStatusMessage(String, Color) void
- addAgePanel() void
- addSearchPanel() void
  List~Integer~ selectedGenders
  String searchKeyword
  List~String~ selectedZodiacs
  String maxAge
  String minAge
  }
  class Heart {
  ~ Heart(int)
  }
  class IController {
  <<Interface>>
+ updateView() void
  }
  class IFilterController {
  <<Interface>>
+ onSearch(ActionEvent) void
+ onReset(ActionEvent) void
  }
  class ISortController {
  <<Interface>>
+ handleSortItems(Comparator~CharacterRecord~) Response
  }
  class IWishListController {
  <<Interface>>
+ handleAddToWishList(int) Response
+ handleRemoveSingleCharacter(int) Response
+ handleClearWishList() Response
+ saveToFile() void
  }
  class ImageCache {
+ ImageCache()
+ getImage(String) ImageIcon?
+ clearCache() void
  }
  class JSONFileHandler {
+ JSONFileHandler()
+ saveWishListToFile(String, Set~CharacterRecord~) void
+ readJsonFile(String) List~CharacterRecord~
- processCharacters(List~CharacterRecord~) List~CharacterRecord~
- calculateZodiacSign(int, int) String
- calculateAge(int, int, int) int
  }
  class Main {
+ Main()
+ main(String[]) void
  }
  class MainController {
+ MainController(MainFrame, CharactersCollection, WishList, SortController, FilterController, WishListController)
+ MainController()
- refreshCharacterList() void
+ start() void
  }
  class MainFrame {
+ MainFrame()
- CharacterListPanel characterListPanel
- WishListPanel wishListPanel
- FilterPanel filterPanel
- SortPanel sortPanel
- initComponents() void
  FilterPanel filterPanel
  SortPanel sortPanel
  WishListPanel wishListPanel
  CharacterListPanel characterListPanel
  }
  class RemoveCharacterListener {
  <<Interface>>
+ onRemove(int) void
  }
  class Response {
+ Response(int, String)
- int status
- String message
+ success(String) Response
+ failure(String) Response
  String message
  int status
  }
  class SortComparators {
+ SortComparators()
  }
  class SortController {
+ SortController(CharactersCollection, SortPanel, Runnable)
+ handleSortItems(Comparator~CharacterRecord~) Response
- initEventHandlers() void
+ updateView() void
  }
  class SortPanel {
+ SortPanel()
+ updateView() void
- initComponents() void
+ addSortListener(ActionListener) void
  String selectedSortOption
  String sortOrder
  int count
  }
  class TitleHeartAnimationPanel {
+ TitleHeartAnimationPanel(int, int)
# paintComponent(Graphics) void
}
class WishList {
+ WishList()
- Set~CharacterRecord~ wishList
+ addCharacter(int) boolean
+ removeAllCharacters() boolean
+ removeSingleCharacter(int) boolean
+ getCharacterById(int) CharacterRecord
  Set~CharacterRecord~ wishList
  }
  class WishListController {
+ WishListController(WishList, WishListPanel, CharacterListPanel, CharactersCollection)
+ handleClearWishList() Response
+ handleAddToWishList(int) Response
+ updateView() void
+ handleRemoveSingleCharacter(int) Response
+ saveToFile() void
- initListeners(CharacterListPanel) void
  Set~CharacterRecord~ wishList
  }
  class WishListPanel {
+ WishListPanel()
- RemoveCharacterListener removeCharacterListener
+ updateView() void
- getGenderString(int) String
- initComponents() void
- addCharacterPanel(CharacterRecord) void
+ addClearWishListListener(ActionListener) void
+ addSaveToFileListener(ActionListener) void
  List~CharacterRecord~ wishList
  RemoveCharacterListener removeCharacterListener
  }

CharacterListPanel  -->  AddToWishListListener
FilterController  ..>  IFilterController
TitleHeartAnimationPanel  -->  Heart
IFilterController  -->  IController
ISortController  -->  IController
IWishListController  -->  IController
WishListPanel  -->  RemoveCharacterListener
SortController  ..>  ISortController
WishListController  ..>  IWishListController 
```