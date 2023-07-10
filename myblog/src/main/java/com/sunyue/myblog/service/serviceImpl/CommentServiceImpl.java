package com.sunyue.myblog.service.serviceImpl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.sunyue.myblog.commons.EmptyUtil;
import com.sunyue.myblog.commons.RegexpUtils;
import com.sunyue.myblog.dao.CommentsMapper;
import com.sunyue.myblog.entity.BaseResult;
import com.sunyue.myblog.entity.Comments;
import com.sunyue.myblog.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.Date;
import java.util.List;

@Service
public class CommentServiceImpl implements CommentService {
    @Autowired
    private CommentsMapper commentsMapper;


    @Override
    public List<Comments> list() {
        return commentsMapper.selectAll();
    }

    @Override
    public BaseResult add(Comments comment) {
        if (RegexpUtils.checkNull(comment.getCommentContent())) {
            return BaseResult.fail("评论信息为空！");
        }
        comment.setCommentCreateTime(new Date());
        comment.setParentId(null);
        commentsMapper.insert(comment);
        return BaseResult.success("发表评论成功！");

    }

    @Override
    public PageInfo<Comments> page(int pageNum, int pageSize, Comments comment, String userName) {
        PageHelper.startPage(pageNum, pageSize);
        // 设置分页查询条件
        Example example = new Example(Comments.class);
        Example.Criteria criteria = example.createCriteria();
        if (!EmptyUtil.isEmpty(userName)) {
            if (!EmptyUtil.isEmpty(comment.getUserId())) {
                criteria.andEqualTo("userId", comment.getUserId());
            }else {
                return new PageInfo<>();
            }
        }
        if (!EmptyUtil.isEmpty(comment.getCommentStatus())) {
            criteria.andEqualTo("commentStatus", comment.getCommentStatus());
        }
        List<Comments> comments1 = commentsMapper.selectByExample(example);
        if (EmptyUtil.isEmpty(comments1)) {
            return new PageInfo<>();
        }

        return new PageInfo<>(comments1);
    }

    @Override
    public Comments selectById(String commentId) {
        Example example = new Example(Comments.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("commentId", commentId);
        return commentsMapper.selectOneByExample(example);
    }

    @Override
    public List<Comments> selectByArticleId(String articleId) {
        Example example = new Example(Comments.class);
        Example.Criteria criteria = example.createCriteria();
        example.orderBy("commentId").desc();
        criteria.andEqualTo("articleId", articleId);
        return commentsMapper.selectByExample(example);
    }

    @Override
    public BaseResult update(Comments comment) {
        if (RegexpUtils.checkNull(comment.getCommentContent())) {
            return BaseResult.fail("评论信息不能为空！");
        } else {
            /*这个方法主键得有值*/
            int i = commentsMapper.updateByPrimaryKeySelective(comment);
            if (i <= 0) {
                return BaseResult.fail("评论信息更新失败！");
            } else {
                return BaseResult.success("评论信息更新成功！");
            }
        }
    }

    @Override
    public BaseResult delete(String commentId) {
        int i = commentsMapper.deleteByPrimaryKey(commentId);
        if (i <= 0) {
            return BaseResult.fail("评论信息删除失败！");
        }
        return BaseResult.success("评论信息删除成功！");
    }
}
