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


    /**
     * 获取所有章节名
     *
     * @return 所有章节名
     */
    public static List<Chapter> allChapterTitle(String filePath) {
        List<Chapter> result = new ArrayList<>();


        try (BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(filePath), StandardCharsets.UTF_8))) {
            String line;
            int currentLine = 0;
            while ((line = br.readLine()) != null) {
                // 当前行为目标行，可以对其进行处理
                if (line.trim().startsWith("===第") || line.startsWith("第")) {
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
            } else if (result.size() == 0) {
                result.add(new Chapter("",0,currentLine));
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