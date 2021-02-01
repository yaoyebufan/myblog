package com.sunyue.myblog.controller.adminController;

import com.github.pagehelper.PageInfo;
import com.sunyue.myblog.commons.EmptyUtil;
import com.sunyue.myblog.commons.PageUtil;
import com.sunyue.myblog.entity.Articles;
import com.sunyue.myblog.entity.BaseResult;
import com.sunyue.myblog.entity.Labels;
import com.sunyue.myblog.entity.Sorts;
import com.sunyue.myblog.service.*;
import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.*;

@Controller
@RequestMapping(value = "admin")
public class ArticlesController {
    @Autowired
    private ArticleService articleService;
    @Autowired
    private SortService sortService;
    @Autowired
    private LabelService labelService;
    @Autowired
    private ArticleSortService articleSortService;
    @Autowired
    private ArticleLabelService articleLabelService;


    @RequestMapping(value = "/articleList", method = RequestMethod.GET)
    private String articleList(Model model, @RequestParam(defaultValue = "1") int pageNum,
                               @RequestParam(required = false) String articleTitle,
                               @RequestParam(required = false) Integer flag) {
        Articles articles = new Articles();
        if (!EmptyUtil.isEmpty(articleTitle)) {
            articles.setArticleTitle(articleTitle);
        }
        if (!EmptyUtil.isEmpty(flag)) {
            if (flag != 2) {
                articles.setArticleStatus(flag);
            }
        }
        //pageNum:当前页码，pageSize:每页显示条数,条件为对象
        PageInfo<Articles> page = articleService.page(pageNum, 5, articles);
        //保证不是空指针，index是用于页码设置的
        if (page.getList() != null) {
            List<Integer> index = PageUtil.getIndex(pageNum, page.getPages(), 2);
            model.addAttribute("index", index);
        }
        model.addAttribute("page", page);
        model.addAttribute("flag", articles.getArticleStatus());
        model.addAttribute("articleTitle", articles.getArticleTitle());
        return "admin/article/article_list";
    }

    @RequestMapping(value = "/articleAdd", method = RequestMethod.GET)
    private String articleAdd(Model model,HttpServletRequest request) {
        List<Sorts> sortsList = sortService.list();
        List<Labels> labelsList = labelService.list();
        model.addAttribute("sortsList", sortsList);
        model.addAttribute("labelsList",labelsList);
        model.addAttribute("article", new Articles());
        /*已选中*/
        /*分类列表*/
        //String[] sortCheckboxes = request.getParameterValues("sortCheckbox");

        /*标签列表*/
        //String[] labelCheckboxes = request.getParameterValues("labelCheckbox");
        //model.addAttribute("sortCheckboxes",sortCheckboxes);
        //model.addAttribute("labelCheckboxes",labelCheckboxes);
        return "admin/article/article_add";
    }

    @RequestMapping(value = "/articleAdd", method = RequestMethod.POST)
    private String articleAdd(RedirectAttributes redirectAttributes, Model model, Articles articles, HttpServletRequest request, @RequestParam(required = false) String status) {

        /*判断是否启用博文*/
        if (status == null || status.length() == 0) {
            articles.setArticleStatus(0);
        } else {
            articles.setArticleStatus(1);
        }
        /*暂时设置用户为1*/
        articles.setUserId(1L);
        /*博文内容机器mark格式单独获取*/
        articles.setArticleContent(request.getParameter("editormd-html-textarea"));
        articles.setArticleContentMd(request.getParameter("test-editormd-markdown-doc"));
        /*设置时间*/
        articles.setArticleUpdateTime(new Date());
        articles.setArticleCreateTime(new Date());
        /*分类列表*/
        String[] sortCheckboxes = request.getParameterValues("sortCheckbox");
        /*标签列表*/
        String[] labelCheckboxes = request.getParameterValues("labelCheckbox");
        /*单独判断分类标签，不然会报空指针异常*/
        if (sortCheckboxes == null||labelCheckboxes==null) {
            //model.addAttribute("sortCheckboxes",sortCheckboxes);
            //model.addAttribute("labelCheckboxes",labelCheckboxes);
            model.addAttribute("article", articles);
            model.addAttribute("sortsList", sortService.list());
            model.addAttribute("labelsList", labelService.list());
            model.addAttribute("messages","至少选择一个分类或分类");
            return "admin/article/article_add";
        }
        /*添加博文*/
        BaseResult baseResult = articleService.ArticleAdd(articles, sortCheckboxes,labelCheckboxes);
        if (baseResult.getStatus() == 500) {
            model.addAttribute("article", articles);
            //model.addAttribute("sortCheckboxes",sortCheckboxes);
            //model.addAttribute("labelCheckboxes",labelCheckboxes);
            //*得到所有分类标签*//
            model.addAttribute("sortsList", sortService.list());
            model.addAttribute("labelsList", labelService.list());
            model.addAttribute("messages",baseResult.getMessage());
            return "admin/article/article_add";
        }
        redirectAttributes.addFlashAttribute("messages",baseResult.getMessage());
        return "redirect:/admin/articleList";
    }

