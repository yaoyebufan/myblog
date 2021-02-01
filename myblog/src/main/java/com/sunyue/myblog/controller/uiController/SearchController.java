package com.sunyue.myblog.controller.uiController;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.sunyue.myblog.commons.EmptyUtil;
import com.sunyue.myblog.commons.ListRemove;
import com.sunyue.myblog.commons.PageUtil;
import com.sunyue.myblog.entity.Admins;
import com.sunyue.myblog.entity.ArticleLabel;
import com.sunyue.myblog.entity.Articles;
import com.sunyue.myblog.entity.Labels;
import com.sunyue.myblog.service.AdminService;
import com.sunyue.myblog.service.ArticleLabelService;
import com.sunyue.myblog.service.ArticleService;
import com.sunyue.myblog.service.LabelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

@Controller
public class SearchController {
    @Autowired
    private ArticleService articleService;
    @Autowired
    private ArticleLabelService articleLabelService;
    @Autowired
    private AdminService adminService;
    @Autowired
    private LabelService labelService;

    /*搜索页*/
    @RequestMapping(value = {"search"}, method = RequestMethod.GET)
    public String search(Model model, @RequestParam(required = false) String articleTitle, @RequestParam(defaultValue = "1") int pageNum) {
        /*获取所有链接表*/
        List<ArticleLabel> articleLabels = articleLabelService.list();
        model.addAttribute("articleLabels", articleLabels);
        /*获取每一个标签名*/
        List<Labels> labelsList = articleService.selectAllLabel();
        model.addAttribute("labelsList", labelsList);
        /*获取文章作者*/
        List<Articles> articlesList = articleService.selectByArticleTitle(articleTitle);
        List<Admins> adminsList = new ArrayList<>();
        if (!EmptyUtil.isEmpty(articlesList)) {
            //去重
            articlesList = ListRemove.removeArticles(articlesList);
            for (Articles articles : articlesList) {
                adminsList.add(adminService.selectById(articles.getUserId().toString()));
            }
        }

        model.addAttribute("adminsList", adminsList);
        Articles article = new Articles();
        article.setArticleTitle(articleTitle);
        PageInfo<Articles> page = articleService.page(pageNum, 4, article);
        model.addAttribute("page", page);
        //保证不是空指针，index是用于页码设置的
        if (page.getList() != null) {
            List<Integer> index = PageUtil.getIndex(pageNum, page.getPages(), 2);
            model.addAttribute("index", index);
        }
        if (articleTitle==null||articleTitle.equals("")){
            model.addAttribute("articleTitle","");
        }else {
            model.addAttribute("articleTitle",articleTitle);
        }

        return "ui/search";
    }
}
