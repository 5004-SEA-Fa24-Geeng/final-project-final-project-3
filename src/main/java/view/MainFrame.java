package view;

import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame {
    private SortPanel sortPanel;
    private WishListPanel wishListPanel;
    private FilterPanel filterPanel;
    private CharacterListPanel characterListPanel;

    public MainFrame() {
        setTitle("Character Management System");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1200, 800);
        setLocationRelativeTo(null);
        initComponents();
    }

    private void initComponents() {
        // 创建主面板
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // 创建左侧面板（筛选）
        JPanel leftPanel = new JPanel(new BorderLayout());
        leftPanel.setPreferredSize(new Dimension(350, 30));
        filterPanel = new FilterPanel();
        JScrollPane filterScrollPane = new JScrollPane(filterPanel);
        filterScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        leftPanel.add(filterScrollPane, BorderLayout.CENTER);

        // 创建右侧面板
        JPanel rightPanel = new JPanel(new BorderLayout(10, 10));

        // 创建顶部面板（排序）
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setPreferredSize(new Dimension(0, 30));
        sortPanel = new SortPanel();
        topPanel.add(sortPanel, BorderLayout.WEST);

        // 创建中间面板（角色列表）
        characterListPanel = new CharacterListPanel(sortPanel);
        JScrollPane listScrollPane = new JScrollPane(characterListPanel);
        listScrollPane.setPreferredSize(new Dimension(600, 0));

        // 创建右侧面板（心愿单）
        wishListPanel = new WishListPanel();

        // 组装右侧面板
        rightPanel.add(topPanel, BorderLayout.NORTH);
        rightPanel.add(listScrollPane, BorderLayout.CENTER);
        rightPanel.add(wishListPanel, BorderLayout.EAST);

        // 组装主面板
        mainPanel.add(leftPanel, BorderLayout.WEST);
        mainPanel.add(rightPanel, BorderLayout.CENTER);

        // 添加主面板到窗口
        add(mainPanel);
    }

    public SortPanel getSortPanel() {
        return sortPanel;
    }

    public WishListPanel getWishListPanel() {
        return wishListPanel;
    }

    public FilterPanel getFilterPanel() {
        return filterPanel;
    }

    public CharacterListPanel getCharacterListPanel() {
        return characterListPanel;
    }
} 