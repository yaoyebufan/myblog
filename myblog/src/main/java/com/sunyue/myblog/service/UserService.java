package com.sunyue.myblog.service;

import com.github.pagehelper.PageInfo;
import com.sunyue.myblog.entity.Admins;
import com.sunyue.myblog.entity.BaseResult;
import com.sunyue.myblog.entity.Users;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public interface UserService {
    /*用户登录处理*/
    Map login(String email, String pwd);
    /*获取所有用户信息*/
    List<Users> list();
    /*添加用户账号*/
    BaseResult add(Users users);
    /*分页获取数据*/
    PageInfo<Users> page(int pageNum, int pageSize, Users users);
    /*通过userId获取一个账号信息*/
    Users selectById(String userId);
    /*通过userName获取一个账号信息*/
    Users selectByUserName(String userName);
    /*更新用户信息*/
    BaseResult update(Users users);
    /*删除一个用户*/
    BaseResult delete(String userId);
}
