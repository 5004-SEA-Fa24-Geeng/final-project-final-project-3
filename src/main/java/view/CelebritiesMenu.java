package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.util.List;
import java.util.function.Consumer;

/**
 * celebrities menu
 * this class is used to create the menu for the celebrities
 */
public class CelebritiesMenu {
    private final JPanel menuPanel;
    private final JTextField searchField;
    private final JTextField minAgeField;
    private final JTextField maxAgeField;
    private final JList<String> zodiacList;
    private final JCheckBox maleCheckBox;
    private final JCheckBox femaleCheckBox;
    private final JCheckBox nonBinaryCheckBox;
    private final JComboBox<String> sortComboBox;
    private final JButton searchButton;
    private final JButton resetButton;
    private Consumer<SearchParams> searchListener;
    private Consumer<String> sortListener;

    public static class SearchParams {
        public final String query;
        public final List<String> selectedZodiacs;
        public final int minAge;
        public final int maxAge;
        public final boolean maleSelected;
        public final boolean femaleSelected;
        public final boolean nonBinarySelected;

        public SearchParams(String query, List<String> selectedZodiacs, int minAge, 
                          int maxAge, boolean maleSelected, boolean femaleSelected, 
                          boolean nonBinarySelected) {
            this.query = query;
            this.selectedZodiacs = selectedZodiacs;
            this.minAge = minAge;
            this.maxAge = maxAge;
            this.maleSelected = maleSelected;
            this.femaleSelected = femaleSelected;
            this.nonBinarySelected = nonBinarySelected;
        }
    }

