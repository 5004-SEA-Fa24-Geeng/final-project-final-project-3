package view;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.text.AbstractDocument;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class FilterPanel extends JPanel {
    private final JTextField keywordField = new JTextField(15);
    private final JCheckBox maleCheckBox = new JCheckBox("male");
    private final JCheckBox femaleCheckBox = new JCheckBox("female");
    private final JCheckBox otherCheckBox = new JCheckBox("non-binary");
    private final JTextField minAgeField = new JTextField(5);
    private final JTextField maxAgeField = new JTextField(5);
    // TODO: 检查星座是否达到预期
    private final Map<String, JCheckBox> zodiacCheckboxes = new HashMap<>();
    private final JButton searchButton = new JButton("Search");
    private final JButton resetButton = new JButton("Reset");
    // TODO: 检查status如何呈现
    private final JLabel statusLabel = new JLabel("Ready");

    public FilterPanel() {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        initComponents();
        setupNumberFilter();
    }

    private void initComponents() {
        JPanel namePanel = createTitledPanel("Name");
        namePanel.add(keywordField);

        JPanel genderPanel = createTitledPanel("Gender");
        genderPanel.add(maleCheckBox);
        genderPanel.add(femaleCheckBox);
        genderPanel.add(otherCheckBox);
        maleCheckBox.setSelected(true);
        femaleCheckBox.setSelected(true);
        otherCheckBox.setSelected(true);

        JPanel agePanel = createTitledPanel("Age");
        agePanel.add(minAgeField);
        agePanel.add(new JLabel("-"));
        agePanel.add(maxAgeField);

        JPanel zodiacPanel = createTitledPanel("Sign");
        zodiacPanel.setLayout(new GridLayout(0, 3, 5, 5)); // 3列
        String[] zodiacs = {
                "Aries", "Taurus", "Gemini", "Cancer", "Leo", "Virgo",
                "Libra", "Scorpio", "Sagittarius", "Capricorn", "Aquarius", "Pisces"
        };
        for (String zodiac : zodiacs) {
            JCheckBox cb = new JCheckBox(zodiac);
            zodiacCheckboxes.put(zodiac, cb);
            zodiacPanel.add(cb);
        }

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(searchButton);
        buttonPanel.add(resetButton);

        add(namePanel);
        add(genderPanel);
        add(agePanel);
        add(zodiacPanel);
        add(buttonPanel);
        add(statusLabel);
    }

    public String getSearchKeyword() {
        return keywordField.getText();
    }

    private JPanel createTitledPanel(String title) {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panel.setBorder(new TitledBorder(title));
        return panel;
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

    public List<Integer> getSelectedGenders() {
        List<Integer> genders = new ArrayList<>();
        if (maleCheckBox.isSelected()) genders.add(2);
        if (femaleCheckBox.isSelected()) genders.add(1);
        if (otherCheckBox.isSelected()) genders.add(3);
        return genders;
    }

    public String getMinAge() { return minAgeField.getText().trim(); }
    public String getMaxAge() { return maxAgeField.getText().trim(); }

    public List<String> getSelectedZodiacs() {
        return zodiacCheckboxes.entrySet().stream()
                .filter(entry -> entry.getValue().isSelected())
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());
    }

    public void setStatusMessage(String message) {
        statusLabel.setText(message);
    }

    public void resetFilters() {
        maleCheckBox.setSelected(true);
        femaleCheckBox.setSelected(true);
        otherCheckBox.setSelected(true);
        minAgeField.setText("");
        maxAgeField.setText("");
        zodiacCheckboxes.values().forEach(cb -> cb.setSelected(false));
        setStatusMessage("Filters reset.");
    }

    public void addSearchListener(ActionListener listener) {
        searchButton.addActionListener(listener);
    }

    public void addResetListener(ActionListener listener) {
        resetButton.addActionListener(listener);
    }
}
