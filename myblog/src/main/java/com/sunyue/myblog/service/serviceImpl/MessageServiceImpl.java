package com.sunyue.myblog.service.serviceImpl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.sunyue.myblog.commons.EmptyUtil;
import com.sunyue.myblog.commons.RegexpUtils;
import com.sunyue.myblog.dao.MessagesMapper;
import com.sunyue.myblog.entity.Articles;
import com.sunyue.myblog.entity.BaseResult;
import com.sunyue.myblog.entity.Messages;
import com.sunyue.myblog.service.MessageService;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class MessageServiceImpl implements MessageService {
    @Autowired
    private MessagesMapper messagesMapper;

    @Override
    public List<Messages> list() {
        Example example = new Example(Messages.class);
        //排序
        example.orderBy("messageId").desc();
        return messagesMapper.selectByExample(example);
    }

    @Override
    public BaseResult add(Messages messages) {
        if (RegexpUtils.checkNull(messages.getMessageContent())) {
            return BaseResult.fail("留言信息为空！");
        }
        messages.setMessageCreateTime(new Date());
        ;
        messages.setParentId(null);
        messagesMapper.insert(messages);
        return BaseResult.success("添加留言成功！");
    }

    @Override
    public PageInfo<Messages> page(int pageNum, int pageSize, Messages messages, String userName) {
        PageHelper.startPage(pageNum, pageSize);
        // 设置分页查询条件
        Example example = new Example(Messages.class);
        Example.Criteria criteria = example.createCriteria();
        if (!EmptyUtil.isEmpty(userName)) {
            if (!EmptyUtil.isEmpty(messages.getUserId())) {
                criteria.andEqualTo("userId", messages.getUserId());
            } else {
                return new PageInfo<>();
            }
        }

        if (!EmptyUtil.isEmpty(messages.getMessageStatus())) {
            criteria.andEqualTo("messageStatus", messages.getMessageStatus());
        }
        List<Messages> messages1 = messagesMapper.selectByExample(example);
        if (EmptyUtil.isEmpty(messages1)) {
            return new PageInfo<>();
        }

        return new PageInfo<>(messages1);
    }

    @Override
    public Messages selectById(String messageId) {
        Example example = new Example(Messages.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("messageId", messageId);
        return messagesMapper.selectOneByExample(example);
    }

    @Override
    public BaseResult update(Messages messages) {
        if (RegexpUtils.checkNull(messages.getMessageContent())) {
            return BaseResult.fail("留言信息不能为空！");
        } else {
            /*这个方法主键得有值*/
            int i = messagesMapper.updateByPrimaryKeySelective(messages);
            if (i <= 0) {
                return BaseResult.fail("评留言信息更新失败！");
            } else {
                return BaseResult.success("留言信息更新成功！");
            }
        }
    }

    @Override
    public BaseResult delete(String messageId) {
        int i = messagesMapper.deleteByPrimaryKey(messageId);
        if (i <= 0) {
            return BaseResult.fail("留言信息删除失败！");
        }
        return BaseResult.success("留言信息删除成功！");
    }

    @Override
    public List<Messages> selectMessagesByRow(int num) {
        /*RowBounds rowBonds = new RowBounds(0, num);
        return messagesMapper.selectByRowBounds(new Messages(), rowBonds);*/
        Example example = new Example(Messages.class);
        example.orderBy("messageId").desc();
        List<Messages> messagesList = messagesMapper.selectByExample(example);
        List<Messages> messagesArrayList = new ArrayList<>();
        for (int i = 0; i < num; i++) {
            if (messagesList.size() > i) {
                messagesArrayList.add(messagesList.get(i));
            }
        }
        return messagesList;
    }

    @Override
    public int count() {
        return messagesMapper.selectCount(new Messages());
    }
}
