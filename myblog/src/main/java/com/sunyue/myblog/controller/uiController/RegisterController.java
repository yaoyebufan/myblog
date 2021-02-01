package com.sunyue.myblog.controller.uiController;

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

@Controller
public class RegisterController {
    @Autowired
    private UserService userService;

    @RequestMapping(value = "register", method = RequestMethod.GET)
    private String register(Model model) {
        model.addAttribute("user", new Users());
        return "ui/register";
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    private String register(Model model, Users users) {
        users.setUserStatus(1);
        /*设置时间*/
        users.setUserCreateTime(new Date());
        users.setUserLoginTime(new Date());
        BaseResult baseResult = userService.add(users);
        if (baseResult.getStatus() == 500) {
            model.addAttribute("user", users);
            model.addAttribute("messages", baseResult.getMessage());
            return "ui/register";
        } else {
            return "redirect:/login";
        }
    }

}
