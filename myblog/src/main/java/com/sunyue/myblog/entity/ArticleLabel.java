package com.sunyue.myblog.entity;

import javax.persistence.*;

@Table(name = "article_label")
public class ArticleLabel {
    /**
     * 文章ID
     */
    @Id
    @Column(name = "article_id")
    private Long articleId;

    /**
     * 文章标签连接表ID
     */
    @Id
    @Column(name = "label_id")
    private Long labelId;

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
     * 获取文章标签连接表ID
     *
     * @return label_id - 文章标签连接表ID
     */
    public Long getLabelId() {
        return labelId;
    }

    /**
     * 设置文章标签连接表ID
     *
     * @param labelId 文章标签连接表ID
     */
    public void setLabelId(Long labelId) {
        this.labelId = labelId;
    }
}