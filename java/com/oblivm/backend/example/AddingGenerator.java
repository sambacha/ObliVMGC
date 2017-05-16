package com.oblivm.backend.example;

import com.oblivm.backend.flexsc.Flag;
import com.oblivm.backend.util.GenRunnable;

import javax.swing.*;

/**
 * Created by linsen on 2017/5/16.
 */
public class AddingGenerator {

    public static int output = 0;

    public static void main(String[] args) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                AddingFunctionGUI genGUI = new AddingFunctionGUI();
                genGUI.setHeader("发起方");
                genGUI.setActionToCalcButton(e -> {
                    Class<?> clazz = null;
                    try {
                        clazz = Class.forName("com.oblivm.backend.example.AddingFunction$Generator");
                        GenRunnable run = (GenRunnable) clazz.newInstance();
                        String[] arguments = new String[3];
                        arguments[0] = "AddingFunction$Generator";
                        arguments[1] = genGUI.getInputOneText();
                        arguments[2] = genGUI.getInputTwoText();
                        run.args = arguments;
                        run.loadConfig("Config.conf");
                        run.run();
                        if (Flag.CountTime)
                            Flag.sw.print();
                        JOptionPane.showMessageDialog(null, "计算结果为" + AddingGenerator.output, "发起方计算结果", JOptionPane.ERROR_MESSAGE);
                    } catch (ClassNotFoundException e1) {
                        e1.printStackTrace();
                    } catch (IllegalAccessException e1) {
                        e1.printStackTrace();
                    } catch (InstantiationException e1) {
                        e1.printStackTrace();
                    }
                });
                genGUI.setVisible(true);
            }
        });

    }
}
