package com.sunyue.myblog.persistence;

import com.sunyue.myblog.entity.BaseResult;


import java.util.List;

public interface BaseService<T> {
    /**
     * 查询全部信息
     *
     * @return
     */
    List<T> selectAll();

    /*
     * 查询id找到单个对象
     */
    T getById(String id);

    /*
     * 插入一条
     */
    BaseResult add(T t);

    /*
     * 删除一条记录
     */
    BaseResult delete(String id);

    /*
     * 更新一条记录
     */
    BaseResult update(T t);
    /*
      设置是否启用
       */
    void updateStatus(String id, int status);
    /*
     * 总记录
     */
    int count(T t);


}
