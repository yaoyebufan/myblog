package com.sunyue.myblog.entity;

import java.util.Date;
import javax.persistence.*;

public class Messages {
    /**
     * 留言ID
     */
    @Id
    @Column(name = "message_id")
    private Long messageId;

    @Column(name = "parent_id")
    private Long parentId;

    /**
     * 用户ID
     */
    @Column(name = "user_id")
    private Long userId;

    /**
     * 留言创建时间
     */
    @Column(name = "message_create_time")
    private Date messageCreateTime;

    /**
     * 留言状态
     */
    @Column(name = "message_status")
    private Integer messageStatus;

    /**
     * 留言内容
     */
    @Column(name = "message_content")
    private String messageContent;

    /**
     * 获取留言ID
     *
     * @return message_id - 留言ID
     */
    public Long getMessageId() {
        return messageId;
    }

    /**
     * 设置留言ID
     *
     * @param messageId 留言ID
     */
    public void setMessageId(Long messageId) {
        this.messageId = messageId;
    }

    /**
     * @return parent_id
     */
    public Long getParentId() {
        return parentId;
    }

    /**
     * @param parentId
     */
    public void setParentId(Long parentId) {
        this.parentId = parentId;
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
     * 获取留言创建时间
     *
     * @return message_create_time - 留言创建时间
     */
    public Date getMessageCreateTime() {
        return messageCreateTime;
    }

    /**
     * 设置留言创建时间
     *
     * @param messageCreateTime 留言创建时间
     */
    public void setMessageCreateTime(Date messageCreateTime) {
        this.messageCreateTime = messageCreateTime;
    }

    /**
     * 获取留言状态
     *
     * @return message_status - 留言状态
     */
    public Integer getMessageStatus() {
        return messageStatus;
    }

    /**
     * 设置留言状态
     *
     * @param messageStatus 留言状态
     */
    public void setMessageStatus(Integer messageStatus) {
        this.messageStatus = messageStatus;
    }

    /**
     * 获取留言内容
     *
     * @return message_content - 留言内容
     */
    public String getMessageContent() {
        return messageContent;
    }

    /**
     * 设置留言内容
     *
     * @param messageContent 留言内容
     */
    public void setMessageContent(String messageContent) {
        this.messageContent = messageContent;
    }
}