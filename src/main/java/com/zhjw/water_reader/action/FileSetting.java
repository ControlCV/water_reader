package com.zhjw.water_reader.action;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;

import com.zhjw.water_reader.factory.WaterBookContentFactory;
import com.zhjw.water_reader.gui.DialogSetting;
import com.zhjw.water_reader.service.AppSettingsState;

import javax.swing.*;
import java.awt.*;


public class FileSetting extends AnAction {


    AppSettingsState instance = AppSettingsState.getInstance();

    @Override
    public void actionPerformed(AnActionEvent e) {

        DialogSetting dialogSetting = new DialogSetting();

        //确保在EDT中执行UI更新
        SwingUtilities.invokeLater(()->{
            dialogSetting.setMinimumSize(new Dimension(700, 400));
            dialogSetting.setLocationRelativeTo(null);
            dialogSetting.setVisible(true);

            instance.chapterIndex = 0;
            WaterBookContentFactory.initChapterWindowUI().getPanel();
        });
    }
}
