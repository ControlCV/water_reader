package com.zhjw.water_reader.gui;

import com.intellij.openapi.fileChooser.FileChooser;
import com.intellij.openapi.fileChooser.FileChooserDescriptor;
import com.zhjw.water_reader.service.AppSettingsState;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

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
    private JLabel fontSizeDescLabel;
    private JLabel fontNameLabel;
    private JComboBox fontNameBox;
    private JLabel fontLabel;
    private JLabel chapterRulerDescLabel;
    private JTextField fontColorNameTextField;

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

        Font font = instance.font;

        SpinnerModel spinnerModel = new SpinnerNumberModel(instance.font.getSize(), 10, 40, 1);
        fontSizeSpinner.setModel(spinnerModel);
        showFontSizeTextPane.setFont(font);
        showFontSizeTextPane.setForeground(new Color(instance.fontRgb.get(0),instance.fontRgb.get(1),instance.fontRgb.get(2)));

        String showRgbText = new StringBuilder().append(instance.fontRgb.get(0)).append(",").append(instance.fontRgb.get(1)).append(",").append(instance.fontRgb.get(2)).toString();
        fontColorNameTextField.setText(showRgbText);

        String[] fontNames = GraphicsEnvironment.getLocalGraphicsEnvironment().getAvailableFontFamilyNames();
        fontNameBox.setModel(new DefaultComboBoxModel<>(fontNames));
        for (int i = 0; i < fontNames.length; i++) {
            String fontName = fontNames[i];
            if (fontName.equals(font.getFontName())){
                fontNameBox.setSelectedIndex(i);
            }
        }

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
                            Font font =  new Font( fontNameBox.getSelectedItem().toString(), instance.font.getStyle() , value);
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

        fontNameBox.addActionListener((event)->{
            Font font =  new Font( fontNameBox.getSelectedItem().toString(), instance.font.getStyle() , (int) fontSizeSpinner.getValue());
            showFontSizeTextPane.setFont(font);
        });

        fontColorNameTextField.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                Color result = JColorChooser.showDialog(new JFrame(), "颜色选择器", Color.WHITE);
                if(result == null){
                    result = new Color(255,0,0);
                }

                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append(result.getRed()).append(",").append(result.getGreen()).append(",").append(result.getBlue());
                fontColorNameTextField.setText(stringBuilder.toString());
                showFontSizeTextPane.setForeground(result);
            }
        });
    }


    public void saveSetting(){
        instance.filePath = filePathField.getText();
        instance.chapterRuler = chapterRuler.getText();
        instance.font = new Font( fontNameBox.getSelectedItem().toString(), instance.font.getStyle() , (int) fontSizeSpinner.getValue());

        ArrayList<Integer> rgbList = new ArrayList<>();
        for (String s : fontColorNameTextField.getText().split(",")) {
            rgbList.add(Integer.parseInt(s));
        }
        instance.fontRgb = rgbList;
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
