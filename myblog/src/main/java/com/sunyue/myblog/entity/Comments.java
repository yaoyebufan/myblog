package com.sunyue.myblog.entity;

import java.util.Date;
import javax.persistence.*;

public class Comments {
    /**
     * 评论ID
     */
    @Id
    @Column(name = "comment_id")
    private Long commentId;

    /**
     * 父评论ID
     */
    @Column(name = "parent_id")
    private Long parentId;

    /**
     * 文章ID
     */
    @Column(name = "article_id")
    private Long articleId;

    /**
     * 用户ID
     */
    @Column(name = "user_id")
    private Long userId;

    /**
     * 评论点赞数
     */
    @Column(name = "comment_likes")
    private Long commentLikes;

    /**
     * 评论创建时间
     */
    @Column(name = "comment_create_time")
    private Date commentCreateTime;

    /**
     * 评论状态
     */
    @Column(name = "comment_status")
    private Integer commentStatus;

    /**
     * 评论内容
     */
    @Column(name = "comment_content")
    private String commentContent;

    /**
     * 获取评论ID
     *
     * @return comment_id - 评论ID
     */
    public Long getCommentId() {
        return commentId;
    }

    /**
     * 设置评论ID
     *
     * @param commentId 评论ID
     */
    public void setCommentId(Long commentId) {
        this.commentId = commentId;
    }

    /**
     * 获取父评论ID
     *
     * @return parent_id - 父评论ID
     */
    public Long getParentId() {
        return parentId;
    }

    /**
     * 设置父评论ID
     *
     * @param parentId 父评论ID
     */
    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

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
     * 获取评论点赞数
     *
     * @return comment_likes - 评论点赞数
     */
    public Long getCommentLikes() {
        return commentLikes;
    }

    /**
     * 设置评论点赞数
     *
     * @param commentLikes 评论点赞数
     */
    public void setCommentLikes(Long commentLikes) {
        this.commentLikes = commentLikes;
    }

    /**
     * 获取评论创建时间
     *
     * @return comment_create_time - 评论创建时间
     */
    public Date getCommentCreateTime() {
        return commentCreateTime;
    }

    /**
     * 设置评论创建时间
     *
     * @param commentCreateTime 评论创建时间
     */
    public void setCommentCreateTime(Date commentCreateTime) {
        this.commentCreateTime = commentCreateTime;
    }

    /**
     * 获取评论状态
     *
     * @return comment_status - 评论状态
     */
    public Integer getCommentStatus() {
        return commentStatus;
    }

    /**
     * 设置评论状态
     *
     * @param commentStatus 评论状态
     */
    public void setCommentStatus(Integer commentStatus) {
        this.commentStatus = commentStatus;
    }

    /**
     * 获取评论内容
     *
     * @return comment_content - 评论内容
     */
    public String getCommentContent() {
        return commentContent;
    }

    /**
     * 设置评论内容
     *
     * @param commentContent 评论内容
     */
    public void setCommentContent(String commentContent) {
        this.commentContent = commentContent;
    }
}