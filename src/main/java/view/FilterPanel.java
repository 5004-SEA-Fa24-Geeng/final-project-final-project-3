package view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.text.AbstractDocument;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;

public class FilterPanel extends JPanel {
    private final JTextField searchField = new JTextField(15);
    private final JButton searchButton = new JButton("Search");
    private final JButton resetButton = new JButton("Reset");
    private final JTextField minAgeField = new JTextField(5);
    private final JTextField maxAgeField = new JTextField(5);
    private final JCheckBox femaleCheckBox = new JCheckBox("Female");
    private final JCheckBox maleCheckBox = new JCheckBox("Male");
    private final JCheckBox otherCheckBox = new JCheckBox("Other");
    private final JList<String> zodiacList = new JList<>(new String[]{
        "Aries", "Taurus", "Gemini", "Cancer", "Leo", "Virgo",
        "Libra", "Scorpio", "Sagittarius", "Capricorn", "Aquarius", "Pisces"
    });
    private final JLabel statusLabel = new JLabel("Ready");

    public FilterPanel() {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBorder(BorderFactory.createTitledBorder("Filter Options"));
        setPreferredSize(new Dimension(300, 0));
        initComponents();
    }

    private void initComponents() {
        // search part
        JPanel searchPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(2, 5, 2, 5);
        gbc.anchor = GridBagConstraints.WEST;
        
        // Logo in a separate line, centered
        JPanel logoPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        ImageIcon logo = new ImageIcon("src/main/resources/logo.png");
        Image scaledLogo = logo.getImage().getScaledInstance(300, 300, Image.SCALE_SMOOTH);
        JLabel logoLabel = new JLabel(new ImageIcon(scaledLogo));
        logoPanel.add(logoLabel);
        add(logoPanel);
        add(Box.createVerticalStrut(15));
        
        // search part
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 0;
        searchPanel.add(new JLabel("Search:"), gbc);
        
        gbc.gridx = 1;
        gbc.weightx = 3;
        searchPanel.add(searchField, gbc);
        
        gbc.gridx = 2;
        gbc.weightx = 0;
        searchPanel.add(searchButton, gbc);
        
        gbc.gridx = 3;
        searchPanel.add(resetButton, gbc);
        
        add(searchPanel);
        add(Box.createVerticalStrut(15));

        // age part
        JPanel agePanel = new JPanel(new GridBagLayout());
        GridBagConstraints ageGbc = new GridBagConstraints();
        ageGbc.fill = GridBagConstraints.HORIZONTAL;
        ageGbc.insets = new Insets(2, 5, 2, 5);
        ageGbc.anchor = GridBagConstraints.WEST;
        
        ageGbc.gridx = 0;
        ageGbc.weightx = 0;
        agePanel.add(new JLabel("Age Range:"), ageGbc);
        
        ageGbc.gridx = 1;
        ageGbc.weightx = 0;
        agePanel.add(minAgeField, ageGbc);
        
        ageGbc.gridx = 2;
        ageGbc.weightx = 0;
        agePanel.add(new JLabel("——"), ageGbc);
        
        ageGbc.gridx = 3;
        ageGbc.weightx = 0;
        agePanel.add(maxAgeField, ageGbc);
        
        // use BoxLayout to wrap agePanel
        JPanel ageWrapper = new JPanel();
        ageWrapper.setLayout(new BoxLayout(ageWrapper, BoxLayout.X_AXIS));
        ageWrapper.add(Box.createRigidArea(new Dimension(5, 0))); // add left margin
        ageWrapper.add(agePanel);
        add(ageWrapper);
        add(Box.createVerticalStrut(10));

        // gender part
        JPanel genderPanel = new JPanel(new GridBagLayout());
        GridBagConstraints genderGbc = new GridBagConstraints();
        genderGbc.fill = GridBagConstraints.HORIZONTAL;
        genderGbc.insets = new Insets(2, 5, 2, 5);
        genderGbc.anchor = GridBagConstraints.WEST;
        
        genderGbc.gridx = 0;
        genderGbc.weightx = 0;
        genderPanel.add(new JLabel("Gender:"), genderGbc);
        
        genderGbc.gridx = 1;
        genderGbc.weightx = 0;
        genderPanel.add(femaleCheckBox, genderGbc);
        
        genderGbc.gridx = 2;
        genderGbc.weightx = 0;
        genderPanel.add(maleCheckBox, genderGbc);
        
        genderGbc.gridx = 3;
        genderGbc.weightx = 0;
        genderPanel.add(otherCheckBox, genderGbc);
        
        // use BoxLayout to wrap genderPanel
        JPanel genderWrapper = new JPanel();
        genderWrapper.setLayout(new BoxLayout(genderWrapper, BoxLayout.X_AXIS));
        genderWrapper.add(Box.createRigidArea(new Dimension(5, 0))); // add left margin
        genderWrapper.add(genderPanel);
        add(genderWrapper);
        add(Box.createVerticalStrut(10));

        // zodiac part
        JPanel zodiacPanel = new JPanel(new GridBagLayout());
        GridBagConstraints zodiacGbc = new GridBagConstraints();
        zodiacGbc.fill = GridBagConstraints.HORIZONTAL;
        zodiacGbc.insets = new Insets(2, 5, 2, 5);
        zodiacGbc.anchor = GridBagConstraints.WEST;
        
        zodiacGbc.gridx = 0;
        zodiacGbc.weightx = 0;
        zodiacPanel.add(new JLabel("Zodiac:"), zodiacGbc);
        
        zodiacGbc.gridx = 1;
        zodiacGbc.gridwidth = 3;
        zodiacGbc.weightx = 1;
        zodiacList.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        zodiacList.setVisibleRowCount(4);
        JScrollPane zodiacScrollPane = new JScrollPane(zodiacList);
        zodiacScrollPane.setPreferredSize(new Dimension(200, 80));
        zodiacPanel.add(zodiacScrollPane, zodiacGbc);
        
        // use BoxLayout to wrap zodiacPanel
        JPanel zodiacWrapper = new JPanel();
        zodiacWrapper.setLayout(new BoxLayout(zodiacWrapper, BoxLayout.X_AXIS));
        zodiacWrapper.add(Box.createRigidArea(new Dimension(5, 0))); // add left margin
        zodiacWrapper.add(zodiacPanel);
        add(zodiacWrapper);
        add(Box.createVerticalStrut(10));

        // status label
        JPanel statusPanel = new JPanel(new GridBagLayout());
        GridBagConstraints statusGbc = new GridBagConstraints();
        statusGbc.fill = GridBagConstraints.HORIZONTAL;
        statusGbc.insets = new Insets(2, 5, 2, 5);
        statusGbc.anchor = GridBagConstraints.WEST;
        
        statusGbc.gridx = 0;
        statusGbc.weightx = 0;
        statusPanel.add(statusLabel, statusGbc);
        
        // use BoxLayout to wrap statusPanel
        JPanel statusWrapper = new JPanel();
        statusWrapper.setLayout(new BoxLayout(statusWrapper, BoxLayout.X_AXIS));
        statusWrapper.add(Box.createRigidArea(new Dimension(5, 0))); // add left margin
        statusWrapper.add(statusPanel);
        add(statusWrapper);

        // add elastic space
        add(Box.createVerticalGlue());
    }

