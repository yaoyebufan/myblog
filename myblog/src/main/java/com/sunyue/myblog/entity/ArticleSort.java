package com.sunyue.myblog.entity;

import javax.persistence.*;

@Table(name = "article_sort")
public class ArticleSort {
    /**
     * 文章ID
     */
    @Id
    @Column(name = "article_id")
    private Long articleId;

    /**
     * 文章分类连接表ID
     */
    @Id
    @Column(name = "sort_id")
    private Long sortId;

    /**
     * 获取文章ID
     *
     * @return article_id - 文章ID
     */
    public Long getArticleId() {
        return articleId;
    }

    /**
     * 设置文章ID
     *
     * @param articleId 文章ID
     */
    public void setArticleId(Long articleId) {
        this.articleId = articleId;
    }

    /**
     * 获取文章分类连接表ID
     *
     * @return sort_id - 文章分类连接表ID
     */
    public Long getSortId() {
        return sortId;
    }

    /**
     * 设置文章分类连接表ID
     *
     * @param sortId 文章分类连接表ID
     */
    public void setSortId(Long sortId) {
        this.sortId = sortId;
    }
}