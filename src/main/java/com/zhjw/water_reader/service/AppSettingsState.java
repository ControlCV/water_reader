package com.zhjw.water_reader.service;

import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.components.PersistentStateComponent;
import com.intellij.openapi.components.State;
import com.intellij.openapi.components.Storage;
import com.intellij.util.xmlb.XmlSerializerUtil;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

/**
 * 持久化数据
 */
@State(
        name = "com.zhjw.water_reader.service.AppSettingsState",
        storages = @Storage("water_book.xml")
)
public class AppSettingsState implements PersistentStateComponent<AppSettingsState> {

    /**
     * 文件路径
     */
    public String filePath = "";

    /**
     * 选中的章节下标
     */
    public int chapterIndex = 0;

    /**
     * 章节名规则
     */
    public String chapterRuler;

    /**
     * 文本域字体样式
     */
//    public Font font = new Font("宋体", 0, 15);

    /**
     * 文本域字体名称
     */
    public String fontName = "宋体";

    /**
     * 文本域字体样式
     */
    public int fontStyle = 0;

    /**
     * 文本域字体大小
     */
    public int fontSize = 15;

    /**
     * 文本域字体样式
     */
    public List<Integer> fontRgb = List.of(255, 0, 0);

    /**
     * 章节列表
     */
    public List<Chapter> chapterList;

    public static AppSettingsState getInstance() {
        return ApplicationManager.getApplication().getService(AppSettingsState.class);
    }

    @Nullable
    @Override
    public AppSettingsState getState() {
        return this;
    }

    @Override
    public void loadState(@NotNull AppSettingsState state) {
        XmlSerializerUtil.copyBean(state, this);
    }

}
