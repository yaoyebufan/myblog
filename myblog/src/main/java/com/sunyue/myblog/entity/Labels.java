package com.sunyue.myblog.entity;

import javax.persistence.*;

public class Labels {
    /**
     * 标签ID
     */
    @Id
    @Column(name = "label_id")
    private Long labelId;

    /**
     * 标签名
     */
    @Column(name = "label_name")
    private String labelName;

    /**
     * 标签别名
     */
    @Column(name = "label_alias")
    private String labelAlias;

    /**
     * 标签状态
     */
    @Column(name = "label_status")
    private Integer labelStatus;

    /**
     * 标签描述
     */
    @Column(name = "label_descripation")
    private String labelDescripation;

    /**
     * 获取标签ID
     *
     * @return label_id - 标签ID
     */
    public Long getLabelId() {
        return labelId;
    }

    /**
     * 设置标签ID
     *
     * @param labelId 标签ID
     */
    public void setLabelId(Long labelId) {
        this.labelId = labelId;
    }

    /**
     * 获取标签名
     *
     * @return label_name - 标签名
     */
    public String getLabelName() {
        return labelName;
    }

    /**
     * 设置标签名
     *
     * @param labelName 标签名
     */
    public void setLabelName(String labelName) {
        this.labelName = labelName;
    }

    /**
     * 获取标签别名
     *
     * @return label_alias - 标签别名
     */
    public String getLabelAlias() {
        return labelAlias;
    }

    /**
     * 设置标签别名
     *
     * @param labelAlias 标签别名
     */
    public void setLabelAlias(String labelAlias) {
        this.labelAlias = labelAlias;
    }

    /**
     * 获取标签状态
     *
     * @return label_status - 标签状态
     */
    public Integer getLabelStatus() {
        return labelStatus;
    }

    /**
     * 设置标签状态
     *
     * @param labelStatus 标签状态
     */
    public void setLabelStatus(Integer labelStatus) {
        this.labelStatus = labelStatus;
    }

    /**
     * 获取标签描述
     *
     * @return label_descripation - 标签描述
     */
    public String getLabelDescripation() {
        return labelDescripation;
    }

    /**
     * 设置标签描述
     *
     * @param labelDescripation 标签描述
     */
    public void setLabelDescripation(String labelDescripation) {
        this.labelDescripation = labelDescripation;
    }
}