    /**
     * constructor
     * @param searchListener
     * @param sortListener
     */
    public CelebritiesMenu(Consumer<SearchParams> searchListener, Consumer<String> sortListener) {
        this.searchListener = searchListener;
        this.sortListener = sortListener;
        
        // create menu panel
        menuPanel = new JPanel(new GridBagLayout());
        menuPanel.setBackground(new Color(245, 245, 245));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(8, 8, 8, 8);

        // set label font
        Font labelFont = new Font("Segoe UI", Font.PLAIN, 14);
        Font inputFont = new Font("Segoe UI", Font.PLAIN, 13);

        // first row: search box and sort
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 0.0;
        JLabel searchLabel = new JLabel("Name:");
        searchLabel.setFont(labelFont);
        menuPanel.add(searchLabel, gbc);

        gbc.gridx = 1;
        gbc.weightx = 0.3;
        searchField = new JTextField(8);
        searchField.setFont(inputFont);
        searchField.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(200, 200, 200)),
            BorderFactory.createEmptyBorder(5, 5, 5, 5)
        ));
        menuPanel.add(searchField, gbc);

        gbc.gridx = 2;
        gbc.weightx = 0.0;
        JLabel sortLabel = new JLabel("Sort by:");
        sortLabel.setFont(labelFont);
        menuPanel.add(sortLabel, gbc);

        gbc.gridx = 3;
        gbc.weightx = 0.0;
        // create sort options
        sortComboBox = new JComboBox<>(new String[]{"Alphabetical", "Popularity"});
        sortComboBox.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        sortComboBox.setBackground(Color.WHITE);
        sortComboBox.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200)));
        sortComboBox.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        // add sort options listener
        sortComboBox.addActionListener(e -> {
            String selectedSort = (String) sortComboBox.getSelectedItem();
            System.out.println("Sort ComboBox: Selected sort changed to: " + selectedSort); // 调试输出
            if (this.sortListener != null && selectedSort != null) {
                System.out.println("Sort ComboBox: Calling sort listener with: " + selectedSort); // 调试输出
                this.sortListener.accept(selectedSort);
            } else {
                System.out.println("Sort ComboBox: Sort listener is " + (this.sortListener == null ? "null" : "not null")); // 调试输出
                System.out.println("Sort ComboBox: Selected sort is " + (selectedSort == null ? "null" : "not null")); // 调试输出
            }
        });
        
        menuPanel.add(sortComboBox, gbc);

        // second row: age range and zodiac
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weightx = 0.0;
        JLabel ageLabel = new JLabel("Age:");
        ageLabel.setFont(labelFont);
        menuPanel.add(ageLabel, gbc);

        gbc.gridx = 1;
        gbc.weightx = 0.0;
        JPanel agePanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 0));
        agePanel.setBackground(new Color(245, 245, 245));
        minAgeField = new JTextField(4);
        maxAgeField = new JTextField(4);
        minAgeField.setFont(inputFont);
        maxAgeField.setFont(inputFont);
        minAgeField.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200)));
        maxAgeField.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200)));
        agePanel.add(minAgeField);
        agePanel.add(new JLabel("to"));
        agePanel.add(maxAgeField);
        menuPanel.add(agePanel, gbc);

        gbc.gridx = 2;
        gbc.weightx = 0.0;
        JLabel zodiacLabel = new JLabel("Zodiac:");
        zodiacLabel.setFont(labelFont);
        menuPanel.add(zodiacLabel, gbc);

        gbc.gridx = 3;
        gbc.weightx = 0.0;
        String[] zodiacs = {"Aries", "Taurus", "Gemini", "Cancer", "Leo", "Virgo", 
                           "Libra", "Scorpio", "Sagittarius", "Capricorn", "Aquarius", "Pisces"};
        DefaultListModel<String> zodiacModel = new DefaultListModel<>();
        for (String zodiac : zodiacs) {
            zodiacModel.addElement(zodiac);
        }
        zodiacList = new JList<>(zodiacModel);
        zodiacList.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        zodiacList.setFont(inputFont);
        zodiacList.setVisibleRowCount(3);
        zodiacList.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200)));
        JScrollPane zodiacScrollPane = new JScrollPane(zodiacList);
        zodiacScrollPane.setPreferredSize(new Dimension(150, 80));
        menuPanel.add(zodiacScrollPane, gbc);

        // third row: gender selection and buttons
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.weightx = 0.0;
        JLabel genderLabel = new JLabel("Gender:");
        genderLabel.setFont(labelFont);
        menuPanel.add(genderLabel, gbc);

        gbc.gridx = 1;
        gbc.weightx = 0.0;
        JPanel genderPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
        genderPanel.setBackground(new Color(245, 245, 245));
        maleCheckBox = new JCheckBox("Male");
        femaleCheckBox = new JCheckBox("Female");
        nonBinaryCheckBox = new JCheckBox("Non-binary");
        maleCheckBox.setFont(inputFont);
        femaleCheckBox.setFont(inputFont);
        nonBinaryCheckBox.setFont(inputFont);
        genderPanel.add(maleCheckBox);
        genderPanel.add(femaleCheckBox);
        genderPanel.add(nonBinaryCheckBox);
        menuPanel.add(genderPanel, gbc);

        gbc.gridx = 2;
        gbc.gridwidth = 1;
        gbc.weightx = 0.0;
        searchButton = new JButton("Search");
        searchButton.setFont(new Font("Segoe UI", Font.BOLD, 14));
        searchButton.setBackground(new Color(66, 133, 244));
        searchButton.setForeground(Color.WHITE);
        searchButton.setBorderPainted(false);
        searchButton.setFocusPainted(false);
        searchButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        searchButton.addActionListener(e -> performSearch());
        menuPanel.add(searchButton, gbc);

        gbc.gridx = 3;
        resetButton = new JButton("Reset");
        resetButton.setFont(new Font("Segoe UI", Font.BOLD, 14));
        resetButton.setBackground(new Color(234, 67, 53));
        resetButton.setForeground(Color.WHITE);
        resetButton.setBorderPainted(false);
        resetButton.setFocusPainted(false);
        resetButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        resetButton.addActionListener(e -> {
            reset();
            performSearch();
        });
        menuPanel.add(resetButton, gbc);

        // add event listeners
        addEventListeners();
    }

    /**
     * add event listeners
     * this method is used to add event listeners to the gender checkboxes, zodiac list, age input fields, and search button
     */
    private void addEventListeners() {
        // add event listeners to gender checkboxes
        maleCheckBox.addActionListener(e -> performSearch());
        femaleCheckBox.addActionListener(e -> performSearch());
        nonBinaryCheckBox.addActionListener(e -> performSearch());
        
        // add listener to zodiac list
        zodiacList.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                performSearch();
            }
        });
        
        // add focus listener to age input fields
        minAgeField.addFocusListener(new FocusAdapter() {
            @Override
            public void focusLost(FocusEvent e) {
                performSearch();
            }
        });
        
        maxAgeField.addFocusListener(new FocusAdapter() {
            @Override
            public void focusLost(FocusEvent e) {
                performSearch();
            }
        });
        
        // add document listener to search field
        searchField.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                SwingUtilities.invokeLater(() -> performSearch());
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                SwingUtilities.invokeLater(() -> performSearch());
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                SwingUtilities.invokeLater(() -> performSearch());
            }
        });
    }

    /**
     * perform search
     * this method is used to perform the search
     */
    private void performSearch() {
        if (searchListener != null) {
            String query = searchField.getText();
            List<String> selectedZodiacs = zodiacList.getSelectedValuesList();
            int minAge = parseAge(minAgeField.getText());
            int maxAge = parseAge(maxAgeField.getText());
            boolean maleSelected = maleCheckBox.isSelected();
            boolean femaleSelected = femaleCheckBox.isSelected();
            boolean nonBinarySelected = nonBinaryCheckBox.isSelected();
            
            searchListener.accept(new SearchParams(query, selectedZodiacs, minAge, 
                maxAge, maleSelected, femaleSelected, nonBinarySelected));
        }
    }

    /**
     * reset
     * this method is used to reset the search parameters
     */
    public void reset() {
        // reset search field
        searchField.setText("");
        
        // reset age range
        minAgeField.setText("");
        maxAgeField.setText("");
        
        // reset zodiac selection
        zodiacList.clearSelection();
        
        // reset gender selection
        maleCheckBox.setSelected(false);
        femaleCheckBox.setSelected(false);
        nonBinaryCheckBox.setSelected(false);
        
        // reset sort option
        sortComboBox.setSelectedItem("Alphabetical");
    }

    /**
     * set search listener
     * @param listener
     */
    public void setSearchListener(Consumer<SearchParams> listener) {
        this.searchListener = listener;
    }

    /**
     * set sort listener
     * @param listener
     */
    public void setSortListener(Consumer<String> listener) {
        System.out.println("CelebritiesMenu: Setting sort listener to " + (listener == null ? "null" : "not null")); // debug output
        this.sortListener = listener;
    }

    /**
     * get menu panel
     * @return menu panel
     */
    public JPanel getMenuPanel() {
        return menuPanel;
    }

    /**
     * get sort by
     * @return sort by
     */
    public String getSortBy() {
        return (String) sortComboBox.getSelectedItem();
    }

    /**
     * parse age
     * @param ageText
     * @return age
     */
    private int parseAge(String ageText) {
        try {
            return Integer.parseInt(ageText);
        } catch (NumberFormatException e) {
            return -1;
        }
    }
} 