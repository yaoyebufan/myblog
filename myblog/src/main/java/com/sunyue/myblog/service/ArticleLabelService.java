package com.sunyue.myblog.service;

import com.sunyue.myblog.entity.ArticleLabel;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ArticleLabelService {
    /*查询所有标签连接*/
    List<ArticleLabel> list();
    /*通过文章id查询连接表*/
    List<ArticleLabel> selectByArticleId(String articleId);
    /*通过标签id查询连接表*/
    List<ArticleLabel> selectByLabelId(String labelId);
}
