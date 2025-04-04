package view;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.ArrayList;
import model.Character;

public class Celebrities {
    private JPanel rootPanel;
    private JTextField searchName;
    private JTextField textField1;  // Age输入框
    private JTextField textField2;  // 排序输入框
    private JComboBox<String> comboBox1;  // 星座下拉框
    private JComboBox<String> comboBox2;  // Sort下拉框
    private JCheckBox maleCheckbox;
    private JCheckBox femaleCheckBox;
    private JCheckBox nonbinaryCheckbox;
    private JButton searchButton;
    private JButton resetButton;
    private JButton button1;  // Clear按钮
    private JButton button2;  // Save按钮
    private JList<Character> CharacterList;
    private JList<Character> wishlist;
    private JLabel sign;
    private JLabel age;
    private JLabel gender;
    private JLabel sort;
    private JList<String> signList;  // 星座多选列表
    private JScrollPane signScrollPane;  // 星座多选列表的滚动面板

    public Celebrities() {
        // 首先初始化所有组件
        initializeComponents();
        // 然后设置布局
        setupLayout();
        // 最后设置事件监听器
        setupListeners();
    }

    private void initializeComponents() {
        rootPanel = new JPanel();
        searchName = new JTextField();
        textField1 = new JTextField();
        textField2 = new JTextField();
        
        // 将星座下拉框改为多选列表
        comboBox1 = new JComboBox<>(new String[]{
            "Aries", "Taurus", "Gemini", "Cancer", "Leo", "Virgo",
            "Libra", "Scorpio", "Sagittarius", "Capricorn", "Aquarius", "Pisces"
        });
        
        // 创建星座多选列表
        signList = new JList<>(new String[]{
            "Aries", "Taurus", "Gemini", "Cancer", "Leo", "Virgo",
            "Libra", "Scorpio", "Sagittarius", "Capricorn", "Aquarius", "Pisces"
        });
        signList.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        signList.setVisibleRowCount(5);
        signScrollPane = new JScrollPane(signList);
        signScrollPane.setPreferredSize(new Dimension(120, 100));
        
        comboBox2 = new JComboBox<>(new String[]{"by Alphabet", "by Age"});
        maleCheckbox = new JCheckBox("Male");
        femaleCheckBox = new JCheckBox("Female");
        nonbinaryCheckbox = new JCheckBox("Non-binary");
        searchButton = new JButton("Search");
        resetButton = new JButton("Reset");
        button1 = new JButton("Clear");
        button2 = new JButton("Save");
        CharacterList = new JList<>();
        wishlist = new JList<>();
        sign = new JLabel("Sign");
        age = new JLabel("Age");
        gender = new JLabel("Gender");
        sort = new JLabel("Sort");

        // 设置组件首选大小
        searchName.setPreferredSize(new Dimension(150, 30));
        textField1.setPreferredSize(new Dimension(60, 30));
        textField2.setPreferredSize(new Dimension(60, 30));
        comboBox1.setPreferredSize(new Dimension(120, 30));
        comboBox2.setPreferredSize(new Dimension(120, 30));
        searchButton.setPreferredSize(new Dimension(80, 30));
        resetButton.setPreferredSize(new Dimension(80, 30));
        button1.setPreferredSize(new Dimension(200, 30));
        button2.setPreferredSize(new Dimension(200, 30));

        // 设置字体
        Font labelFont = new Font("Arial", Font.BOLD, 14);
        Font buttonFont = new Font("Arial", Font.BOLD, 14);
        Font inputFont = new Font("Arial", Font.PLAIN, 14);
        
        sign.setFont(labelFont);
        age.setFont(labelFont);
        gender.setFont(labelFont);
        sort.setFont(labelFont);
        
        searchName.setFont(inputFont);
        textField1.setFont(inputFont);
        textField2.setFont(inputFont);
        comboBox1.setFont(inputFont);
        comboBox2.setFont(inputFont);
        signList.setFont(inputFont);
        
        searchButton.setFont(buttonFont);
        resetButton.setFont(buttonFont);
        button1.setFont(buttonFont);
        button2.setFont(buttonFont);
        
        // 设置按钮样式
        Color buttonColor = new Color(70, 130, 180); // 钢蓝色
        Color buttonHoverColor = new Color(100, 149, 237); // 矢车菊蓝
        Color buttonTextColor = Color.WHITE;
        
        searchButton.setBackground(buttonColor);
        searchButton.setForeground(buttonTextColor);
        searchButton.setFocusPainted(false);
        searchButton.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(buttonColor.darker()),
            BorderFactory.createEmptyBorder(5, 15, 5, 15)
        ));
        
        resetButton.setBackground(buttonColor);
        resetButton.setForeground(buttonTextColor);
        resetButton.setFocusPainted(false);
        resetButton.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(buttonColor.darker()),
            BorderFactory.createEmptyBorder(5, 15, 5, 15)
        ));
        
        button1.setBackground(new Color(240, 240, 240));
        button1.setForeground(Color.BLACK);
        button1.setFocusPainted(false);
        button1.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(Color.GRAY),
            BorderFactory.createEmptyBorder(5, 15, 5, 15)
        ));
        
        button2.setBackground(new Color(240, 240, 240));
        button2.setForeground(Color.BLACK);
        button2.setFocusPainted(false);
        button2.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(Color.GRAY),
            BorderFactory.createEmptyBorder(5, 15, 5, 15)
        ));
        
        // 设置输入框样式
        searchName.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(Color.GRAY),
            BorderFactory.createEmptyBorder(5, 10, 5, 10)
        ));
        
        textField1.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(Color.GRAY),
            BorderFactory.createEmptyBorder(5, 10, 5, 10)
        ));
        
        textField2.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(Color.GRAY),
            BorderFactory.createEmptyBorder(5, 10, 5, 10)
        ));
        
        // 设置下拉框样式
        comboBox1.setBackground(Color.WHITE);
        comboBox2.setBackground(Color.WHITE);
        
        // 设置复选框样式
        maleCheckbox.setFont(inputFont);
        femaleCheckBox.setFont(inputFont);
        nonbinaryCheckbox.setFont(inputFont);
    }

    private void setupLayout() {
        // 使用BorderLayout作为根布局
        rootPanel.setLayout(new BorderLayout(10, 10));
        rootPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        rootPanel.setBackground(new Color(245, 245, 245)); // 浅灰色背景

        // 创建顶部面板（标题和搜索区域）
        JPanel topPanel = new JPanel(new BorderLayout(10, 20));
        topPanel.setBackground(new Color(245, 245, 245));
        
        // 标题
        JLabel titleLabel = new JLabel("Celebrity Wishlist ⭐");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 28));
        titleLabel.setForeground(new Color(70, 130, 180)); // 钢蓝色
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        titleLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 0));
        topPanel.add(titleLabel, BorderLayout.NORTH);
        
        // 创建搜索面板
        JPanel searchPanel = new JPanel(new GridBagLayout());
        searchPanel.setBackground(Color.WHITE);
        searchPanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(new Color(200, 200, 200), 1, true),
                "Search",
                javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION,
                javax.swing.border.TitledBorder.DEFAULT_POSITION,
                new Font("Arial", Font.BOLD, 14),
                new Color(70, 130, 180)
            ),
            BorderFactory.createEmptyBorder(15, 15, 15, 15)
        ));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.anchor = GridBagConstraints.WEST;
        
        // 第一行：姓名和年龄搜索
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 1;
        JLabel nameLabel = new JLabel("Name:");
        nameLabel.setPreferredSize(new Dimension(60, 30));
        searchPanel.add(nameLabel, gbc);
        
        gbc.gridx = 1;
        gbc.gridwidth = 3;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        searchPanel.add(searchName, gbc);
        
        gbc.gridx = 4;
        gbc.gridwidth = 1;
        gbc.fill = GridBagConstraints.NONE;
        gbc.weightx = 0.0;
        age.setPreferredSize(new Dimension(40, 30));
        searchPanel.add(age, gbc);
        
        gbc.gridx = 5;
        gbc.gridwidth = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        searchPanel.add(textField1, gbc);
        
        gbc.gridx = 6;
        gbc.gridwidth = 1;
        gbc.fill = GridBagConstraints.NONE;
        JLabel dashLabel = new JLabel("——");
        dashLabel.setHorizontalAlignment(SwingConstants.CENTER);
        dashLabel.setPreferredSize(new Dimension(30, 30));
        searchPanel.add(dashLabel, gbc);
        
        gbc.gridx = 7;
        gbc.gridwidth = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        searchPanel.add(textField2, gbc);
        
        gbc.gridx = 8;
        gbc.gridwidth = 1;
        gbc.fill = GridBagConstraints.NONE;
        gbc.insets = new Insets(8, 24, 8, 8); // 增加左侧间隔，使search按钮与reset按钮对齐
        searchPanel.add(searchButton, gbc);
        
        // 第二行：性别、星座和排序
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        gbc.fill = GridBagConstraints.NONE;
        gender.setPreferredSize(new Dimension(60, 30));
        searchPanel.add(gender, gbc);
        
        gbc.gridx = 1;
        gbc.gridwidth = 1;
        searchPanel.add(maleCheckbox, gbc);
        
        gbc.gridx = 2;
        gbc.gridwidth = 1;
        searchPanel.add(femaleCheckBox, gbc);
        
        gbc.gridx = 3;
        gbc.gridwidth = 1;
        searchPanel.add(nonbinaryCheckbox, gbc);
        
        gbc.gridx = 4;
        gbc.gridwidth = 1;
        sign.setPreferredSize(new Dimension(40, 30));
        searchPanel.add(sign, gbc);
        
        // 调整sign和下拉框之间的间隔
        gbc.gridx = 5;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(8, 16, 8, 8); // 增加左侧间隔，与sort和下拉框一致
        searchPanel.add(signScrollPane, gbc);
        
        gbc.gridx = 7;
        gbc.gridwidth = 1;
        gbc.fill = GridBagConstraints.NONE;
        sort.setPreferredSize(new Dimension(40, 30));
        gbc.insets = new Insets(8, 24, 8, 8); // 增加左侧间隔，使sort向右移动
        searchPanel.add(sort, gbc);
        
        gbc.gridx = 8;
        gbc.gridwidth = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(8, 8, 8, 8); // 减小与sort的距离
        searchPanel.add(comboBox2, gbc);
        
        gbc.gridx = 9;
        gbc.gridwidth = 1;
        gbc.fill = GridBagConstraints.NONE;
        gbc.insets = new Insets(8, 24, 8, 8); // 保持与search按钮相同的间隔，使它们对齐
        searchPanel.add(resetButton, gbc);
        
        topPanel.add(searchPanel, BorderLayout.CENTER);
        rootPanel.add(topPanel, BorderLayout.NORTH);
        
        // 创建中间面板（列表区域）
        JPanel listPanel = new JPanel(new BorderLayout(20, 10));
        listPanel.setBackground(new Color(245, 245, 245));
        listPanel.setBorder(BorderFactory.createEmptyBorder(20, 0, 0, 0));
        
        // 主列表
        JScrollPane mainListScrollPane = new JScrollPane(CharacterList);
        mainListScrollPane.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(new Color(200, 200, 200), 1, true),
                "Celebrities",
                javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION,
                javax.swing.border.TitledBorder.DEFAULT_POSITION,
                new Font("Arial", Font.BOLD, 14),
                new Color(70, 130, 180)
            ),
            BorderFactory.createEmptyBorder(10, 10, 10, 10)
        ));
        mainListScrollPane.setBackground(Color.WHITE);
        listPanel.add(mainListScrollPane, BorderLayout.CENTER);
        
        // 右侧面板（愿望清单和按钮）
        JPanel rightPanel = new JPanel(new BorderLayout(10, 10));
        rightPanel.setBackground(new Color(245, 245, 245));
        rightPanel.setPreferredSize(new Dimension(250, 0));
        
        // 愿望清单
        JScrollPane wishlistScrollPane = new JScrollPane(wishlist);
        wishlistScrollPane.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(new Color(200, 200, 200), 1, true),
                "Wishlist",
                javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION,
                javax.swing.border.TitledBorder.DEFAULT_POSITION,
                new Font("Arial", Font.BOLD, 14),
                new Color(70, 130, 180)
            ),
            BorderFactory.createEmptyBorder(10, 10, 10, 10)
        ));
        wishlistScrollPane.setBackground(Color.WHITE);
        rightPanel.add(wishlistScrollPane, BorderLayout.CENTER);
        
        // Wishlist按钮面板
        JPanel wishlistButtonPanel = new JPanel(new GridLayout(2, 1, 10, 10));
        wishlistButtonPanel.setBackground(new Color(245, 245, 245));
        wishlistButtonPanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));
        
        wishlistButtonPanel.add(button1);
        wishlistButtonPanel.add(button2);
        rightPanel.add(wishlistButtonPanel, BorderLayout.SOUTH);
        
        listPanel.add(rightPanel, BorderLayout.EAST);
        rootPanel.add(listPanel, BorderLayout.CENTER);
    }

    private void setupListeners() {
        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Search button clicked");
            }
        });

        resetButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                searchName.setText("");
                textField1.setText("");
                textField2.setText("");
                comboBox1.setSelectedIndex(0);
                comboBox2.setSelectedIndex(0);
                maleCheckbox.setSelected(false);
                femaleCheckBox.setSelected(false);
                nonbinaryCheckbox.setSelected(false);
                signList.clearSelection();
            }
        });

        button1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Clear wishlist clicked");
            }
        });

        button2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Save wishlist clicked");
            }
        });

        CharacterList.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (!e.getValueIsAdjusting()) {
                    System.out.println("Character selected");
                }
            }
        });
    }

    public JPanel getRootPanel() {
        return rootPanel;
    }
} 