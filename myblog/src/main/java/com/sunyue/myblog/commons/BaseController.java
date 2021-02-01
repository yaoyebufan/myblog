package com.sunyue.myblog.commons;

import com.sunyue.myblog.persistence.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

public abstract class BaseController<T,S extends BaseService<T>> {

    /**
     * 分页查询
     *
     * @param request
     * @return
     */
    @Autowired
    private S service;
    @ResponseBody
    @RequestMapping(value = "page", method = RequestMethod.GET, produces = "application/json; charset=utf-8")
    public String page(HttpServletRequest request, T t) {
        String draw1 = request.getParameter("draw");
        String start1 = request.getParameter("start");
        String length1 = request.getParameter("length");
        int start = start1 == null ? 0 : Integer.parseInt(start1);
        int length = length1 == null ? 10 : Integer.parseInt(length1);
        int draw = draw1 == null ? 0 : Integer.parseInt(draw1);
        /*PageInfo<T> pageInfo = service.page(start, length, draw, t);*/
        String s = null;
        try {
           /* s = MapperUtils.obj2json(pageInfo);*/
        } catch (Exception e) {
            e.printStackTrace();
        }
        return s;
    }
}
