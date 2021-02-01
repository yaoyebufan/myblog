package com.sunyue.myblog.service.serviceImpl;

import com.sunyue.myblog.dao.ArticleSortMapper;
import com.sunyue.myblog.entity.ArticleSort;
import com.sunyue.myblog.service.ArticleSortService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

@Service
public class ArticleSortServiceImpl implements ArticleSortService {
    @Autowired
    private ArticleSortMapper articleSortMapper;

    @Override
    public List<ArticleSort> list() {
        return articleSortMapper.selectAll();
    }

    @Override
    public List<ArticleSort> selectByArticleId(String articleId) {
        Example example = new Example(ArticleSort.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("articleId", articleId);
        return articleSortMapper.selectByExample(example);
    }

    @Override
    public List<ArticleSort> selectBySortId(String sortId) {
        Example example = new Example(ArticleSort.class);
        Example.Criteria criteria = example.createCriteria();
        example.orderBy("articleId").desc();
        criteria.andEqualTo("sortId", sortId);
        return articleSortMapper.selectByExample(example);
    }
}
