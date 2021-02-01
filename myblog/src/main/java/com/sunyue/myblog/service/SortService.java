package com.sunyue.myblog.service;

import com.github.pagehelper.PageInfo;
import com.sunyue.myblog.entity.BaseResult;
import com.sunyue.myblog.entity.Sorts;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface SortService {
    /*获取所有分类信息*/
    List<Sorts> list();

    /*添加分类*/
    BaseResult add(Sorts sorts);

    /*分页获取数据*/
    PageInfo<Sorts> page(int pageNum, int pageSize, Sorts sorts);

    /*通过sortId获取一个分类信息*/
    Sorts selectById(String sortId);

    /*通过sortName获取一个分类信息*/
    Sorts selectBySortName(String sortName);

    /*更新分类信息*/
    BaseResult update(Sorts sorts);

    /*删除一个分类*/
    BaseResult delete(String sortId);
}
