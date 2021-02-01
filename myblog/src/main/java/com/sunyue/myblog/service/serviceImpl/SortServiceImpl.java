package com.sunyue.myblog.service.serviceImpl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.sunyue.myblog.commons.EmptyUtil;
import com.sunyue.myblog.commons.RegexpUtils;
import com.sunyue.myblog.dao.ArticleSortMapper;
import com.sunyue.myblog.dao.SortsMapper;
import com.sunyue.myblog.entity.ArticleSort;
import com.sunyue.myblog.entity.BaseResult;
import com.sunyue.myblog.entity.Sorts;
import com.sunyue.myblog.service.SortService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

@Service
public class SortServiceImpl implements SortService {
    @Autowired
    private SortsMapper sortsMapper;
    @Autowired
    private ArticleSortMapper articleSortMapper;

    private void deleteArticleSort(String sortId) {
        Example example = new Example(ArticleSort.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("sortId", sortId);
        articleSortMapper.deleteByExample(example);
    }

    @Override
    public List<Sorts> list() {
        return sortsMapper.selectAll();
    }

    @Override
    public BaseResult add(Sorts sorts) {
        if (RegexpUtils.checkNull(sorts.getSortName())) {
            return BaseResult.fail("分类名称为空！");
        }
        Sorts sort = selectBySortName(sorts.getSortName());
        if (EmptyUtil.isEmpty(sort)) {
            sortsMapper.insert(sorts);
            return BaseResult.success("添加分类成功！");
        } else {
            return BaseResult.fail("该分类已被添加，请勿重新添加！");
        }
    }

    @Override
    public PageInfo<Sorts> page(int pageNum, int pageSize, Sorts sorts) {
        PageHelper.startPage(pageNum, pageSize);
        // 设置分页查询条件
        Example example = new Example(Sorts.class);
        Example.Criteria criteria = example.createCriteria();
        if (!EmptyUtil.isEmpty(sorts.getSortName())) {
            criteria.andLike("sortName", "%" + sorts.getSortName() + "%");
        }
        if (!EmptyUtil.isEmpty(sorts.getSortStatus())) {
            criteria.andEqualTo("sortStatus", sorts.getSortStatus());
        }
        List<Sorts> sorts1 = sortsMapper.selectByExample(example);
        if (EmptyUtil.isEmpty(sorts1)) {
            return new PageInfo<>();
        }
        return new PageInfo<>(sorts1);
    }

    @Override
    public Sorts selectById(String sortId) {
        Example example = new Example(Sorts.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("sortId", sortId);
        return sortsMapper.selectOneByExample(example);
    }

    @Override
    public Sorts selectBySortName(String sortName) {
        Example example = new Example(Sorts.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("sortName", sortName);
        return sortsMapper.selectOneByExample(example);
    }


    @Override
    public BaseResult update(Sorts sorts) {
        if (RegexpUtils.checkNull(sorts.getSortName())) {
            return BaseResult.fail("分类名称不能为空！");
        } else {
            int i = sortsMapper.updateByPrimaryKeySelective(sorts);
            if (i <= 0) {
                return BaseResult.fail("分类更新失败！");
            } else {
                return BaseResult.success("分类更新成功！");
            }
        }
    }

    @Override
    public BaseResult delete(String sortId) {
        deleteArticleSort(sortId);
        int i = sortsMapper.deleteByPrimaryKey(sortId);
        if (i <= 0) {
            return BaseResult.fail("分类删除失败！");
        }
        return BaseResult.success("分类删除成功！");
    }
}
