package com.sunyue.myblog.controller.adminController;

import com.github.pagehelper.PageInfo;
import com.sunyue.myblog.commons.EmptyUtil;
import com.sunyue.myblog.commons.PageUtil;
import com.sunyue.myblog.entity.BaseResult;
import com.sunyue.myblog.entity.Users;
import com.sunyue.myblog.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Date;
import java.util.List;

@Controller
@RequestMapping(value = "admin")
public class UserController {
    @Autowired
    private UserService userService;

    @RequestMapping(value = "/userList", method = RequestMethod.GET)
    private String userList(Model model, @RequestParam(defaultValue = "1") int pageNum,
                            @RequestParam(required = false) String userName,
                            @RequestParam(required = false) Integer flag) {
        Users users = new Users();
        if (!EmptyUtil.isEmpty(userName)) {
            users.setUserName(userName);
        }
        if (!EmptyUtil.isEmpty(flag)) {
            if (flag != 2) {
                users.setUserStatus(flag);
            }
        }
        //pageNum:当前页码，pageSize:每页显示条数,条件为对象
        PageInfo<Users> page = userService.page(pageNum, 5, users);
        //保证不是空指针，index是用于页码设置的
        if (page.getList() != null) {
            List<Integer> index = PageUtil.getIndex(pageNum, page.getPages(), 2);
            model.addAttribute("index", index);
        }
        model.addAttribute("page", page);
        model.addAttribute("flag", users.getUserStatus());
        model.addAttribute("userName", users.getUserName());
        return "admin/user/user_list";
    }

    @RequestMapping(value = "/userAdd", method = RequestMethod.GET)
    private String userAdd(Model model) {
        model.addAttribute("user", new Users());
        return "admin/user/user_add";
    }

    @RequestMapping(value = "/userAdd", method = RequestMethod.POST)
    private String userAdd(RedirectAttributes redirectAttributes, Model model, Users users, @RequestParam(required = false) String status) {
        ;
        /*判断是否启用管理员账号*/
        if (status == null || status.length() == 0) {
            users.setUserStatus(0);
        } else {
            users.setUserStatus(1);
        }
        /*设置时间*/
        users.setUserCreateTime(new Date());
        users.setUserLoginTime(new Date());
        BaseResult baseResult = userService.add(users);
        if (baseResult.getStatus() == 500) {
            model.addAttribute("user", users);
            model.addAttribute("messages",baseResult.getMessage());
            return "admin/user/user_add";
        } else {
            redirectAttributes.addFlashAttribute("messages", baseResult.getMessage());
            return "redirect:userList";
        }
    }


    @RequestMapping(value = "/userUpdate", method = RequestMethod.GET)
    private String userUpdate(Model model, @RequestParam String userId) {
        Users users = userService.selectById(userId);
        model.addAttribute("user", users);
        return "admin/user/user_update";
    }

    @RequestMapping(value = "/userUpdate", method = RequestMethod.POST)
    private String userUpdate(RedirectAttributes redirectAttributes,Model model, Users users, @RequestParam(required = false) String status) {
        /*判断是否启用管理员账号*/
        if (status == null || status.length() == 0) {
            users.setUserStatus(0);
        } else {
            users.setUserStatus(1);
        }
        BaseResult baseResult = userService.update(users);
        if (baseResult.getStatus() == 500) {
            model.addAttribute("user", users);
            model.addAttribute("messages",baseResult.getMessage());
            return "admin/user/user_update";
        }
        redirectAttributes.addFlashAttribute("messages", baseResult.getMessage());
        return "redirect:userList";
    }

    @RequestMapping(value = "/userDelete", method = RequestMethod.GET)
    private String userDelete(RedirectAttributes redirectAttributes, @RequestParam String userId) {
        BaseResult baseResult = userService.delete(userId);
        redirectAttributes.addFlashAttribute("messages", baseResult.getMessage());
        return "redirect:userList";
    }
}
