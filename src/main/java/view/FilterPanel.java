package view;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.text.AbstractDocument;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

/**
 * A panel for filtering characters based on various criteria such as
 * keyword, age range, gender, and zodiac sign. Provides a logo,
 * search controls, and a status label.
 */
public class FilterPanel extends JPanel {
    private static final int LOGO_WIDTH = 300;
    private static final int LOGO_HEIGHT = 300;
    private static final int FIELD_WIDTH = 15;
    private static final int VERTICAL_GAP = 10;

    private final JTextField searchField = new JTextField(FIELD_WIDTH);
    private final JButton searchButton = new JButton("Search");
    private final JButton resetButton = new JButton("Reset");
    private final JTextField minAgeField = new JTextField(5);
    private final JTextField maxAgeField = new JTextField(5);
    private final JCheckBox femaleCheckBox = new JCheckBox("Female");
    private final JCheckBox maleCheckBox = new JCheckBox("Male");
    private final JCheckBox otherCheckBox = new JCheckBox("Non-binary");
    private final JList<String> zodiacList = new JList<>(new String[]{
            "Aries ♈", "Taurus ♉", "Gemini ♊", "Cancer ♋", "Leo ♌", "Virgo ♍",
            "Libra ♎", "Scorpio ♏", "Sagittarius ♐", "Capricorn ♑", "Aquarius ♒", "Pisces ♓"
    });
    private final JLabel statusLabel = new JLabel("Ready");

    /**
     * Constructs the filter panel with initialized components and layout.
     */
    public FilterPanel() {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBorder(BorderFactory.createTitledBorder("Filter Options"));
        setPreferredSize(new Dimension(300, 0));

        addLogo();
        add(Box.createVerticalStrut(VERTICAL_GAP));
        addSearchPanel();
        add(Box.createVerticalStrut(VERTICAL_GAP));
        addAgePanel();
        add(Box.createVerticalStrut(VERTICAL_GAP));
        addGenderPanel();
        add(Box.createVerticalStrut(VERTICAL_GAP));
        addZodiacPanel();
        add(Box.createVerticalStrut(VERTICAL_GAP));
        addStatusPanel();
        add(Box.createVerticalGlue());

        setupNumberFilter();
    }

    /**
     * Adds the logo to the top of the panel.
     */
    private void addLogo() {
        JPanel logoPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        try {
            ImageIcon logo = new ImageIcon("src/main/resources/logo.png");
            Image scaledLogo = logo.getImage().getScaledInstance(LOGO_WIDTH, LOGO_HEIGHT, Image.SCALE_SMOOTH);
            logoPanel.add(new JLabel(new ImageIcon(scaledLogo)));
        } catch (Exception e) {
            logoPanel.add(new JLabel("Logo not found"));
        }
        add(logoPanel);
    }

    /**
     * Adds the search input field and buttons.
     */
    private void addSearchPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = createGbc();

        gbc.gridx = 0;
        panel.add(new JLabel("Search:"), gbc);

        gbc.gridx = 1;
        gbc.weightx = 3;
        panel.add(searchField, gbc);

        gbc.gridx = 2;
        gbc.weightx = 0;
        panel.add(searchButton, gbc);

        gbc.gridx = 3;
        panel.add(resetButton, gbc);

