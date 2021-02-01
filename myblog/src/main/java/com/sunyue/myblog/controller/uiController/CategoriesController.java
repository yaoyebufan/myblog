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
public class CategoriesController {
    @Autowired
    private SortService sortService;
    @Autowired
    private ArticleService articleService;
    @Autowired
    private ArticleSortService articleSortService;
    @Autowired
    private AdminService adminService;

    /*分类页*/
    @RequestMapping(value = {"sort"}, method = RequestMethod.GET)
    public String sort(Model model, @RequestParam(required = false) String sortName, @RequestParam(defaultValue = "1") int pageNum) {
        /*获取所有链接表*/
        List<ArticleSort> articleSorts = articleSortService.list();
        model.addAttribute("articleSorts", articleSorts);
        /*获取每一个分类名*/
        List<Sorts> sortsList = articleService.selectAllSort();
        model.addAttribute("sortsList", sortsList);
        /*获取文章作者*/
        List<Articles> articlesList = articleService.selectArticleBySort(sortName);
        List<Admins> adminsList = new ArrayList<>();
        if (!EmptyUtil.isEmpty(articlesList)) {
            //去重
            articlesList = ListRemove.removeArticles(articlesList);
            for (Articles articles : articlesList) {
                adminsList.add(adminService.selectById(articles.getUserId().toString()));
            }
        }
        model.addAttribute("adminsList", adminsList);
        if (!EmptyUtil.isEmpty(sortName)) {
            /*
             1. 静态方法，传递两个参数（当前页码，每页查询条数）
             2. 使用pageHelper 分页的时候，不再关注分页语句，查询全部的语句
             3. 自动的对PageHelper.startPage 方法下的第一个sql 查询进行分页
             */
            /*先获取到sortId*/
            Sorts sorts1 = sortService.selectBySortName(sortName);
            PageHelper.startPage(pageNum, 4);
            List<ArticleSort> articleSorts1 = articleSortService.selectBySortId(sorts1.getSortId().toString());
            PageInfo<ArticleSort> page = new PageInfo<>(articleSorts1);

            //保证不是空指针，index是用于页码设置的
            if (page.getList() != null && page.getList().size() != 0) {
                List<Integer> index = PageUtil.getIndex(pageNum, page.getPages(), 2);
                model.addAttribute("index", index);
            }
            model.addAttribute("articleSorts", articleSorts);
            model.addAttribute("status", 1);
            model.addAttribute("articlesList", articleService.selectArticleBySort(sortName));
            model.addAttribute("sortName", sortName);
            model.addAttribute("page", page);
        } else {
            /*设置为空查询所有文章*/
            PageInfo<Articles> page = articleService.page(pageNum, 4, new Articles());
            /*获取分类名*/
            List<Articles> articlesList1 = page.getList();
            model.addAttribute("status", 0);
            model.addAttribute("sortName", "");
            model.addAttribute("page", page);
            //保证不是空指针，index是用于页码设置的
            if (articlesList1 != null) {
                List<Integer> index = PageUtil.getIndex(pageNum, page.getPages(), 2);
                model.addAttribute("index", index);
            }
        }
        return "ui/categories";
    }
}
