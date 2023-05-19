package com.zhjw.water_reader.action;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;

import com.zhjw.water_reader.factory.WaterBookContentFactory;
import com.zhjw.water_reader.gui.DialogSetting;
import com.zhjw.water_reader.service.AppSettingsState;

import javax.swing.*;


public class FileSetting extends AnAction {


    AppSettingsState instance = AppSettingsState.getInstance();

    @Override
    public void actionPerformed(AnActionEvent e) {

        DialogSetting dialogSetting = new DialogSetting();

        JOptionPane.showOptionDialog(null, dialogSetting.getContentPane(), "Water Book Setting",
                JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE,
                null, new Object[]{}, null);


        instance.chapterIndex = 0;
        WaterBookContentFactory.initChapterWindowUI().getPanel();


    }
}
