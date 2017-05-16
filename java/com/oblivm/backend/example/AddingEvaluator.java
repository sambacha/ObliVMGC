package com.oblivm.backend.example;

import com.oblivm.backend.flexsc.Flag;
import com.oblivm.backend.util.EvaRunnable;
import com.oblivm.backend.util.GenRunnable;

import javax.swing.*;

/**
 * Created by linsen on 2017/5/16.
 */
public class AddingEvaluator {

    public static int output = 0;

    public static void main(String[] args) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                AddingFunctionGUI evaGUI = new AddingFunctionGUI();
                evaGUI.setHeader("协作方");
                evaGUI.setActionToCalcButton(e -> {
                    Class<?> clazz = null;
                    try {
                        clazz = Class.forName("com.oblivm.backend.example.AddingFunction$Evaluator");
                        EvaRunnable run = (EvaRunnable) clazz.newInstance();
                        String[] arguments = new String[3];
                        arguments[0] = "AddingFunction$Evaluator";
                        arguments[1] = evaGUI.getInputOneText();
                        arguments[2] = evaGUI.getInputTwoText();
                        run.args = arguments;
                        run.loadConfig("Config.conf");
                        run.run();
                        if (Flag.CountTime)
                            Flag.sw.print();
                        JOptionPane.showMessageDialog(null, "计算结果为" + AddingEvaluator.output, "协作方计算结果", JOptionPane.ERROR_MESSAGE);
                    } catch (ClassNotFoundException e1) {
                        e1.printStackTrace();
                    } catch (IllegalAccessException e1) {
                        e1.printStackTrace();
                    } catch (InstantiationException e1) {
                        e1.printStackTrace();
                    }
                });
                evaGUI.setVisible(true);
            }
        });

    }
}