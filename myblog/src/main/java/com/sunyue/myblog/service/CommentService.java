package com.sunyue.myblog.service;

import com.github.pagehelper.PageInfo;
import com.sunyue.myblog.entity.BaseResult;
import com.sunyue.myblog.entity.Comments;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CommentService {
    /*获取所有评论信息*/
    List<Comments> list();

    /*添加评论*/
    BaseResult add(Comments comment);

    /*评论获取数据*/
    PageInfo<Comments> page(int pageNum, int pageSize, Comments comment,String  userName);

    /*通过commentId获取一个评论信息*/
    Comments selectById(String commentId);

    /*通过articleId获取评论信息*/
    List<Comments> selectByArticleId(String articleId);

    /*更新评论信息*/
    BaseResult update(Comments comment);

    /*删除一个评论*/
    BaseResult delete(String commentId);
}
