package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputMethodEvent;
import java.awt.event.InputMethodListener;

import model.Character;

public class Celebrities {
    private JPanel rootPanel;
    private JTextField searchName;
    private JTextField minAge;  // Age输入框
    private JTextField maxAge;  // 排序输入框
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

    public static void main(String[] args) {
        JFrame frame = new JFrame("Celebrities");
        frame.setContentPane(new Celebrities().rootPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }

    public Celebrities() {
        searchName.addInputMethodListener(new InputMethodListener() {
            /**
             * Invoked when the text entered through an input method has changed.
             *
             * @param event the event to be processed
             */
            @Override
            public void inputMethodTextChanged(InputMethodEvent event) {

            }

            /**
             * Invoked when the caret within composed text has changed.
             *
             * @param event the event to be processed
             */
            @Override
            public void caretPositionChanged(InputMethodEvent event) {

            }
        });
        minAge.addInputMethodListener(new InputMethodListener() {
            /**
             * Invoked when the text entered through an input method has changed.
             *
             * @param event the event to be processed
             */
            @Override
            public void inputMethodTextChanged(InputMethodEvent event) {

            }

            /**
             * Invoked when the caret within composed text has changed.
             *
             * @param event the event to be processed
             */
            @Override
            public void caretPositionChanged(InputMethodEvent event) {

            }
        });
        maxAge.addInputMethodListener(new InputMethodListener() {
            /**
             * Invoked when the text entered through an input method has changed.
             *
             * @param event the event to be processed
             */
            @Override
            public void inputMethodTextChanged(InputMethodEvent event) {

            }

            /**
             * Invoked when the caret within composed text has changed.
             *
             * @param event the event to be processed
             */
            @Override
            public void caretPositionChanged(InputMethodEvent event) {

            }
        });
        searchButton.addActionListener(new ActionListener() {
            /**
             * Invoked when an action occurs.
             *
             * @param e the event to be processed
             */
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
        resetButton.addActionListener(new ActionListener() {
            /**
             * Invoked when an action occurs.
             *
             * @param e the event to be processed
             */
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
    }

    public Container getRootPanel() {
        return rootPanel;
    }
}