package com.zhjw.water_reader.service;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 章节实体
 */
@Data
@NoArgsConstructor
public class Chapter {

    /**
     * 章节名
     */
    private String titleName;

    /**
     * 当前章节开始行号
     */
    private int startLine;

    /**
     * 当前章节结束行号
     */
    private int endLine;

    public Chapter(String titleName, int startLine, int endLine) {
        this.titleName = titleName;
        this.startLine = startLine;
        this.endLine = endLine;
    }

    /**
     * 展示章节名
     *
     * @return 章节名
     */
    @Override
    public String toString() {
        return titleName;
    }


}