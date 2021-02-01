package com.sunyue.myblog.service.serviceImpl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.sunyue.myblog.commons.EmptyUtil;
import com.sunyue.myblog.commons.RegexpUtils;
import com.sunyue.myblog.dao.ArticleLabelMapper;
import com.sunyue.myblog.dao.LabelsMapper;
import com.sunyue.myblog.entity.ArticleLabel;
import com.sunyue.myblog.entity.BaseResult;
import com.sunyue.myblog.entity.Labels;
import com.sunyue.myblog.service.LabelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

@Service
public class LabelServiceImpl implements LabelService {
    @Autowired
    private LabelsMapper labelsMapper;
    @Autowired
    private ArticleLabelMapper articleLabelMapper;

    private void deleteArticleLabel(String labelId) {
        Example example = new Example(ArticleLabel.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("labelId", labelId);
        articleLabelMapper.deleteByExample(example);
    }

    @Override
    public List<Labels> list() {
        return labelsMapper.selectAll();
    }

    @Override
    public BaseResult add(Labels labels) {
        if (RegexpUtils.checkNull(labels.getLabelName())) {
            return BaseResult.fail("标签名称为空！");
        }
        Labels label = selectByLabelName(labels.getLabelName());
        if (EmptyUtil.isEmpty(label)) {
            labelsMapper.insert(labels);
            return BaseResult.success("添加标签成功！");
        } else {
            return BaseResult.fail("该标签已被添加，请勿重新添加！");
        }
    }

    @Override
    public PageInfo<Labels> page(int pageNum, int pageSize, Labels labels) {
        PageHelper.startPage(pageNum, pageSize);
        // 设置分页查询条件
        Example example = new Example(Labels.class);
        Example.Criteria criteria = example.createCriteria();
        if (!EmptyUtil.isEmpty(labels.getLabelName())) {
            criteria.andLike("labelName", "%" + labels.getLabelName() + "%");
        }
        if (!EmptyUtil.isEmpty(labels.getLabelStatus())) {
            criteria.andEqualTo("labelStatus", labels.getLabelStatus());
        }
        List<Labels> labels1 = labelsMapper.selectByExample(example);
        if (EmptyUtil.isEmpty(labels1)) {
            return new PageInfo<>();
        }
        return new PageInfo<>(labels1);
    }

    @Override
    public Labels selectById(String labelId) {
        Example example = new Example(Labels.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("labelId", labelId);
        return labelsMapper.selectOneByExample(example);
    }

    @Override
    public Labels selectByLabelName(String labelName) {
        Example example = new Example(Labels.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("labelName", labelName);
        return labelsMapper.selectOneByExample(example);
    }

    @Override
    public BaseResult update(Labels labels) {
        if (RegexpUtils.checkNull(labels.getLabelName())) {
            return BaseResult.fail("标签名称不能为空！");
        } else {
            int i = labelsMapper.updateByPrimaryKeySelective(labels);
            if (i <= 0) {
                return BaseResult.fail("标签更新失败！");
            } else {
                return BaseResult.success("标签更新成功！");
            }
        }
    }

    @Override
    public BaseResult delete(String labelId) {
        deleteArticleLabel(labelId);
        int i = labelsMapper.deleteByPrimaryKey(labelId);
        if (i <= 0) {
            return BaseResult.fail("标签删除失败！");
        }
        return BaseResult.success("标签删除成功！");
    }

    @Override
    public int count() {
        return labelsMapper.selectCount(new Labels());
    }
}
