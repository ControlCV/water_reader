package com.zhjw.water_reader.service;


import lombok.Data;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

/**
 * 读取txt文件实体类
 */
@Data
public class TxtFileReaderUtil {

    private final static String MATCH_RULER_STR = "^第[\\u4e00-\\u9fa50-9]{1,10}章.*";


    private static AppSettingsState instance = AppSettingsState.getInstance();

    /**
     * 获取所有章节名
     *
     * @return 所有章节名
     */
    public static List<Chapter> allChapterTitle(String filePath) {
        List<Chapter> result = new ArrayList<>();

        String ruler;
        if(instance.chapterRuler != null && !"".equals(instance.chapterRuler)){
            ruler = instance.chapterRuler;
        }else{
            ruler = MATCH_RULER_STR;
        }

        try (BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(filePath), StandardCharsets.UTF_8))) {
            String line;
            int currentLine = 0;
            while ((line = br.readLine()) != null) {
                // 当前行为目标行，可以对其进行处理
                if (line.trim().matches(ruler)) {
                    Chapter chapter = new Chapter(line.trim(), currentLine, 0);
                    result.add(chapter);

                    if (result.size() != 1) {
                        //上一个章节的结束行号 = 当前章节的开始行号
                        result.get(result.size() - 2).setEndLine(result.get(result.size() - 1).getStartLine());
                    }
                }


                currentLine++;
            }

            if (result.size() != 0) {
                result.get(result.size() - 1).setEndLine(currentLine);
            } else {
                result.add(new Chapter("",0,currentLine));
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return result;
    }


    /**
     * 获取文本指定起始行所有内容
     *
     * @return 文本所有内容
     */
    public static List<String> designateLineContent(String filePath, int startLine, int endLine) {
        List<String> result = new ArrayList<>();

        int lineNo = 0;
        try (BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(filePath), StandardCharsets.UTF_8))) {
            String line;
            while ((line = br.readLine()) != null) {
                // 当前行为目标行，可以对其进行处理
                if (lineNo >= startLine && lineNo <= endLine) {
                    result.add(line);
                }
                lineNo++;
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return result;
    }

    /**
     * 获取文本所有内容
     *
     * @return 文本所有内容
     */
    public static List<String> allLineContent(String filePath) {
        List<String> result = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(filePath), StandardCharsets.UTF_8))) {
            String line;
            while ((line = br.readLine()) != null) {
                // 当前行为目标行，可以对其进行处理
                result.add(line);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return result;
    }


    public static void main(String[] args) {
        List<Chapter> chapters = allChapterTitle("C:\\Users\\ZHJW\\Desktop\\2.txt");
        Chapter chapter = chapters.get(chapters.size() - 1);
        System.out.println(chapter);
    }

}