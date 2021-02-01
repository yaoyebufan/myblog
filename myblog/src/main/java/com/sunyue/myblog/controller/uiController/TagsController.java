package com.sunyue.myblog.controller.uiController;

import com.github.pagehelper.PageHelper;
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
import java.util.List;

@Controller
public class TagsController {
    @Autowired
    private ArticleService articleService;
    @Autowired
    private ArticleLabelService articleLabelService;
    @Autowired
    private AdminService adminService;
    @Autowired
    private LabelService labelService;
    /*标签页*/
    @RequestMapping(value = {"label"}, method = RequestMethod.GET)
    public String label(Model model){
        List<Labels> allLabel = articleService.selectAllLabel();
        model.addAttribute("allLabel",allLabel);
        return "ui/label";
    }
    @RequestMapping(value = {"label_con"}, method = RequestMethod.GET)
    public String toLabel(Model model,@RequestParam(required = false) String labelName, @RequestParam(defaultValue = "1") int pageNum){
        /*获取所有链接表*/
        List<ArticleLabel> articleLabels = articleLabelService.list();
        model.addAttribute("articleLabels", articleLabels);
        /*获取每一个标签名*/
        List<Labels> labelsList = articleService.selectAllLabel();
        model.addAttribute("labelsList", labelsList);
        /*获取文章作者*/
        List<Articles> articlesList = articleService.selectArticleByLabel(labelName);
        List<Admins> adminsList = new ArrayList<>();
        if (!EmptyUtil.isEmpty(articlesList)) {
            //去重
            articlesList = ListRemove.removeArticles(articlesList);
            for (Articles articles : articlesList) {
                adminsList.add(adminService.selectById(articles.getUserId().toString()));
            }
        }
        model.addAttribute("adminsList", adminsList);
        if (!EmptyUtil.isEmpty(labelName)) {
            /*
             1. 静态方法，传递两个参数（当前页码，每页查询条数）
             2. 使用pageHelper 分页的时候，不再关注分页语句，查询全部的语句
             3. 自动的对PageHelper.startPage 方法下的第一个sql 查询进行分页
             */
            /*先获取到labelId*/
            Labels labels1 = labelService.selectByLabelName(labelName);
            PageHelper.startPage(pageNum, 4);
            List<ArticleLabel> articleLabels1 = articleLabelService.selectByLabelId(labels1.getLabelId().toString());
            PageInfo<ArticleLabel> page = new PageInfo<>(articleLabels1);
            //保证不是空指针，index是用于页码设置的
            if (page.getList() != null&&page.getList().size()!=0) {
                List<Integer> index = PageUtil.getIndex(pageNum, page.getPages(), 2);
                model.addAttribute("index", index);
            }
            model.addAttribute("articleLabels", articleLabels);
            model.addAttribute("status", 1);
            model.addAttribute("articlesList", articleService.selectArticleByLabel(labelName));
            model.addAttribute("labelName", labelName);
            model.addAttribute("page", page);
        } else {
            /*设置为空查询所有文章*/
            PageInfo<Articles> page = articleService.page(pageNum, 4, new Articles());
            /*获取分类名*/
            List<Articles> articlesList1 = page.getList();
            model.addAttribute("status", 0);
            model.addAttribute("labelName", "");
            model.addAttribute("page", page);
            //保证不是空指针，index是用于页码设置的
            if (articlesList1 != null) {
                List<Integer> index = PageUtil.getIndex(pageNum, page.getPages(), 2);
                model.addAttribute("index", index);
            }
        }
        return "ui/label_con";
    }

}
