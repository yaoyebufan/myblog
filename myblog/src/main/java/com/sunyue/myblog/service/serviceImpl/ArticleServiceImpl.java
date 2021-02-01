package com.sunyue.myblog.service.serviceImpl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.sunyue.myblog.commons.EmptyUtil;
import com.sunyue.myblog.commons.ListRemove;
import com.sunyue.myblog.commons.RegexpUtils;
import com.sunyue.myblog.dao.*;
import com.sunyue.myblog.entity.*;
import com.sunyue.myblog.service.ArticleService;
import com.sunyue.myblog.service.LabelService;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@Service
public class ArticleServiceImpl implements ArticleService {
    @Autowired
    private ArticlesMapper articlesMapper;
    @Autowired
    private ArticleSortMapper articleSortMapper;
    @Autowired
    private SortsMapper sortsMapper;
    @Autowired
    private ArticleLabelMapper articleLabelMapper;
    @Autowired
    private LabelsMapper labelsMapper;
    @Autowired
    private CommentsMapper commentsMapper;
    // 获取配置文件中图片的路径
    @Value("${cbs.imagesPath}")
    private String mImagesPath;

    private void deleteComment(String articleId) {
        Example example = new Example(Comments.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("articleId", articleId);
        commentsMapper.deleteByExample(example);
    }

    private void deleteSort(String articleId) {
        Example example = new Example(ArticleSort.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("articleId", articleId);
        int j = articleSortMapper.deleteByExample(example);
    }

    private void deleteLabel(String articleId) {
        Example example = new Example(ArticleLabel.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("articleId", articleId);
        int j = articleLabelMapper.deleteByExample(example);
    }

    private void insertSort(Articles articles, String sortCheckboxes) {
        ArticleSort articleSort = new ArticleSort();
        articleSort.setArticleId(articles.getArticleId());
        articleSort.setSortId(Long.parseLong(sortCheckboxes));
        articleSortMapper.insert(articleSort);
    }

    private void insertLabel(Articles articles, String labelCheckboxes) {
        ArticleLabel articleLabel = new ArticleLabel();
        articleLabel.setArticleId(articles.getArticleId());
        articleLabel.setLabelId(Long.parseLong(labelCheckboxes));
        articleLabelMapper.insert(articleLabel);
    }

    private BaseResult comRegexp(Articles articles) {
        if (RegexpUtils.checkNull(articles.getArticleContentMd().trim())) {
            return BaseResult.fail("博文内容不能为空！");
        }
        if (RegexpUtils.checkNull(articles.getUserId().toString())) {
            return BaseResult.fail("用户不能为空！");
        }
        if (RegexpUtils.checkNull(articles.getArticleTitle())) {
            return BaseResult.fail("博文标题不能为空！");
        }
        if (articles.getArticleStatus() == null) {
            return BaseResult.fail("博文状态不能为空！");
        }
        return BaseResult.success();
    }

    private BaseResult regexp(Articles articles, String[] sortCheckboxes, String[] labelCheckboxes) {
        if (comRegexp(articles).getStatus() == 200) {
            if (sortCheckboxes.length <= 0 || labelCheckboxes.length <= 0) {
                return BaseResult.fail("至少选择一个分类或标签");
            }
            return BaseResult.success();
        }
        return comRegexp(articles);
    }

    private Articles selectOneArticle(Articles articles) {
        Example example = new Example(Articles.class);
        Example.Criteria criteria1 = example.createCriteria();
        /*通过创标题查找*/
        criteria1.andEqualTo("articleTitle", articles.getArticleTitle());
        return articlesMapper.selectOneByExample(example);
    }

    @Override
    public List<Articles> selectByArticleTitle(String articleTitle) {
        Example example = new Example(Articles.class);
        Example.Criteria criteria = example.createCriteria();
        if (!EmptyUtil.isEmpty(articleTitle)) {
            criteria.andLike("articleTitle", "%" + articleTitle + "%");
        }
        List<Articles> articles = articlesMapper.selectByExample(example);
        if (EmptyUtil.isEmpty(articles)) {
            return null;
        }
        return articles;
    }

    @Override
    public int count() {
        return articlesMapper.selectCount(new Articles());
    }

    @Override
    public BaseResult ArticleAdd(Articles articles, String[] sortCheckboxes, String[] labelCheckboxes) {
        BaseResult regexp = regexp(articles, sortCheckboxes, labelCheckboxes);
        if (regexp.getStatus() == 200) {
            /*判断是否标题相同*/
            Articles articles1 = selectOneArticle(articles);
            if (articles1 == null) {
                articlesMapper.insert(articles);
                /*查询获取文章id,因为文章id为空，必须添加过后自增，才能知道id*/
                Articles articles2 = selectOneArticle(articles);
                for (String sortCheckbox : sortCheckboxes) {
                    insertSort(articles2, sortCheckbox);
                }
                for (String labelCheckbox : labelCheckboxes) {
                    insertLabel(articles2, labelCheckbox);
                }
                return BaseResult.success("添加博文成功，继续加油哟！");
            }
            return BaseResult.fail("已经有相同的标题了换一个好吗！");
        }
        return regexp;
    }

    @Override
    public BaseResult ArticleSave(Articles articles) {
        articlesMapper.insert(articles);
        return null;
    }

    @Override
    public PageInfo<Articles> page(int pageNum, int pageSize, Articles articles) {
        PageHelper.startPage(pageNum, pageSize);
        // 设置分页查询条件
        Example example = new Example(Articles.class);
        //排序
        example.setOrderByClause("article_id DESC");
        Example.Criteria criteria = example.createCriteria();
        if (!EmptyUtil.isEmpty(articles.getArticleTitle())) {
            criteria.andLike("articleTitle", "%" + articles.getArticleTitle() + "%");
        }
        if (!EmptyUtil.isEmpty(articles.getArticleStatus())) {
            criteria.andEqualTo("articleStatus", articles.getArticleStatus());
        }
        List<Articles> articles1 = articlesMapper.selectByExample(example);
        if (EmptyUtil.isEmpty(articles1)) {
            return new PageInfo<>();
        }
        return new PageInfo<>(articles1);
    }

    @Override
    public Articles selectById(String articleId) {
        Example example = new Example(Articles.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("articleId", articleId);
        return articlesMapper.selectOneByExample(example);
    }

    @Override
    public BaseResult update(Articles articles, String[] sortCheckboxes, String[] labelCheckboxes) {
        BaseResult regexp = regexp(articles, sortCheckboxes, labelCheckboxes);
        if (regexp.getStatus() == 500) {
            return regexp;
        } else {
            deleteSort(articles.getArticleId().toString());
            deleteLabel(articles.getArticleId().toString());
            int i = articlesMapper.updateByPrimaryKeySelective(articles);
            for (String sortCheckbox : sortCheckboxes) {
                insertSort(articles, sortCheckbox);
            }
            for (String labelCheckbox : labelCheckboxes) {
                insertLabel(articles, labelCheckbox);
            }
            if (i <= 0) {
                return BaseResult.fail("更新失败");
            } else {
                return BaseResult.success("更新成功");
            }
        }
    }

    @Override
    public BaseResult delete(String articleId) {
        deleteSort(articleId);
        deleteLabel(articleId);
        deleteComment(articleId);
        Articles articles = selectById(articleId);
        //图片正则获取url
        List<String> strings = RegexpUtils.checkImgUrl(articles.getArticleContent());
        if (strings != null) {
            for (String string : strings) {
                String url = mImagesPath + string;
                //删除插图;
                File file = new File(url);
                file.delete();
            }
        }
        int i = articlesMapper.deleteByPrimaryKey(articleId);
        if (i <= 0) {
            return BaseResult.fail("删除失败");
        }
        return BaseResult.success("删除成功");
    }

    @Override
    public List<Labels> selectAllLabel() {
        return ListRemove.removeLabel(labelsMapper.selectAll());
    }

    @Override
    public List<Articles> selectArticleByLabel(String label) {
        List<Articles> articlesList = new ArrayList<>();
        Example example1 = new Example(Labels.class);
        Example.Criteria criteria1 = example1.createCriteria();
        criteria1.andEqualTo("labelName", label);
        List<Labels> labelsList = labelsMapper.selectByExample(example1);
        if (!EmptyUtil.isEmpty(labelsList)) {
            for (Labels labels : labelsList) {
                Example example2 = new Example(ArticleLabel.class);
                Example.Criteria criteria2 = example2.createCriteria();
                criteria2.andEqualTo("labelId", labels.getLabelId());
                List<ArticleLabel> articleLabelList = articleLabelMapper.selectByExample(example2);
                if (!EmptyUtil.isEmpty(articleLabelList)) {
                    for (ArticleLabel articleLabel : articleLabelList) {
                        Articles articles = selectById(articleLabel.getArticleId().toString());
                        articlesList.add(articles);
                    }
                }
            }
        }
        return articlesList;
    }

    @Override
    public List<Sorts> selectAllSort() {
        return ListRemove.removeSort(sortsMapper.selectAll());
    }

    @Override
    public List<Articles> selectArticleBySort(String sort) {
        List<Articles> articlesList = new ArrayList<>();
        Example example1 = new Example(Sorts.class);
        Example.Criteria criteria1 = example1.createCriteria();
        criteria1.andEqualTo("sortName", sort);
        List<Sorts> sortsList = sortsMapper.selectByExample(example1);
        if (!EmptyUtil.isEmpty(sortsList)) {
            for (Sorts sorts : sortsList) {
                Example example2 = new Example(ArticleSort.class);
                Example.Criteria criteria2 = example2.createCriteria();
                criteria2.andEqualTo("sortId", sorts.getSortId());
                List<ArticleSort> articleSortsList = articleSortMapper.selectByExample(example2);
                if (!EmptyUtil.isEmpty(articleSortsList)) {
                    for (ArticleSort articleSort : articleSortsList) {
                        Articles articles = selectById(articleSort.getArticleId().toString());
                        articlesList.add(articles);
                    }
                }
            }
        }
        return articlesList;
    }

    @Override
    public List<Articles> selectArticleByRow(int num) {
       /* RowBounds rowBonds = new RowBounds(0, num);
        return articlesMapper.selectByRowBounds(new Articles(), rowBonds);*/
        Example example = new Example(Articles.class);
        //排序
        example.orderBy("articleId").desc();
        List<Articles> articlesList = articlesMapper.selectByExample(example);
        List<Articles> articlesArrayList = new ArrayList<>();
        for (int i = 0; i < num; i++) {
            if (articlesList.size() > i) {
                articlesArrayList.add(articlesList.get(i));
            }
        }
        return articlesArrayList;
    }

    @Override
    public List<String> selectAllDate() {
        Example example = new Example(Articles.class);
        //排序
        example.orderBy("articleCreateTime").desc();
        List<Articles> articlesList = articlesMapper.selectByExample(example);
        List<String> dateTimeList = new ArrayList<>();
        for (Articles articles : articlesList) {
            Date articleCreateTime = articles.getArticleCreateTime();
            String nowTime = new SimpleDateFormat("yyyy-MM").format(articleCreateTime);//将时间格式转换成符合Timestamp要求的格式
            dateTimeList.add(nowTime);
        }
        ListRemove.removeDuplicate(dateTimeList);
        return dateTimeList;
    }

    @Override
    public List<Articles> selectByDate(String dateTime) {
        if (dateTime == null || dateTime.equals("")) {
            Example example = new Example(Articles.class);
            example.orderBy("articleId").desc();
            return articlesMapper.selectByExample(example);
        }
        Example example = new Example(Articles.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andGreaterThanOrEqualTo("articleCreateTime", dateTime);
        String a = dateTime.substring(5, 7);
        int b = Integer.parseInt(a) + 1;
        String endTime;
        if (b < 10) {
            endTime = dateTime.substring(0, 4) + "-0" + b;
        } else {
            endTime = dateTime.substring(0, 4) + "-" + b;
        }
        criteria.andLessThanOrEqualTo("articleCreateTime", endTime);
        example.orderBy("articleId").desc();
        return articlesMapper.selectByExample(example);
    }
}
