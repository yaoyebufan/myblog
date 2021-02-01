package com.sunyue.myblog.entity;

import java.util.Date;
import javax.persistence.*;

public class Articles {
    /**
     * 文章ID
     */
    @Id
    @Column(name = "article_id")
    private Long articleId;

    /**
     * 用户ID
     */
    @Column(name = "user_id")
    private Long userId;

    /**
     * 文章外链
     */
    @Column(name = "article_resource")
    private String articleResource;

    /**
     * 文章浏览量
     */
    @Column(name = "article_views")
    private Long articleViews;

    /**
     * 文章点赞数
     */
    @Column(name = "article_likes")
    private Long articleLikes;

    /**
     * 文章评论数
     */
    @Column(name = "article_comments")
    private Long articleComments;

    /**
     * 文章创建时间
     */
    @Column(name = "article_create_time")
    private Date articleCreateTime;

    /**
     * 文章修改时间
     */
    @Column(name = "article_update_time")
    private Date articleUpdateTime;

    /**
     * 文章状态
     */
    @Column(name = "article_status")
    private Integer articleStatus;

    /**
     * 文章标题
     */
    @Column(name = "article_title")
    private String articleTitle;

    /**
     * md格式
     */
    @Column(name = "article_content_md")
    private String articleContentMd;

    /**
     * 文章内容
     */
    @Column(name = "article_content")
    private String articleContent;

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
     * 获取用户ID
     *
     * @return user_id - 用户ID
     */
    public Long getUserId() {
        return userId;
    }

    /**
     * 设置用户ID
     *
     * @param userId 用户ID
     */
    public void setUserId(Long userId) {
        this.userId = userId;
    }

    /**
     * 获取文章外链
     *
     * @return article_resource - 文章外链
     */
    public String getArticleResource() {
        return articleResource;
    }

    /**
     * 设置文章外链
     *
     * @param articleResource 文章外链
     */
    public void setArticleResource(String articleResource) {
        this.articleResource = articleResource;
    }

    /**
     * 获取文章浏览量
     *
     * @return article_views - 文章浏览量
     */
    public Long getArticleViews() {
        return articleViews;
    }

    /**
     * 设置文章浏览量
     *
     * @param articleViews 文章浏览量
     */
    public void setArticleViews(Long articleViews) {
        this.articleViews = articleViews;
    }

    /**
     * 获取文章点赞数
     *
     * @return article_likes - 文章点赞数
     */
    public Long getArticleLikes() {
        return articleLikes;
    }

    /**
     * 设置文章点赞数
     *
     * @param articleLikes 文章点赞数
     */
    public void setArticleLikes(Long articleLikes) {
        this.articleLikes = articleLikes;
    }

    /**
     * 获取文章评论数
     *
     * @return article_comments - 文章评论数
     */
    public Long getArticleComments() {
        return articleComments;
    }

    /**
     * 设置文章评论数
     *
     * @param articleComments 文章评论数
     */
    public void setArticleComments(Long articleComments) {
        this.articleComments = articleComments;
    }

    /**
     * 获取文章创建时间
     *
     * @return article_create_time - 文章创建时间
     */
    public Date getArticleCreateTime() {
        return articleCreateTime;
    }

    /**
     * 设置文章创建时间
     *
     * @param articleCreateTime 文章创建时间
     */
    public void setArticleCreateTime(Date articleCreateTime) {
        this.articleCreateTime = articleCreateTime;
    }

    /**
     * 获取文章修改时间
     *
     * @return article_update_time - 文章修改时间
     */
    public Date getArticleUpdateTime() {
        return articleUpdateTime;
    }

    /**
     * 设置文章修改时间
     *
     * @param articleUpdateTime 文章修改时间
     */
    public void setArticleUpdateTime(Date articleUpdateTime) {
        this.articleUpdateTime = articleUpdateTime;
    }

    /**
     * 获取文章状态
     *
     * @return article_status - 文章状态
     */
    public Integer getArticleStatus() {
        return articleStatus;
    }

    /**
     * 设置文章状态
     *
     * @param articleStatus 文章状态
     */
    public void setArticleStatus(Integer articleStatus) {
        this.articleStatus = articleStatus;
    }

    /**
     * 获取文章标题
     *
     * @return article_title - 文章标题
     */
    public String getArticleTitle() {
        return articleTitle;
    }

    /**
     * 设置文章标题
     *
     * @param articleTitle 文章标题
     */
    public void setArticleTitle(String articleTitle) {
        this.articleTitle = articleTitle;
    }

    /**
     * 获取md格式
     *
     * @return article_content_md - md格式
     */
    public String getArticleContentMd() {
        return articleContentMd;
    }

    /**
     * 设置md格式
     *
     * @param articleContentMd md格式
     */
    public void setArticleContentMd(String articleContentMd) {
        this.articleContentMd = articleContentMd;
    }

    /**
     * 获取文章内容
     *
     * @return article_content - 文章内容
     */
    public String getArticleContent() {
        return articleContent;
    }

    /**
     * 设置文章内容
     *
     * @param articleContent 文章内容
     */
    public void setArticleContent(String articleContent) {
        this.articleContent = articleContent;
    }
}