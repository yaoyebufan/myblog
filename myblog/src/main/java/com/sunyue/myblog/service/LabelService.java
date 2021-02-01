package com.sunyue.myblog.service;

import com.github.pagehelper.PageInfo;
import com.sunyue.myblog.entity.BaseResult;
import com.sunyue.myblog.entity.Labels;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface LabelService {
    /*获取所有标签信息*/
    List<Labels> list();

    /*添加标签*/
    BaseResult add(Labels labels);

    /*分页获取数据*/
    PageInfo<Labels> page(int pageNum, int pageSize, Labels labels);

    /*通过sortId获取一个标签信息*/
    Labels selectById(String labelId);

    /*通过sortName获取一个标签信息*/
    Labels selectByLabelName(String labelName);

    /*更新标签信息*/
    BaseResult update(Labels labels);

    /*删除一个标签*/
    BaseResult delete(String labelId);

    /*标签数*/
    int count();
}
