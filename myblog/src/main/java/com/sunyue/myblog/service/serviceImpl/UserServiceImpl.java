package com.sunyue.myblog.service.serviceImpl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.sunyue.myblog.commons.EmptyUtil;
import com.sunyue.myblog.commons.RegexpUtils;
import com.sunyue.myblog.dao.CommentsMapper;
import com.sunyue.myblog.dao.MessagesMapper;
import com.sunyue.myblog.dao.UsersMapper;
import com.sunyue.myblog.entity.BaseResult;
import com.sunyue.myblog.entity.Comments;
import com.sunyue.myblog.entity.Messages;
import com.sunyue.myblog.entity.Users;
import com.sunyue.myblog.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UsersMapper usersMapper;
    @Autowired
    private MessagesMapper messagesMapper;
    @Autowired
    private CommentsMapper commentsMapper;
    // 获取配置文件中图片的路径
    @Value("${cbs.imagesPath}")
    private String mImagesPath;

    private void deleteMessage(String userId) {
        Example example = new Example(Messages.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("userId", userId);
        messagesMapper.deleteByExample(example);
    }

    private void deleteComment(String userId) {
        Example example = new Example(Comments.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("userId", userId);
        int j = commentsMapper.deleteByExample(example);
    }

    private BaseResult regexp(Users users) {
        if (RegexpUtils.checkNull(users.getUserName())) {
            return BaseResult.fail("姓名不能为空");
        }
        if (RegexpUtils.checkNull(users.getUserSex())) {
            return BaseResult.fail("性别不能为空");
        }
        if (!RegexpUtils.checkNull(users.getUserSex())) {
            if (!(users.getUserSex().equals("男") || users.getUserSex().equals("女"))) {
                return BaseResult.fail("性别只能是男女哦");
            }
        }
        if (RegexpUtils.checkNull(users.getUserAge())) {
            return BaseResult.fail("年龄不能为空");
        }
        if (RegexpUtils.checkNUM(users.getUserAge())) {
            return BaseResult.fail("年龄不是数字");
        }
        if (!RegexpUtils.checkNUM(users.getUserAge())) {
            if (Integer.parseInt(users.getUserAge()) <= 0 || Integer.parseInt(users.getUserAge()) > 120) {
                return BaseResult.fail("你的年龄不合法哦");
            }
        }

        if (RegexpUtils.checkPassword(users.getUserPassword())) {
            return BaseResult.fail("密码格式错误");
        }
        if (RegexpUtils.checkEmail(users.getUserEmail())) {
            return BaseResult.fail("邮箱格式错误");
        }
        if (RegexpUtils.checkPhone(users.getUserPhone())) {
            return BaseResult.fail("电话号码格式错误");
        }
        return BaseResult.success();
    }

    @Override
    public Map login(String email, String pwd) {
        Map<String, Object> map = new HashMap<>();
        if (EmptyUtil.isEmpty(email) || EmptyUtil.isEmpty(pwd)) {
            BaseResult baseResult = BaseResult.fail("用户名或密码为空，请重新输入！");
            map.put("user", null);
            map.put("message", baseResult);
            return map;
        } else {
            Example example = new Example(Users.class);
            Example.Criteria criteria = example.createCriteria();
            criteria.andEqualTo("userEmail", email);
            Users users = usersMapper.selectOneByExample(example);
            if (EmptyUtil.isEmpty(users)) {
                BaseResult baseResult = BaseResult.fail("邮箱账号不存在！");
                map.put("user", null);
                map.put("message", baseResult);
                return map;
            } else {
                if (!users.getUserPassword().equals(pwd)) {
                    BaseResult baseResult = BaseResult.fail("密码错误，请重新输入");
                    map.put("user", null);
                    map.put("message", baseResult);
                    return map;
                } else {
                    BaseResult baseResult = BaseResult.success();
                    map.put("user", users);
                    map.put("message", baseResult);
                    return map;
                }
            }
        }
    }

    @Override
    public List<Users> list() {
        return usersMapper.selectAll();
    }

    @Override
    public BaseResult add(Users users) {
        BaseResult regexp = regexp(users);
        if (regexp.getStatus() == 500) {
            return regexp;
        }
        Example example = new Example(Users.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("userEmail", users.getUserEmail());
        Users user = usersMapper.selectOneByExample(example);
        if (EmptyUtil.isEmpty(user)) {
            usersMapper.insert(users);
            return BaseResult.success("恭喜您，注册成功！");

        } else {
            return BaseResult.fail("该电子邮箱已被注册");
        }
    }

    @Override
    public PageInfo<Users> page(int pageNum, int pageSize, Users users) {
        PageHelper.startPage(pageNum, pageSize);
        // 设置分页查询条件
        Example example = new Example(Users.class);
        Example.Criteria criteria = example.createCriteria();
        if (!EmptyUtil.isEmpty(users.getUserName())) {
            criteria.andLike("userName", "%" + users.getUserName() + "%");
        }
        if (!EmptyUtil.isEmpty(users.getUserStatus())) {
            criteria.andEqualTo("userStatus", users.getUserStatus());
        }
        List<Users> admins1 = usersMapper.selectByExample(example);
        if (EmptyUtil.isEmpty(admins1)) {
            return new PageInfo<>();
        }
        return new PageInfo<>(admins1);
    }

    @Override
    public Users selectById(String userId) {
        Example example = new Example(Users.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("userId", userId);
        return usersMapper.selectOneByExample(example);
    }

    @Override
    public Users selectByUserName(String userName) {
        Example example = new Example(Users.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andLike("userName", "%" + userName + "%");
        return usersMapper.selectOneByExample(example);
    }

    @Override
    public BaseResult update(Users users) {
        BaseResult regexp = regexp(users);
        if (regexp.getStatus() == 500) {
            return regexp;
        } else {
            int i = usersMapper.updateByPrimaryKeySelective(users);
            if (i <= 0) {
                return BaseResult.fail("用户更新失败！");
            } else {
                return BaseResult.success("用户更新成功！");
            }
        }
    }

    @Override
    public BaseResult delete(String userId) {
        /*删除图片*/
        Example example = new Example(Users.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("userId", userId);
        Users users = usersMapper.selectOneByExample(example);
        String url = mImagesPath + users.getUserPhoto().substring(users.getUserPhoto().lastIndexOf("/") + 1);
        File file = new File(url);
        //删除头像
        file.delete();
        deleteComment(userId);
        deleteMessage(userId);
        int i = usersMapper.deleteByPrimaryKey(userId);
        if (i <= 0) {
            return BaseResult.fail("用户删除失败！");
        }
        return BaseResult.success("用户删除成功！");
    }

}
