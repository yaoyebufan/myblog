package com.sunyue.myblog.controller.uiController;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.sunyue.myblog.commons.EmptyUtil;
import com.sunyue.myblog.commons.ListRemove;
import com.sunyue.myblog.commons.PageUtil;
import com.sunyue.myblog.entity.Admins;
import com.sunyue.myblog.entity.Articles;
import com.sunyue.myblog.service.AdminService;
import com.sunyue.myblog.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

@Controller
public class ArchivesController {
    @Autowired
    private ArticleService articleService;
    @Autowired
    private AdminService adminService;

    /*归档页*/
    @RequestMapping(value = {"archive"}, method = RequestMethod.GET)
    public String sort(Model model, @RequestParam(required = false) String dateTime, @RequestParam(defaultValue = "1") int pageNum) {
        /*获取文章作者*/
        List<Articles> articlesList = articleService.selectByDate(dateTime);
        List<Admins> adminsList = new ArrayList<>();
        if (!EmptyUtil.isEmpty(articlesList)) {
            //去重
            articlesList = ListRemove.removeArticles(articlesList);
            for (Articles articles : articlesList) {
                adminsList.add(adminService.selectById(articles.getUserId().toString()));
            }
        }
        model.addAttribute("adminsList", adminsList);

        List<String> dateList = articleService.selectAllDate();
        model.addAttribute("dateList", dateList);

        PageHelper.startPage(pageNum, 4);
        List<Articles> articlesList1 = articleService.selectByDate(dateTime);
        PageInfo<Articles> articlesPageInfo = new PageInfo<>(articlesList1);
        model.addAttribute("articlesPageInfo", articlesPageInfo);

        //保证不是空指针，index是用于页码设置的
        if (articlesPageInfo.getList() != null && articlesPageInfo.getList().size() != 0) {
            List<Integer> index = PageUtil.getIndex(pageNum, articlesPageInfo.getPages(), 2);
            model.addAttribute("index", index);
        }
        /*传递时间可能为空*/
        if (dateTime == null || dateTime.equals("")) {
            model.addAttribute("dateTime", "");
        } else {
            model.addAttribute("dateTime", dateTime);
        }
        /*传递总文章数*/
        model.addAttribute("articleCount",articleService.count());
        return "ui/archives";
    }
}
