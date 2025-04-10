package View;

import java.util.List;
import javax.swing.*;
import model.CharacterRecord;
import java.awt.*;
import java.awt.event.ActionListener;

public class WishListPanel extends JPanel {
    private final DefaultListModel<CharacterRecord> listModel = new DefaultListModel<>();
    private final JList<CharacterRecord> wishList = new JList<>(listModel);
    private final JButton clearButton = new JButton("Clear");
    private final JButton saveButton = new JButton("Save to File");
    private RemoveCharacterListener removeCharacterListener;

    public WishListPanel() {
        setLayout(new BorderLayout());
        setPreferredSize(new Dimension(300, 0));
        initComponents();
    }

    // The panel provides a way to register observers
    public void setRemoveCharacterListener(RemoveCharacterListener listener) {
        this.removeCharacterListener = listener;
    }

    private void initComponents() {
        // 自定义渲染器
        wishList.setCellRenderer(new ListCellRenderer<CharacterRecord>() {
            @Override
            public Component getListCellRendererComponent(JList<? extends CharacterRecord> list, CharacterRecord value, int index, boolean isSelected, boolean cellHasFocus) {
                JPanel panel = new JPanel(new BorderLayout());

                JPanel infoPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
                JLabel nameLabel = new JLabel(value.getName());
                // TODO：图片显示逻辑（调用api？）
                JLabel profileLabel = new JLabel(new ImageIcon(value.getProfile()));
                infoPanel.add(profileLabel);
                infoPanel.add(nameLabel);

                JButton removeButton = new JButton("Remove");
                // Notifies the observer when an event occurs (button click):
                // When clicked, the panel calls the pre-registered onRemove method
                removeButton.addActionListener(e -> {
                    int characterId = value.getId();
                    if (removeCharacterListener != null) {
                        removeCharacterListener.onRemove(characterId);
                    }
                });

                panel.add(infoPanel, BorderLayout.CENTER);
                panel.add(removeButton, BorderLayout.EAST);

                if (isSelected) {
                    panel.setBackground(Color.CYAN);
                } else {
                    panel.setBackground(Color.WHITE);
                }

                return panel;
            }
        });

        // 用户每次只能选择一个角色
        wishList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        // 添加 JList 到 JScrollPane
        add(new JScrollPane(wishList), BorderLayout.CENTER);

        // 按钮面板
        JPanel buttonPanel = new JPanel(new GridLayout(1, 2));
        buttonPanel.add(clearButton);
        buttonPanel.add(saveButton);
        add(buttonPanel, BorderLayout.SOUTH);
    }

    public void setWishList(List<CharacterRecord> characters) {
        listModel.clear();
        for (CharacterRecord character : characters) {
            listModel.addElement(character);
        }

        clearButton.setEnabled(!characters.isEmpty());
        saveButton.setEnabled(!characters.isEmpty());
    }

    public void addClearWishListListener(ActionListener listener) {
        clearButton.addActionListener(listener);
    }

    public void addSaveToFileListener(ActionListener listener) {
        saveButton.addActionListener(listener);
    }
}
