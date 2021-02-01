package com.sunyue.myblog.commons;

import com.alibaba.druid.util.StringUtils;
import com.sunyue.myblog.entity.*;

import java.util.*;

public class ListRemove {
    /**
     * 根据list中对象某些字段去重
     * @param list 需要去重的list
     * @return 返回去重后的list
     */

    public static List<ArticleSort> removeArticleSort(List<ArticleSort> list) {
        Set<ArticleSort> set = new TreeSet<>(new Comparator<ArticleSort>() {
            public int compare(ArticleSort articleSort, ArticleSort articleSortNew) {
                int compareToResult = 1;//==0表示重复
                if(articleSort.getSortId().equals(articleSortNew.getSortId())) {
                    compareToResult = 0;
                }
                return compareToResult;
            }
        });
        set.addAll(list);
        return new ArrayList<>(set);
    }
    public static List<Articles> removeArticles(List<Articles> list) {
        Set<Articles> set = new TreeSet<>(new Comparator<Articles>() {
            public int compare(Articles articles, Articles articlesNew) {
                int compareToResult = 1;//==0表示重复
                if(articles.getUserId().equals(articlesNew.getUserId())) {
                    compareToResult = 0;
                }
                return compareToResult;
            }
        });
        set.addAll(list);
        return new ArrayList<>(set);
    }
    public static List<Messages> removeMessages(List<Messages> list) {
        Set<Messages> set = new TreeSet<>(new Comparator<Messages>() {
            public int compare(Messages messages, Messages messagesNew) {
                int compareToResult = 1;//==0表示重复
                if(messages.getUserId().equals(messagesNew.getUserId())) {
                    compareToResult = 0;
                }
                return compareToResult;
            }
        });
        set.addAll(list);
        return new ArrayList<>(set);
    }
    public static List<Sorts> removeSort(List<Sorts> list) {
        Set<Sorts> set = new TreeSet<>(new Comparator<Sorts>() {
            public int compare(Sorts sorts, Sorts sortsNew) {
                int compareToResult = 1;//==0表示重复
                if(sorts.getSortName().equals(sortsNew.getSortName())) {
                    compareToResult = 0;
                }
                return compareToResult;
            }
        });
        set.addAll(list);
        return new ArrayList<>(set);
    }
    public static List<Labels> removeLabel(List<Labels> list) {
        Set<Labels> set = new TreeSet<>(new Comparator<Labels>() {
            public int compare(Labels labels, Labels labelsNew) {
                int compareToResult = 1;//==0表示重复
                if(labels.getLabelName().equals(labelsNew.getLabelName())) {
                    compareToResult = 0;
                }
                return compareToResult;
            }
        });
        set.addAll(list);
        return new ArrayList<>(set);
    }

    public static void removeDuplicate(List<String> list) {
        HashSet<String> set = new HashSet<String>(list.size());
        List<String> result = new ArrayList<String>(list.size());
        for (String str : list) {
            if (set.add(str)) {
                result.add(str);
            }
        }
        list.clear();
        list.addAll(result);
    }
}
