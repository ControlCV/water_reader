package com.zhjw.water_reader.factory;

import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.wm.ToolWindow;
import com.intellij.openapi.wm.ToolWindowFactory;
import com.intellij.ui.content.Content;
import com.intellij.ui.content.ContentFactory;
import com.zhjw.water_reader.gui.ChapterWindowUI;
import org.jetbrains.annotations.NotNull;


/**
 * 创建toolWindow工厂
 */
public class WaterBookContentFactory implements ToolWindowFactory {

    static ChapterWindowUI chapterWindowUI;

    /**
     * 创建并组装toolWindow内容
     *
     * @param project    当前项目
     * @param toolWindow toolWindow对象
     */
    @Override
    public void createToolWindowContent(@NotNull Project project, @NotNull ToolWindow toolWindow) {
        initChapterWindowUI();

        // 确保在EDT中执行UI更新
        ApplicationManager.getApplication().invokeLater(() -> {
            Content content = ContentFactory.getInstance().createContent(chapterWindowUI.getPanel(), "", false);
            toolWindow.getContentManager().addContent(content);
        });

    }


    /**
     * 保证ChapterWindowUI单例
     *
     * @return 单例ChapterWindowUI
     */
    public static ChapterWindowUI initChapterWindowUI() {
        if (chapterWindowUI == null) {
            chapterWindowUI = new ChapterWindowUI();
        }

        return chapterWindowUI;
    }
}