    @RequestMapping(value = "/articleUpdate", method = RequestMethod.GET)
    private String articleUpdate(Model model, @RequestParam String articleId) {
        List<Sorts> sortsList = sortService.list();
        List<Labels> labelsList = labelService.list();
        model.addAttribute("sortsList", sortsList);
        model.addAttribute("labelsList", labelService.list());
        Articles article = articleService.selectById(articleId);
        model.addAttribute("article", article);
        return "admin/article/article_update";
    }

    @RequestMapping(value = "/articleUpdate", method = RequestMethod.POST)
    private String articleUpdate(RedirectAttributes redirectAttributes,Model model, Articles article, HttpServletRequest request, @RequestParam(required = false) String status) {
        /*判断是否启用博文*/
        if (status == null || status.length() == 0) {
            article.setArticleStatus(0);
        } else {
            article.setArticleStatus(1);
        }
        article.setArticleUpdateTime(new Date());
        /*博文内容机器mark格式单独获取*/
        article.setArticleContent(request.getParameter("editormd-html-textarea"));
        article.setArticleContentMd(request.getParameter("test-editormd-markdown-doc"));
        /*分类列表*/
        String[] sortCheckboxes = request.getParameterValues("sortCheckbox");
        /*标签列表*/
        String[] labelCheckboxes = request.getParameterValues("labelCheckbox");
        /*单独判断分类标签，不然会报空指针异常*/
        if (sortCheckboxes == null||labelCheckboxes==null) {
            model.addAttribute("article", article);
            model.addAttribute("sortsList", sortService.list());
            model.addAttribute("labelsList", labelService.list());
            model.addAttribute("messages","至少选择一个分类或标签");
            return "admin/article/article_update";
        }
        BaseResult baseResult = articleService.update(article, sortCheckboxes,labelCheckboxes);
        ;
        if (baseResult.getStatus() == 500) {

            model.addAttribute("article", article);
            model.addAttribute("messages",baseResult.getMessage());
            return "admin/article/article_update";
        }
        redirectAttributes.addFlashAttribute("messages", baseResult.getMessage());
        return "redirect:articleList";
    }

    @RequestMapping(value = "/articleDelete", method = RequestMethod.GET)
    private String articleDelete(RedirectAttributes redirectAttributes, @RequestParam String articleId) {
        BaseResult baseResult = articleService.delete(articleId);
        redirectAttributes.addFlashAttribute("messages", baseResult.getMessage());
        return "redirect:articleList";
    }

    @ResponseBody
    @RequestMapping(value = "/articleSave", method = RequestMethod.POST)
    private Map<String, Object> articleSave(@RequestBody Map<String, Object> params, Model model) {
        Articles articles = new Articles();
        articles.setArticleContent(params.get("htmlContent").toString());
        articles.setArticleContentMd(params.get("mdContent").toString());
        articles.setArticleTitle(params.get("title").toString());
        articles.setUserId(1l);
        //articles.setArticleLabel("aa");
        articles.setArticleStatus(1);
        articles.setArticleCreateTime(new Date());
        articles.setArticleUpdateTime(new Date());
        articleService.ArticleSave(articles);
        Map map = new HashMap<String, Object>();
        map.put("success", 0);
        map.put("message", "发布成功！");
        map.put("id", "1");
        return map;
    }

/*    @RequestMapping(value = "/articleTo", method = RequestMethod.GET)
    private String articleTo(Model model,HttpServletRequest request) {
        Example example = new Example(Articles.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("articleId", request.getParameter("id"));
        Articles articles = articlesMapper.selectOneByExample(example);
        model.addAttribute("html",articles.getArticleContent());
        return "admin/article/article_view";
    }*/

/*    @RequestMapping(value = "/articleAdd", method = RequestMethod.POST)
    private String articleAdd(@RequestBody Map<String, Object> params, Model model) {
        System.out.println(params.get("title"));
        System.out.println(params.get("htmlContent"));
        model.addAttribute("html",params.get("htmlContent"));
        return "redirect:admin/article/article_view";
    }*/
}
