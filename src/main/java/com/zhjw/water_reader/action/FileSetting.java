package com.zhjw.water_reader.action;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;

import com.intellij.openapi.fileChooser.FileChooser;
import com.intellij.openapi.fileChooser.FileChooserDescriptor;
import com.intellij.openapi.project.Project;

import com.intellij.openapi.vfs.VirtualFile;

import com.zhjw.water_reader.factory.WaterBookContentFactory;
import com.zhjw.water_reader.service.AppSettingsState;


public class FileSetting extends AnAction {

    AppSettingsState instance = AppSettingsState.getInstance();

    @Override
    public void actionPerformed(AnActionEvent e) {

        Project currentProject = e.getProject();
        FileChooserDescriptor descriptor = new FileChooserDescriptor(true, false, false , true, false, true);
        VirtualFile chooseFile = FileChooser.chooseFile(descriptor, currentProject, null);

        instance.filePath = chooseFile.getPath();
        instance.chapterIndex = 0;
        WaterBookContentFactory.initChapterWindowUI().getPanel();
    }
}
