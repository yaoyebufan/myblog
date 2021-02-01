package com.sunyue.myblog.entity;

import javax.persistence.*;

public class Sorts {
    /**
     * 分类ID
     */
    @Id
    @Column(name = "sort_id")
    private Long sortId;

    /**
     * 父分类ID
     */
    @Column(name = "parent_id")
    private Long parentId;

    /**
     * 分类名
     */
    @Column(name = "sort_name")
    private String sortName;

    /**
     * 分类别名
     */
    @Column(name = "sort_alias")
    private String sortAlias;

    /**
     * 分类状态
     */
    @Column(name = "sort_status")
    private Integer sortStatus;

    /**
     * 分类描述
     */
    @Column(name = "sort_descripation")
    private String sortDescripation;

    /**
     * 获取分类ID
     *
     * @return sort_id - 分类ID
     */
    public Long getSortId() {
        return sortId;
    }

    /**
     * 设置分类ID
     *
     * @param sortId 分类ID
     */
    public void setSortId(Long sortId) {
        this.sortId = sortId;
    }

    /**
     * 获取父分类ID
     *
     * @return parent_id - 父分类ID
     */
    public Long getParentId() {
        return parentId;
    }

    /**
     * 设置父分类ID
     *
     * @param parentId 父分类ID
     */
    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    /**
     * 获取分类名
     *
     * @return sort_name - 分类名
     */
    public String getSortName() {
        return sortName;
    }

    /**
     * 设置分类名
     *
     * @param sortName 分类名
     */
    public void setSortName(String sortName) {
        this.sortName = sortName;
    }

    /**
     * 获取分类别名
     *
     * @return sort_alias - 分类别名
     */
    public String getSortAlias() {
        return sortAlias;
    }

    /**
     * 设置分类别名
     *
     * @param sortAlias 分类别名
     */
    public void setSortAlias(String sortAlias) {
        this.sortAlias = sortAlias;
    }

    /**
     * 获取分类状态
     *
     * @return sort_status - 分类状态
     */
    public Integer getSortStatus() {
        return sortStatus;
    }

    /**
     * 设置分类状态
     *
     * @param sortStatus 分类状态
     */
    public void setSortStatus(Integer sortStatus) {
        this.sortStatus = sortStatus;
    }

    /**
     * 获取分类描述
     *
     * @return sort_descripation - 分类描述
     */
    public String getSortDescripation() {
        return sortDescripation;
    }

    /**
     * 设置分类描述
     *
     * @param sortDescripation 分类描述
     */
    public void setSortDescripation(String sortDescripation) {
        this.sortDescripation = sortDescripation;
    }
}