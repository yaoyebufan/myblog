package com.sunyue.myblog.service;

import com.github.pagehelper.PageInfo;
import com.sunyue.myblog.entity.Admins;
import com.sunyue.myblog.entity.BaseResult;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
@Service
public interface AdminService {
    /*管理员登录处理*/
    Map login(String email, String pwd);
    /*获取所有管理员信息*/
    List<Admins> list();
    /*添加管理员账号*/
    BaseResult add(Admins admins);
    /*分页获取数据*/
    PageInfo<Admins> page(int pageNum, int pageSize,Admins admins);
    /*通过adminId获取一个账号信息*/
    Admins selectById(String adminId);
    /*更新管理员信息*/
    BaseResult update(Admins admins);
    /*删除一个管理员*/
    BaseResult delete(String adminId);
}
