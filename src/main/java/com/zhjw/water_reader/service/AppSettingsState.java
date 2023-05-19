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
     * 字体大小
     */
    public int fontSize = 15;

    /**
     * 章节列表
     */
    public List<Chapter> chapterList;

    /**
     * 所有的内容
     */
    public List<String>  allContentStrList;

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
