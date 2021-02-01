package com.sunyue.myblog.service.serviceImpl;

import com.sunyue.myblog.dao.ArticleLabelMapper;
import com.sunyue.myblog.entity.ArticleLabel;
import com.sunyue.myblog.entity.ArticleSort;
import com.sunyue.myblog.entity.Messages;
import com.sunyue.myblog.service.ArticleLabelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

@Service
public class ArticleLabelServiceImpl implements ArticleLabelService {
    @Autowired
    private ArticleLabelMapper articleLabelMapper;

    @Override
    public List<ArticleLabel> list() {
        return articleLabelMapper.selectAll();
    }

    @Override
    public List<ArticleLabel> selectByArticleId(String articleId) {
        Example example = new Example(ArticleLabel.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("articleId", articleId);
        return articleLabelMapper.selectByExample(example);
    }

    @Override
    public List<ArticleLabel> selectByLabelId(String labelId) {
        Example example = new Example(ArticleLabel.class);
        //排序
        example.orderBy("articleId").desc();
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("labelId", labelId);
        return articleLabelMapper.selectByExample(example);
    }
}
