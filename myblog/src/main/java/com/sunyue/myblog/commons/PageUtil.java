package com.sunyue.myblog.commons;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class PageUtil {
    //pagePages：有多少条数据，pageNum：当前页码数，footerSize：页脚需要多少个
    public static List<Integer> getIndex(int pageNum, int pagePages, int footerSize) {
        ArrayList<Integer> arrayList = new ArrayList<>();
        for (int i = 1; i <= pagePages; i++) {
            arrayList.add(i);
        }
        //通过工具将大数组切为等比小数组，splitNum为上面的除数
        List<List<Integer>> index = PageUtil.getSplitList(footerSize, arrayList);
        //确定选择是哪个小数组
        int num = (pageNum - 1) / footerSize;
        return index.get(num);
    }

    public static <T> List<List<T>> getSplitList(int splitNum, List<T> list) {
        List<List<T>> splitList = new LinkedList<>();
        // groupFlag >= 1
        int groupFlag = list.size() % splitNum == 0 ? (list.size() / splitNum) : (list.size() / splitNum + 1);
        for (int j = 1; j <= groupFlag; j++) {
            if ((j * splitNum) <= list.size()) {
                splitList.add(list.subList(j * splitNum - splitNum, j * splitNum));
            } else if ((j * splitNum) > list.size()) {
                splitList.add(list.subList(j * splitNum - splitNum, list.size()));
            }
        }
        return splitList;
    }
}