        add(panel);
    }

    /**
     * Adds the age range input fields.
     */
    private void addAgePanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = createGbc();

        gbc.gridx = 0;
        panel.add(new JLabel("Age Range:"), gbc);

        gbc.gridx = 1;
        panel.add(minAgeField, gbc);

        gbc.gridx = 2;
        panel.add(new JLabel("——"), gbc);

        gbc.gridx = 3;
        panel.add(maxAgeField, gbc);

        addSection(panel);
    }

    /**
     * Adds gender selection checkboxes.
     */
    private void addGenderPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = createGbc();

        gbc.gridx = 0;
        panel.add(new JLabel("Gender:"), gbc);

        gbc.gridx = 1;
        panel.add(femaleCheckBox, gbc);

        gbc.gridx = 2;
        panel.add(maleCheckBox, gbc);

        gbc.gridx = 3;
        panel.add(otherCheckBox, gbc);

        addSection(panel);
    }

    /**
     * Adds the zodiac sign multi-select list.
     */
    private void addZodiacPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = createGbc();

        gbc.gridx = 0;
        panel.add(new JLabel("Zodiac:"), gbc);

        gbc.gridx = 1;
        gbc.gridwidth = 3;
        zodiacList.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        zodiacList.setVisibleRowCount(4);
        JScrollPane scrollPane = new JScrollPane(zodiacList);
        scrollPane.setPreferredSize(new Dimension(200, 80));
        panel.add(scrollPane, gbc);

        addSection(panel);
    }

    /**
     * Adds the status label panel.
     */
    private void addStatusPanel() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        statusLabel.setFont(statusLabel.getFont().deriveFont(Font.PLAIN, 14f));
        panel.add(statusLabel);
        add(panel);
    }

    /**
     * Creates default GridBagConstraints with common spacing and alignment.
     *
     * @return a configured GridBagConstraints instance
     */
    private GridBagConstraints createGbc() {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(2, 5, 2, 5);
        gbc.anchor = GridBagConstraints.WEST;
        return gbc;
    }

    /**
     * Wraps and adds a section component with padding.
     *
     * @param component the component to add
     */
    private void addSection(JComponent component) {
        JPanel wrapper = new JPanel();
        wrapper.setLayout(new BoxLayout(wrapper, BoxLayout.X_AXIS));
        wrapper.add(Box.createRigidArea(new Dimension(5, 0)));
        wrapper.add(component);
        add(wrapper);
    }

    /**
     * Restricts the min and max age fields to numeric input only.
     */
    private void setupNumberFilter() {
        DocumentFilter filter = new DocumentFilter() {
            @Override
            public void insertString(FilterBypass fb, int offset, String string, AttributeSet attr)
                    throws BadLocationException {
                if (string.matches("\\d*")) {
                    super.insertString(fb, offset, string, attr);
                }
            }

            @Override
            public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attrs)
                    throws BadLocationException {
                if (text.matches("\\d*")) {
                    super.replace(fb, offset, length, text, attrs);
                }
            }
        };
        ((AbstractDocument) minAgeField.getDocument()).setDocumentFilter(filter);
        ((AbstractDocument) maxAgeField.getDocument()).setDocumentFilter(filter);
    }

    /**
     * Registers an ActionListener for the search button.
     *
     * @param listener the ActionListener to register
     */
    public void addSearchListener(ActionListener listener) {
        searchButton.addActionListener(listener);
    }

    /**
     * Registers an ActionListener for the reset button.
     *
     * @param listener the ActionListener to register
     */
    public void addResetListener(ActionListener listener) {
        resetButton.addActionListener(listener);
    }

    /**
     * Returns the keyword entered in the search field.
     *
     * @return the keyword string
     */
    public String getSearchKeyword() {
        return searchField.getText();
    }

    /**
     * Returns the minimum age entered.
     *
     * @return the minimum age string
     */
    public String getMinAge() {
        return minAgeField.getText();
    }

    /**
     * Returns the maximum age entered.
     *
     * @return the maximum age string
     */
    public String getMaxAge() {
        return maxAgeField.getText();
    }

    /**
     * Returns a list of selected gender codes.
     * 1 = Female, 2 = Male, 3 = Non-binary
     *
     * @return list of selected gender codes
     */
    public List<Integer> getSelectedGenders() {
        List<Integer> genders = new ArrayList<>();
        if (femaleCheckBox.isSelected()) genders.add(1);
        if (maleCheckBox.isSelected()) genders.add(2);
        if (otherCheckBox.isSelected()) genders.add(3);
        return genders;
    }

    /**
     * Returns a list of selected zodiac signs.
     *
     * @return list of selected zodiac sign strings
     */
    public List<String> getSelectedZodiacs() {
        return zodiacList.getSelectedValuesList();
    }

    /**
     * Resets all filters and form fields to default values.
     */
    public void resetFilters() {
        searchField.setText("");
        minAgeField.setText("");
        maxAgeField.setText("");
        femaleCheckBox.setSelected(false);
        maleCheckBox.setSelected(false);
        otherCheckBox.setSelected(false);
        zodiacList.clearSelection();
    }

    /**
     * Sets the message and color of the status label.
     *
     * @param message the message to display
     * @param color   the color of the message text
     */
    public void setStatusMessage(String message, Color color) {
        statusLabel.setForeground(color);
        statusLabel.setText(message);
    }
}
