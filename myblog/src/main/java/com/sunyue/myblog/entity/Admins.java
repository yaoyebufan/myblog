package com.sunyue.myblog.entity;

import java.util.Date;
import javax.persistence.*;

public class Admins {
    /**
     * 管理员ID
     */
    @Id
    @Column(name = "admin_id")
    private Long adminId;

    /**
     * 管理员名
     */
    @Column(name = "admin_name")
    private String adminName;

    /**
     * 管理员密码
     */
    @Column(name = "admin_password")
    private String adminPassword;

    /**
     * 管理员头像
     */
    @Column(name = "admin_photo")
    private String adminPhoto;

    /**
     * 管理员邮箱
     */
    @Column(name = "admin_email")
    private String adminEmail;

    /**
     * 管理员手机号
     */
    @Column(name = "admin_phone")
    private String adminPhone;

    /**
     * 注册时间
     */
    @Column(name = "admin_create_time")
    private Date adminCreateTime;

    /**
     * 上次登录时间
     */
    @Column(name = "admin_login_time")
    private Date adminLoginTime;

    /**
     * 管理员状态
     */
    @Column(name = "admin_status")
    private Integer adminStatus;

    /**
     * 获取管理员ID
     *
     * @return admin_id - 管理员ID
     */
    public Long getAdminId() {
        return adminId;
    }

    /**
     * 设置管理员ID
     *
     * @param adminId 管理员ID
     */
    public void setAdminId(Long adminId) {
        this.adminId = adminId;
    }

    /**
     * 获取管理员名
     *
     * @return admin_name - 管理员名
     */
    public String getAdminName() {
        return adminName;
    }

    /**
     * 设置管理员名
     *
     * @param adminName 管理员名
     */
    public void setAdminName(String adminName) {
        this.adminName = adminName;
    }

    /**
     * 获取管理员密码
     *
     * @return admin_password - 管理员密码
     */
    public String getAdminPassword() {
        return adminPassword;
    }

    /**
     * 设置管理员密码
     *
     * @param adminPassword 管理员密码
     */
    public void setAdminPassword(String adminPassword) {
        this.adminPassword = adminPassword;
    }

    /**
     * 获取管理员头像
     *
     * @return admin_photo - 管理员头像
     */
    public String getAdminPhoto() {
        return adminPhoto;
    }

    /**
     * 设置管理员头像
     *
     * @param adminPhoto 管理员头像
     */
    public void setAdminPhoto(String adminPhoto) {
        this.adminPhoto = adminPhoto;
    }

    /**
     * 获取管理员邮箱
     *
     * @return admin_email - 管理员邮箱
     */
    public String getAdminEmail() {
        return adminEmail;
    }

    /**
     * 设置管理员邮箱
     *
     * @param adminEmail 管理员邮箱
     */
    public void setAdminEmail(String adminEmail) {
        this.adminEmail = adminEmail;
    }

    /**
     * 获取管理员手机号
     *
     * @return admin_phone - 管理员手机号
     */
    public String getAdminPhone() {
        return adminPhone;
    }

    /**
     * 设置管理员手机号
     *
     * @param adminPhone 管理员手机号
     */
    public void setAdminPhone(String adminPhone) {
        this.adminPhone = adminPhone;
    }

    /**
     * 获取注册时间
     *
     * @return admin_create_time - 注册时间
     */
    public Date getAdminCreateTime() {
        return adminCreateTime;
    }

    /**
     * 设置注册时间
     *
     * @param adminCreateTime 注册时间
     */
    public void setAdminCreateTime(Date adminCreateTime) {
        this.adminCreateTime = adminCreateTime;
    }

    /**
     * 获取上次登录时间
     *
     * @return admin_login_time - 上次登录时间
     */
    public Date getAdminLoginTime() {
        return adminLoginTime;
    }

    /**
     * 设置上次登录时间
     *
     * @param adminLoginTime 上次登录时间
     */
    public void setAdminLoginTime(Date adminLoginTime) {
        this.adminLoginTime = adminLoginTime;
    }

    /**
     * 获取管理员状态
     *
     * @return admin_status - 管理员状态
     */
    public Integer getAdminStatus() {
        return adminStatus;
    }

    /**
     * 设置管理员状态
     *
     * @param adminStatus 管理员状态
     */
    public void setAdminStatus(Integer adminStatus) {
        this.adminStatus = adminStatus;
    }
}