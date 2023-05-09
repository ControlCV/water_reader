package com.zhjw.water_reader.gui;

import com.zhjw.water_reader.service.AppSettingsState;
import com.zhjw.water_reader.service.Chapter;
import com.zhjw.water_reader.service.TxtFileReaderUtil;


import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.util.List;


public class ChapterWindowUI {

    private JPanel contentPanel;

    private JTextArea wordTextArea;

    private JButton preButton;

    private JButton nextButton;

    private JButton hideButton;

    private JSplitPane splitPane;

    private JPanel wordPanel;

    private JPanel chapterPanel;

    private JScrollPane chapterScrollPane;

    private JList chapterList;

    private JScrollPane wordScrollPane;

    /**
     * 记录分割区域位置
     */
    private Dimension splitPaneDimension;

    /**
     * 持久化配置
     */
    AppSettingsState instance = AppSettingsState.getInstance();

    public JPanel getPanel() {

        //初始化分隔栏比重
        splitPane.setResizeWeight(0.5);

        if (instance.filePath == null || "".equals(instance.filePath)) {
            wordTextArea.setText("未选择书籍初始化!请去置顶菜单栏Help选项 -> WaterBookFileSetting 选择初始化书籍！");
            return contentPanel;
        }

        File file = new File(instance.filePath);

        if (!file.exists()) {
            wordTextArea.setText("书籍名称" + instance.filePath + "不存在，请重新选择！");
            return contentPanel;
        }

        //判断当前选中章节是否为0
        if (instance.chapterIndex == 0) {
            //代表需要重新读取txt文件

            //获取所有标题
            List<Chapter> allChapterTitle = TxtFileReaderUtil.allChapterTitle(instance.filePath);

            //获取所有内容
            List<String> allLineContent = TxtFileReaderUtil.allLineContent(instance.filePath);

            instance.chapterList = allChapterTitle;
            instance.allContentStrList = allLineContent;
        }

        //初始化标题内容
        DefaultListModel defaultListModel = new DefaultListModel();
        defaultListModel.addAll(instance.chapterList);
        chapterList.setModel(defaultListModel);
        chapterList.setSelectedIndex(instance.chapterIndex);
        //将当前选中标题设置为第一行显示
        chapterScrollPane.getViewport().setViewPosition(chapterList.indexToLocation(chapterList.getSelectedIndex()));


        //初始化文本内容
        Chapter selectdChapter = instance.chapterList.get(instance.chapterIndex);
        buildWordAreaText(selectdChapter);

        //按钮绑定事件
        buttonBindingClick();

        return contentPanel;
    }


    /**
     * 按钮绑定事件
     */
    private void buttonBindingClick() {
        //隐藏按钮点击监听事件
        hideButton.addActionListener(e -> {
            Component leftComponent = splitPane.getLeftComponent();

            if (leftComponent.getWidth() == 0) {
                // 当左边组件被收起时，展开左边内容
                splitPane.setDividerLocation(splitPaneDimension.width);
                leftComponent.setSize(splitPaneDimension);

                //将当前选中标题设置为第一行显示
                chapterScrollPane.getViewport().setViewPosition(chapterList.indexToLocation(chapterList.getSelectedIndex()));
            } else {
                // 当左边组件已经展开时，收起左边内容
                splitPaneDimension = leftComponent.getSize();
                leftComponent.setSize(new Dimension(0, splitPaneDimension.height));
                splitPane.setDividerLocation(0);
            }
        });


        //章节列表点击监听事件
        chapterList.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int selectedIndex = chapterList.getSelectedIndex();
                instance.chapterIndex = chapterList.getSelectedIndex();

                Chapter selectdChapter = (Chapter) chapterList.getModel().getElementAt(selectedIndex);
                buildWordAreaText(selectdChapter);
            }
        });


        //上一章点击监听事件
        preButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int currentIndex = chapterList.getSelectedIndex();

                if (currentIndex == 0) {
                    //第一章不做任何处理

                } else {
                    int preIndex = chapterList.getSelectedIndex() - 1;
                    instance.chapterIndex = preIndex;

                    Chapter preChapter = (Chapter) chapterList.getModel().getElementAt(preIndex);
                    buildWordAreaText(preChapter);

                    chapterList.setSelectedIndex(preIndex);

                    //章节页面展示不够刷新页面
                    if (chapterList.getFirstVisibleIndex() >= chapterList.getSelectedIndex()) {
                        int startIndex = preIndex - 1;
                        int endIndex = chapterList.getLastVisibleIndex() - 1;

                        // 计算刷新区域的坐标和大小
                        Rectangle rect = chapterList.getCellBounds(startIndex, endIndex);

                        if (rect != null) {
                            chapterList.scrollRectToVisible(rect);
                        }

                    }
                }
            }
        });


        //下一章点击监听事件
        nextButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int currentIndex = chapterList.getSelectedIndex();

                if (currentIndex == chapterList.getModel().getSize() - 1) {
                    //最后一章不做处理
                } else {
                    int nextIndex = chapterList.getSelectedIndex() + 1;
                    instance.chapterIndex = nextIndex;

                    Chapter nextChapter = (Chapter) chapterList.getModel().getElementAt(nextIndex);
                    buildWordAreaText(nextChapter);

                    chapterList.setSelectedIndex(nextIndex);

                    //章节页面展示不够刷新页面
                    if (chapterList.getLastVisibleIndex() <= chapterList.getSelectedIndex()) {
                        int startIndex = chapterList.getFirstVisibleIndex() + 1;
                        int endIndex = nextIndex + 1;

                        // 计算刷新区域的坐标和大小
                        Rectangle rect = chapterList.getCellBounds(startIndex, endIndex);

                        if (rect != null) {
                            chapterList.scrollRectToVisible(rect);
                        }

                    }
                }


            }
        });
    }

    /**
     * 构建文本域展示内容
     *
     * @param selectedChapter 选中的章节
     */
    private void buildWordAreaText(Chapter selectedChapter) {
        List<String> selectedContent = instance.allContentStrList.subList(selectedChapter.getStartLine(), selectedChapter.getEndLine());

        StringBuilder stringBuilder = new StringBuilder();
        for (String line : selectedContent) {
            stringBuilder.append(line + "\n");
        }
        wordTextArea.setText(stringBuilder.toString());
        //滑动栏回到头部
        wordTextArea.setCaretPosition(0);
    }


}
