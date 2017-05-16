package com.oblivm.backend.example;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

/**
 * Created by linsen on 2017/5/17.
 */
public class AddingFunctionGUI extends JFrame {

    private JLabel inputOneLabel;
    private JLabel inputTwoLabel;
    private JTextField inputOneText;
    private JTextField inputTwoText;
    private JButton calcButton;
//    private JButton exitButton;


    public AddingFunctionGUI() {
        initComponents();
    }

    public void setHeader(String title) {
        setTitle(title);
    }

    public void setActionToCalcButton(ActionListener action) {
        this.calcButton.addActionListener(action);
    }

    public String getInputOneText() {
        return this.inputOneText.getText();
    }

    public String getInputTwoText() {
        return this.inputTwoText.getText();
    }

    private void initComponents() {
        inputOneLabel = new JLabel("第一个参数:");
        inputTwoLabel = new JLabel("第二个参数:");
        inputOneText = new JTextField(16);
        inputTwoText = new JTextField(16);
        calcButton = new JButton("计算");
//        exitButton = new JButton("关闭");

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        BoxLayout thisLayout = new BoxLayout(getContentPane(), javax.swing.BoxLayout.Y_AXIS);
        getContentPane().setLayout(thisLayout);

        JPanel inputOnePanel = new JPanel();
        inputOnePanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        inputOnePanel.add(inputOneLabel);
        inputOnePanel.add(inputOneText);

        JPanel inputTowPanel = new JPanel();
        inputTowPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        inputTowPanel.add(inputTwoLabel);
        inputTowPanel.add(inputTwoText);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.add(calcButton);

        this.add(inputOnePanel);
        this.add(inputTowPanel);
        this.add(buttonPanel);

        this.setSize(1000, 500);
        pack();

    }
}