    private void setupNumberFilter() {
        DocumentFilter filter = new DocumentFilter() {
            @Override
            public void insertString(FilterBypass fb, int offset,
                                     String string, AttributeSet attr)
                    throws BadLocationException {
                if (string.matches("\\d*")) {
                    super.insertString(fb, offset, string, attr);
                }
            }

            @Override
            public void replace(FilterBypass fb, int offset, int length,
                                String text, AttributeSet attrs)
                    throws BadLocationException {
                if (text.matches("\\d*")) {
                    super.replace(fb, offset, length, text, attrs);
                }
            }
        };

        ((AbstractDocument) minAgeField.getDocument()).setDocumentFilter(filter);
        ((AbstractDocument) maxAgeField.getDocument()).setDocumentFilter(filter);
    }

    public void addSearchListener(ActionListener listener) {
        searchButton.addActionListener(listener);
    }

    public void addResetListener(ActionListener listener) {
        resetButton.addActionListener(listener);
    }

    public String getSearchKeyword() {
        return searchField.getText();
    }

    public String getMinAge() {
        return minAgeField.getText();
    }

    public String getMaxAge() {
        return maxAgeField.getText();
    }

    public List<Integer> getSelectedGenders() {
        List<Integer> genders = new ArrayList<>();
        if (femaleCheckBox.isSelected()) genders.add(1);
        if (maleCheckBox.isSelected()) genders.add(2);
        if (otherCheckBox.isSelected()) genders.add(3);
        return genders;
    }

    public List<String> getSelectedZodiacs() {
        return zodiacList.getSelectedValuesList();
    }

    public void resetFilters() {
        searchField.setText("");
        minAgeField.setText("");
        maxAgeField.setText("");
        femaleCheckBox.setSelected(false);
        maleCheckBox.setSelected(false);
        otherCheckBox.setSelected(false);
        zodiacList.clearSelection();
    }

    public void setStatusMessage(String message) {
        statusLabel.setText(message);
    }
}
