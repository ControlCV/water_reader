package com.zhjw.water_reader.gui;

import com.intellij.openapi.fileChooser.FileChooser;
import com.intellij.openapi.fileChooser.FileChooserDescriptor;
import com.zhjw.water_reader.service.AppSettingsState;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.*;

public class DialogSetting extends JDialog {
    private JPanel contentPane;
    private JTextField filePathField;
    private JSpinner fontSizeSpinner;
    private JTextField chapterRuler;
    private JTextPane showFontSizeTextPane;
    private JLabel filePathTextLabel;
    private JButton chooseFileButton;
    private JButton calButton;
    private JButton OKButton;
    private JLabel chapterRulerLabel;
    private JLabel fontSizeLabel;

    AppSettingsState instance = AppSettingsState.getInstance();

    public DialogSetting() {
        setContentPane(contentPane);
        setModal(true);

        if (!"".equals(instance.filePath)) {
            filePathField.setText(instance.filePath);
        }

        if (!"".equals(instance.chapterRuler)) {
            chapterRuler.setText(instance.chapterRuler);
        }

       int fontSize = instance.fontSize;

        SpinnerModel spinnerModel = new SpinnerNumberModel(instance.fontSize, 10, 40, 1);
        fontSizeSpinner.setModel(spinnerModel);
        Font font = new Font("", 0, fontSize);
        showFontSizeTextPane.setFont(font);

        buttonBindingClick();
    }

    private void buttonBindingClick() {
        chooseFileButton.addMouseListener(
                new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        // 创建FileChooserDescriptor对象，并设置其属性
                        FileChooserDescriptor descriptor = new FileChooserDescriptor(true, false, false, false, false, false);
                        descriptor.setTitle("Choose a file");

                        // 使用FileChooser打开文件选择对话框，获取用户选择的文件
                        String filePath = FileChooser.chooseFile(descriptor, null, null).getPath();

                        // 如果用户选择了文件，则将文件名设置给按钮文本
                        if (filePath != null) {
                            filePathField.setText(filePath);
                        }

                    }
                }
        );

        fontSizeSpinner.addChangeListener(
                new ChangeListener() {
                    @Override
                    public void stateChanged(ChangeEvent e) {
                        int value = (int) fontSizeSpinner.getValue();
                        if (10 < value && value < 40) {
                            Font font = new Font("", 0, value);
                            showFontSizeTextPane.setFont(font);
                        }
                    }
                }
        );

        OKButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                saveSetting();
                closeDialog(e);
            }
        });

        calButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                closeDialog(e);
            }
        });
    }


    public void saveSetting(){
        instance.filePath = filePathField.getText();
        instance.chapterRuler = chapterRuler.getText();
        instance.fontSize = (int)fontSizeSpinner.getValue();

    }

    public void closeDialog(MouseEvent e){
        JComponent comp = (JComponent) e.getSource();
        Window win = SwingUtilities.getWindowAncestor(comp);
        win.dispose(); // 关闭窗口
    }

    public JPanel getContentPane(){
        return contentPane;
    }

}
