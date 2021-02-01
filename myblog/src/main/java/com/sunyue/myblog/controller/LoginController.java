package com.sunyue.myblog.controller;

import com.sunyue.myblog.commons.EmptyUtil;
import com.sunyue.myblog.entity.Admins;
import com.sunyue.myblog.entity.BaseResult;
import com.sunyue.myblog.entity.Users;
import com.sunyue.myblog.service.AdminService;
import com.sunyue.myblog.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.bind.support.SimpleSessionStatus;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.Map;

@Controller
public class LoginController {
    @Autowired
    private UserService userService;
    @Autowired
    private AdminService adminService;

    @RequestMapping(value = "login", method = RequestMethod.GET)
    private String login() {
        return "ui/login";
    }

    @RequestMapping(value = "login", method = RequestMethod.POST)
    private String login(Model model, @RequestParam String email, @RequestParam String password, HttpServletRequest request) {
        Map map = userService.login(email, password);
        Users user = (Users) map.get("user");
        BaseResult baseResult = (BaseResult) map.get("message");
        if (!EmptyUtil.isEmpty(user)) {
            HttpSession session = request.getSession(true);
            session.setAttribute("user", user);
            return "redirect:/";
        } else {
            HttpSession session = request.getSession(true);
            SessionStatus sessionStatus = new SimpleSessionStatus();
            loginOut(session, sessionStatus, "user");
            model.addAttribute("userEmail", email);
            model.addAttribute("userPassword", password);
            model.addAttribute("messages", baseResult.getMessage());
            return "ui/login";
        }
    }

    @RequestMapping(value = "admin/login", method = RequestMethod.GET)
    private String admin_login(Model model) {
        model.addAttribute("admin", new Admins());
        return "admin/login";
    }

    @RequestMapping(value = "admin/login", method = RequestMethod.POST)
    private String admin_login(Model model, @RequestParam String email, @RequestParam String password, HttpServletRequest request) {
        //System.out.println(request.getRequestURL());
        Map map = adminService.login(email, password);
        Admins admin = (Admins) map.get("admin");
        BaseResult baseResult = (BaseResult) map.get("baseResult");
        if (!EmptyUtil.isEmpty(admin)) {
            admin.setAdminLoginTime(new Date());
            adminService.update(admin);
            HttpSession session = request.getSession(true);
            session.setAttribute("admin", admin);
            request.getSession().setAttribute("admin", admin);
            return "redirect:/admin/";
        } else {
            HttpSession session = request.getSession(true);
            SessionStatus sessionStatus = new SimpleSessionStatus();
            loginOut(session, sessionStatus, "admin");
            model.addAttribute("adminEmail", email);
            model.addAttribute("adminPassword", password);
            model.addAttribute("messages", baseResult.getMessage());
            return "admin/login";
        }
    }

    @RequestMapping(value = "admin/out", method = RequestMethod.GET)
    private String adminLogout(HttpServletRequest request) {
        HttpSession session = request.getSession(true);
        SessionStatus sessionStatus = new SimpleSessionStatus();
        loginOut(session, sessionStatus, "admin");
        return "admin/login";
    }
    @RequestMapping(value = "/out", method = RequestMethod.GET)
    private String userLogout(HttpServletRequest request) {
        HttpSession session = request.getSession(true);
        SessionStatus sessionStatus = new SimpleSessionStatus();
        loginOut(session, sessionStatus, "user");
        return "redirect:/login";
    }

    private void loginOut(HttpSession session, SessionStatus sessionStatus, String obj) {
        session.removeAttribute(obj);//我这里是先取出httpsession中的user属性
        session.invalidate();  //然后是让httpsession失效
        sessionStatus.setComplete();//最后是调用sessionStatus方法
    }
}
