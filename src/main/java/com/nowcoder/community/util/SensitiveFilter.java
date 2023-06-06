package com.nowcoder.community.util;

import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.CharUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

/**
 * SensitiveFilter
 *
 * @author Jimmy
 * @date 2023/5/12 13:14
 * @description: 敏感词过滤
 */

@Slf4j
@Component
public class SensitiveFilter {

    // 替换符号
    private static final String REPLACEMENT = "***";

    // root
    private TrieNode rootNode = new TrieNode();

    // 表示在construct之后调用的方法，容器在调用当前bean的构造函数（初始化）之后就会调用这个方法
    // 即：在访问这个类一开始就构造一次这个搜索树
    @PostConstruct
    public void init() {
        try (
                // 放在这里可以由编译器自动在final里关闭流
                InputStream is = this.getClass().getClassLoader().getResourceAsStream("sensitive-words.txt");
                // 字节流 -> 字符流(可读) -> 缓冲流(效率高)
                BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        ) {
            // 读取敏感词
            String keyword;
            while ((keyword = reader.readLine()) != null){
                // 敏感词添加到前缀树
                this.addKeyword(keyword);
            }
        } catch (IOException e) {
            log.error("加载敏感词文件失败：" + e.getMessage());
        }

    }

    // 把一个敏感词添加到前缀树的方法
    private void addKeyword(String keyword){
        TrieNode tempNode = rootNode;
        for (int i = 0; i < keyword.length(); i++) {
            char c = keyword.charAt(i);
            TrieNode subNode = tempNode.getSubNode(c);
            if (subNode == null){   // 子节点为空
                // 初始化子节点
                subNode = new TrieNode();
                tempNode.addSubNode(c, subNode);
            }
            // 迭代树上的tempNode指针，进入下一轮循环
            tempNode = subNode;

            // 设置敏感词的结束标志
            if (i == keyword.length() - 1){ // 说明这条敏感词已经遍历完了
                tempNode.setKeywordEnd(true);   // 使用指针在结束位置打一个标记
            }
        }
    }

    /**
     * 过滤敏感词
     * @param text 待过滤的文本
     * @return  过滤后的文本
     */
    public String filter(String text){
        if (StringUtils.isBlank(text)){
            return null;
        }

        // 指针1
         TrieNode tempNode = rootNode;
        // 指针2，该指针是慢指针
        int begin = 0;
        // 指针3，该指针是快指针，因此用来做便利
        int position = 0;
        // 记录结果
        StringBuilder sb = new StringBuilder();

        while(position < text.length()){
            char c = text.charAt(position);

            // 如果是特殊字符，则跳过
            if (isSymbol(c)){
                // 若指针1处于根节点，此时说明特殊符号在敏感字符前面，因此我们不处理它，将此符号计入结果，让指针2向后一步
                if (tempNode == rootNode){
                    sb.append(c);
                    begin++;
                }
                // 如果c是特殊符号，指针3无论如何都要走一步
                position++;
                continue;
            }
            // 检查子节点
            tempNode = tempNode.getSubNode(c);
            if (tempNode == null){
                // 到底了，表示以begin为开头的字符串不是敏感词
                // 因此可以记录begin所指向的字符并且begin向后一步
                sb.append(text.charAt(begin));
                // 进入下一个位置，同时归位position指针
                position = ++begin;
                // 树指针归位，重新指向根节点
                tempNode = rootNode;
            } else if (tempNode.isKeywordEnd()) {
                // 如果取到的这个子节点发现了敏感词，则替换字符串
                sb.append(REPLACEMENT);
                // 进入下一个位置，并且归位begin
                begin = ++position;
                // 树指针归位，重新指向根节点
                tempNode = rootNode;
            } else {
                // 此时，c既在搜索树中，又不是结束节点（c作为敏感字符串的中间字符）
                position++;
            }
        }
        // 将最后一批不是敏感词的字符计入结果
        sb.append(text.substring(begin));

        return sb.toString();
    }

    // 判断是否为符号：此方法为了跳过刻意加在敏感词之间、规避检查的符号
    private boolean isSymbol(Character c){
        // 判断是否是常规字符，如果是合法字符（alphabet或数字）返回true，不是（即符号）则返回false
        // 因此取反
        // 0x2E80 ~ 0x9FFF 是东亚文字返回，超出这个范围的我们也认定为是特殊符号
        return !CharUtils.isAsciiAlphanumeric(c) && (c < 0x2E80 || c > 0x9FFF);
    }

    // 前缀树（使用内部类，因为只有这里要用）
    private class TrieNode {
        // 关键词结束标识
        private boolean isKeywordEnd = false;
        // 子节点（key是下级字符，value是下级节点）
        private Map<Character, TrieNode> subNodes = new HashMap<>();

        // getter
        public boolean isKeywordEnd() {
            return isKeywordEnd;
        }

        // setter
        public void setKeywordEnd(boolean keywordEnd) {
            isKeywordEnd = keywordEnd;
        }

        // 添加子节点
        public void addSubNode(Character c, TrieNode node) {
            subNodes.put(c, node);
        }

        // 获取子节点
        public TrieNode getSubNode(Character c) {
            return subNodes.get(c);
        }
    }
}
