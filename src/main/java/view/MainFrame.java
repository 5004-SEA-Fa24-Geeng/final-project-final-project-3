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
        // create main panel
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // create left panel (filter)
        JPanel leftPanel = new JPanel(new BorderLayout());
        leftPanel.setPreferredSize(new Dimension(350, 30));
        filterPanel = new FilterPanel();
        JScrollPane filterScrollPane = new JScrollPane(filterPanel);
        filterScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        leftPanel.add(filterScrollPane, BorderLayout.CENTER);

        // create right panel
        JPanel rightPanel = new JPanel(new BorderLayout(10, 10));

        // create top panel (sort)
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setPreferredSize(new Dimension(0, 30));
        sortPanel = new SortPanel();
        topPanel.add(sortPanel, BorderLayout.WEST);

        // create middle panel (character list)
        characterListPanel = new CharacterListPanel(sortPanel);
        JScrollPane listScrollPane = new JScrollPane(characterListPanel);
        listScrollPane.setPreferredSize(new Dimension(600, 0));

        // create right panel (wish list)
        wishListPanel = new WishListPanel();

        // assemble right panel
        rightPanel.add(topPanel, BorderLayout.NORTH);
        rightPanel.add(listScrollPane, BorderLayout.CENTER);
        rightPanel.add(wishListPanel, BorderLayout.EAST);

        // assemble main panel
        mainPanel.add(leftPanel, BorderLayout.WEST);
        mainPanel.add(rightPanel, BorderLayout.CENTER);

        // add main panel to window
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