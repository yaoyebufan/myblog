package com.sunyue.myblog.service.serviceImpl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.sunyue.myblog.commons.EmptyUtil;
import com.sunyue.myblog.commons.RegexpUtils;
import com.sunyue.myblog.dao.AdminsMapper;
import com.sunyue.myblog.entity.Admins;
import com.sunyue.myblog.entity.BaseResult;
import com.sunyue.myblog.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class AdminServiceImpl implements AdminService {
    @Autowired
    private AdminsMapper adminsMapper;
    // 获取配置文件中图片的路径
    @Value("${cbs.imagesPath}")
    private String mImagesPath;

    @Override
    public Map login(String email, String pwd) {
        Map<String, Object> map = new HashMap<>();
        if (EmptyUtil.isEmpty(email) || EmptyUtil.isEmpty(pwd)) {
            BaseResult baseResult = BaseResult.fail("账号或密码为空，请重新输入！");
            map.put("admin", null);
            map.put("baseResult", baseResult);
            return map;
        } else {
            Example example = new Example(Admins.class);
            Example.Criteria criteria = example.createCriteria();
            criteria.andEqualTo("adminEmail", email);
            Admins admin = adminsMapper.selectOneByExample(example);
            if (EmptyUtil.isEmpty(admin)) {
                BaseResult baseResult = BaseResult.fail("邮箱账号不存在！");
                map.put("admin", null);
                map.put("baseResult", baseResult);
                return map;
            } else {
                if (!admin.getAdminPassword().equals(pwd)) {
                    BaseResult baseResult = BaseResult.fail("密码错误，请重新输入");
                    map.put("admin", null);
                    map.put("baseResult", baseResult);
                    return map;
                } else {
                    BaseResult baseResult = BaseResult.success();
                    map.put("admin", admin);
                    map.put("baseResult", baseResult);
                    return map;
                }
            }
        }
    }

    @Override
    public List<Admins> list() {
        return adminsMapper.selectAll();
    }

    private BaseResult regexp(Admins admins) {
        if (RegexpUtils.checkNull(admins.getAdminName())) {
            return BaseResult.fail("姓名不能为空");
        }
        if (RegexpUtils.checkPassword(admins.getAdminPassword())) {
            return BaseResult.fail("密码格式错误");
        }
        if (RegexpUtils.checkEmail(admins.getAdminEmail())) {
            return BaseResult.fail("邮箱格式错误");
        }
        if (RegexpUtils.checkPhone(admins.getAdminPhone())) {
            return BaseResult.fail("电话号码格式错误");
        }
        return BaseResult.success();
    }

    @Override
    public BaseResult add(Admins admins) {
        BaseResult regexp = regexp(admins);
        if (regexp.getStatus() == 500) {
            return regexp;
        }
        Example example = new Example(Admins.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("adminEmail", admins.getAdminEmail());
        Admins admin = adminsMapper.selectOneByExample(example);
        if (EmptyUtil.isEmpty(admin)) {
            adminsMapper.insert(admins);
            return BaseResult.success("恭喜您，注册成功！");

        } else {
            return BaseResult.fail("管理员Email账号已注册");
        }
    }

    @Override
    public PageInfo<Admins> page(int pageNum, int pageSize, Admins admins) {
        PageHelper.startPage(pageNum, pageSize);
        // 设置分页查询条件
        Example example = new Example(Admins.class);
        Example.Criteria criteria = example.createCriteria();
        if (!EmptyUtil.isEmpty(admins.getAdminName())) {
            criteria.andLike("adminName", "%" + admins.getAdminName() + "%");
        }
        if (!EmptyUtil.isEmpty(admins.getAdminStatus())) {
            criteria.andEqualTo("adminStatus", admins.getAdminStatus());
        }
        List<Admins> admins1 = adminsMapper.selectByExample(example);
        if (EmptyUtil.isEmpty(admins1)) {
            return new PageInfo<>();
        }
        return new PageInfo<>(admins1);
    }

    @Override
    public Admins selectById(String adminId) {
        Example example = new Example(Admins.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("adminId", adminId);
        return adminsMapper.selectOneByExample(example);
    }

    @Override
    public BaseResult update(Admins admins) {
        BaseResult regexp = regexp(admins);
        if (regexp.getStatus() == 500) {
            return regexp;
        } else {
            int i = adminsMapper.updateByPrimaryKeySelective(admins);
            if (i <= 0) {
                return BaseResult.fail("更新失败");
            } else {
                return BaseResult.success("更新成功");
            }
        }
    }

    @Override
    public BaseResult delete(String adminId) {
        Example example = new Example(Admins.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("adminId", adminId);
        Admins admin = adminsMapper.selectOneByExample(example);
        String url = mImagesPath + admin.getAdminPhoto().substring(admin.getAdminPhoto().lastIndexOf("/") + 1);
        File file = new File(url);
        //删除头像
        file.delete();
        int i = adminsMapper.deleteByPrimaryKey(adminId);
        if (i <= 0) {
            return BaseResult.fail("删除失败");
        }
        return BaseResult.success("删除成功");
    }
}
