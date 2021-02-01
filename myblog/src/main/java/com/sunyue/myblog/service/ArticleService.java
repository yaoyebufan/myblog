package com.sunyue.myblog.service;

import com.github.pagehelper.PageInfo;
import com.sunyue.myblog.entity.Articles;
import com.sunyue.myblog.entity.BaseResult;
import com.sunyue.myblog.entity.Labels;
import com.sunyue.myblog.entity.Sorts;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ArticleService {
    /*根据标题查询所有文章*/
    List<Articles> selectByArticleTitle(String articleTitle);

    /*查询所有文章数量*/
    int count();

    /*添加博文（添加博文和分类的连接表）*/
    BaseResult ArticleAdd(Articles articles, String[] sortCheckboxes, String[] labelCheckboxes);

    /*保存博文（草稿箱）*/
    BaseResult ArticleSave(Articles articles);

    /*分页获取数据*/
    PageInfo<Articles> page(int pageNum, int pageSize, Articles articles);

    /*通过adminId获取一个账号信息*/
    Articles selectById(String articleId);

    /*更新博文*/
    BaseResult update(Articles articles, String[] sortCheckboxes, String[] labelCheckboxes);

    /*删除博文*/
    BaseResult delete(String articleId);

    /*查询所有标签*/
    List<Labels> selectAllLabel();

    /*通过标签查询文章*/
    List<Articles> selectArticleByLabel(String label);

    /*查询所有分类*/
    List<Sorts> selectAllSort();

    /*通过分类查询文章*/
    List<Articles> selectArticleBySort(String sort);

    /*查询指定数量的文章列表*/
    List<Articles> selectArticleByRow(int num);

    /*获取文章中所有年月*/
    List<String> selectAllDate();

    /*根据年月查询所有文章*/
    List<Articles> selectByDate(String dateTime);
}
