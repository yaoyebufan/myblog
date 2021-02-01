package com.sunyue.myblog.controller.uiController;


import com.github.pagehelper.PageInfo;
import com.sunyue.myblog.commons.EmptyUtil;
import com.sunyue.myblog.commons.ListRemove;
import com.sunyue.myblog.commons.PageUtil;
import com.sunyue.myblog.entity.*;
import com.sunyue.myblog.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;;


@Controller
public class BlogController {
    @Autowired
    private ArticleService articleService;
    @Autowired
    private UserService userService;
    @Autowired
    private AdminService adminService;
    @Autowired
    private MessageService messageService;
    @Autowired
    private LabelService labelService;

    /*主页*/
    @RequestMapping(value = {"", "index"}, method = RequestMethod.GET)
    public String index(Model model, @RequestParam(defaultValue = "1") int pageNum) {
        /*获取六篇文章*/
        //pageNum:当前页码，pageSize:每页显示条数,条件为对象
        PageInfo<Articles> page = articleService.page(pageNum, 6, new Articles());
        //保证不是空指针，index是用于页码设置的
        if (page.getList() != null) {
            List<Integer> index = PageUtil.getIndex(pageNum, page.getPages(), 2);
            model.addAttribute("index", index);
        }
        /*为了获取管理员admin对象中adminId和博文对象userId对比得到adminName*/
        List<Articles> articles = articleService.selectArticleByRow(6);
        List<Admins> adminsList = new ArrayList<>();
        if (!EmptyUtil.isEmpty(articles)) {
            //去重
            articles = ListRemove.removeArticles(articles);
            for (Articles article : articles) {
                adminsList.add(adminService.selectById(article.getUserId().toString()));
            }
        }
        /*为了让pageList不随页码变化而变化，只显示最前的6条,而且不去重*/
        model.addAttribute("articles",articleService.selectArticleByRow(6));
        model.addAttribute("page", page);
        model.addAttribute("adminsList", adminsList);
        /*获取六个留言*/
        PageInfo<Messages> messagesPageInfo = messageService.page(1, 6, new Messages(), null);
        /*为了获取用户users对象中userId和留言对象userId对比得到userName*/
        List<Messages> messages = messageService.selectMessagesByRow(6);
        List<Users> usersList = new ArrayList<>();
        if (!EmptyUtil.isEmpty(messages)) {
            //去重
            messages = ListRemove.removeMessages(messages);
            for (Messages message : messages) {
                usersList.add(userService.selectById(message.getUserId().toString()));
            }
        }
        /*为了让pageList不随页码变化而变化，只显示最前的6条,而且不去重*/
        model.addAttribute("messages",messageService.selectMessagesByRow(6));
        /*留言和留言用户*/
        model.addAttribute("messagesList", messagesPageInfo);
        model.addAttribute("usersList", usersList);
        /*所有标签*/
        List<Labels> allLabel = articleService.selectAllLabel();
        model.addAttribute("allLabel",allLabel);
        /*数量*/
        model.addAttribute("articleCount",articleService.count());
        model.addAttribute("labelCount",labelService.count());
        model.addAttribute("messageCount",messageService.count());
        return "ui/blog";
    }
}
