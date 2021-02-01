package com.sunyue.myblog.service;

import com.sunyue.myblog.entity.ArticleSort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ArticleSortService {
    /*查询所有分类连接*/
    List<ArticleSort> list();
    /*通过文章id查询连接表*/
    List<ArticleSort> selectByArticleId(String articleId);
    /*通过分类id查询连接表*/
    List<ArticleSort> selectBySortId(String sortId);
}
