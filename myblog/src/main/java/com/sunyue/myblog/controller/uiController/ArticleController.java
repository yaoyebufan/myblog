package com.sunyue.myblog.controller.uiController;

import com.sunyue.myblog.entity.Admins;
import com.sunyue.myblog.entity.Articles;
import com.sunyue.myblog.entity.Comments;
import com.sunyue.myblog.entity.Users;
import com.sunyue.myblog.service.AdminService;
import com.sunyue.myblog.service.ArticleService;
import com.sunyue.myblog.service.CommentService;
import com.sunyue.myblog.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class ArticleController {
    @Autowired
    private AdminService adminService;
    @Autowired
    private ArticleService articleService;
    @Autowired
    private CommentService commentService;
    @Autowired
    private UserService userService;

    /*文章页*/
    @RequestMapping(value = {"article"}, method = RequestMethod.GET)
    public String article(Model model, @RequestParam String articleId) {
        Articles article = articleService.selectById(articleId);
        Admins admin = adminService.selectById(article.getUserId().toString());
        model.addAttribute("admin", admin);
        model.addAttribute("article", article);
        List<Users> usersList = userService.list();
        model.addAttribute("usersList",usersList);
        List<Comments> commentsList = commentService.selectByArticleId(articleId);
        model.addAttribute("commentsList",commentsList);
        return "ui/article";
    }
}
