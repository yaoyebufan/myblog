package com.sunyue.myblog.service;

import com.github.pagehelper.PageInfo;
import com.sunyue.myblog.entity.BaseResult;
import com.sunyue.myblog.entity.Messages;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface MessageService {
    /*获取所有留言信息*/
    List<Messages> list();

    /*添加留言*/
    BaseResult add(Messages messages);

    /*留言获取数据*/
    PageInfo<Messages> page(int pageNum, int pageSize, Messages messages, String userName);

    /*通过commentId获取一个留言信息*/
    Messages selectById(String messageId);

    /*更新留言信息*/
    BaseResult update(Messages messages);

    /*删除一个留言*/
    BaseResult delete(String messageId);

    /*查询指定数量的留言列表*/
    List<Messages> selectMessagesByRow(int num);

    /*留言数*/
    int count();
}
