package com.sunyue.myblog;

import com.alibaba.druid.util.StringUtils;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.sunyue.myblog.commons.ListRemove;
import com.sunyue.myblog.dao.AdminsMapper;
import com.sunyue.myblog.dao.ArticleSortMapper;
import com.sunyue.myblog.dao.ArticlesMapper;
import com.sunyue.myblog.dao.UsersMapper;
import com.sunyue.myblog.entity.*;
import com.sunyue.myblog.service.ArticleService;
import org.apache.ibatis.session.RowBounds;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = MyblogApplication.class)
@Transactional
@Rollback
public class JDBC {
    @Autowired(required = true)
    private UsersMapper UsersMapper;
    @Autowired
    private AdminsMapper adminsMapper;
    @Autowired
    private ArticlesMapper articlesMapper;
    @Autowired
    private ArticleService articleService;
    @Autowired
    private ArticleSortMapper articleSortMapper;
    @Test
    public void St(){
        List<Articles> articlesList = articleService.selectByDate("2020-07");
        System.out.println(articlesList.size());
        String a = "2020-07";
        String substring=a.substring(5,7);
        System.out.println(Integer.parseInt(substring) + 1);
        System.out.println(substring);
        String c = a.substring(0,4);
        System.out.println(c);
    }
    @Test
    public void month(){
        List<Articles> articlesList = articlesMapper.selectAll();
        List<String> date = new ArrayList<>();
        for (Articles articles : articlesList) {
            Date articleCreateTime = articles.getArticleCreateTime();
            String nowTime = new SimpleDateFormat("yyyy-MM").format(articleCreateTime);//将时间格式转换成符合Timestamp要求的格式
            date.add(nowTime);
        }
        ListRemove.removeDuplicate(date);
        for (String s : date) {
            System.out.println(s);
        }
    }
    @Test
    public void selectDate() throws ParseException {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM");//注意月份是MM
        Date date1 = simpleDateFormat.parse("2020-08-01");
        Date date2 = simpleDateFormat.parse("2020-08-31");
        System.out.println(date1);   //Mon Sep 02 00:00:00 CST 2019
        System.out.println(simpleDateFormat.format(date1));  //2019-09-02

        Example example = new Example(Articles.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andGreaterThanOrEqualTo("articleCreateTime", "2020-07");
        criteria.andLessThanOrEqualTo("articleCreateTime", "2020-08");
        List<Articles> articlesList = articlesMapper.selectByExample(example);
        for (Articles articles : articlesList) {
            System.out.println(articles.getArticleTitle());
        }
        //example.setOrderByClause("create_time desc");


    }
    @Test
    public void row(){
        RowBounds rowBonds = new RowBounds(0, 0);
        List<Articles> articlesList = articlesMapper.selectByRowBounds(new Articles(), rowBonds);
        for (Articles articles : articlesList) {
            System.out.println(articles.getArticleId());
        }
    }
    @Test
    public void Arc(){
        PageInfo<Articles> page = articleService.page(1, 4, new Articles());
        for (Articles articles : page.getList()) {
            System.out.println(articles.getArticleTitle());
        }
    }
    @Test
    public void seAllLabel(){
    /*    List<String > allLabel = articleService.selectAllLabel();
        for (String label : allLabel) {
            System.out.println(label);
        }*/
    }
    @Test
    public void label(){
        List<Articles> articlesList = articleService.selectArticleByLabel("测试");
        for (Articles articles : articlesList) {
            System.out.println(articles.getArticleTitle());
        }
    }
    @Test
    public void seAllSort(){
        List<Sorts> sortsList = articleService.selectAllSort();
        for (Sorts sorts : sortsList) {
            System.out.println(sorts.getSortName());
        }
    }

    @Test
    public void Sort(){
        List<Articles> articlesList = articleService.selectArticleBySort("分类3");
        for (Articles articles : articlesList) {
            System.out.println(articles.getArticleTitle());
        }
    }


    /*可以查询对象中一些属性设置，得到一组数据*/

    @Test
    public void select(){
        ArticleSort articleSort = new ArticleSort();
        articleSort.setSortId(2L);
        List<ArticleSort> select = articleSortMapper.select(articleSort);
        for (ArticleSort sort : select) {
            System.out.println(sort.getSortId());
        }
    }


    @Test
    public void aVoid(){
        Articles articles = new Articles();
        articles.setArticleId(11L);
        Example example = new Example(Articles.class);
        Example.Criteria criteria = example.createCriteria();
        /*通过创建时间查找，不过还是不保险*/
        criteria.andEqualTo("articleId", articles.getArticleId());
        Articles articles1 = articlesMapper.selectOneByExample(example);
        System.out.println(articles1);
    }
    @Test
    public void user(){
        List<Users> syUsers = UsersMapper.selectAll();
        for (Users user : syUsers) {
            System.out.println(user.getUserEmail());
        }
    }
    @Test
    public void login(){
        Example example = new Example(Users.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("userEmail","1");
        Users users = UsersMapper.selectOneByExample(example);
        if(users!=null){
            criteria.andEqualTo("userName","孙跃");
            Users users1 = UsersMapper.selectOneByExample(example);
            if (users1==null){
                System.out.println("null");
            }
            else {
                System.out.println(users1.getUserCreateTime());
            }
        }
        else {
            System.out.println("n");
        }
    }
    @Test
    public void testPage() {
        // PageHelper 使用非常简单，只需要设置页码和每页显示笔数即可
        PageHelper.startPage(1, 2);
        // 设置分页查询条件
        Example example = new Example(Admins.class);
        PageInfo<Admins> pageInfo = new PageInfo<>(adminsMapper.selectByExample(example));
        System.out.println(pageInfo.getPages());
        System.out.println(pageInfo.getTotal());
        System.out.println(pageInfo.getPageNum());
        System.out.println(pageInfo.getPageSize());
        // 获取查询结果
        List<Admins> Admin = pageInfo.getList();
        for (Admins admins : Admin) {
            System.out.println(admins.getAdminName());
        }
    }
}
