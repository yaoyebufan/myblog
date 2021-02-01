package com.sunyue.myblog.controller.adminController;

import com.github.pagehelper.PageInfo;
import com.sunyue.myblog.commons.EmptyUtil;
import com.sunyue.myblog.commons.PageUtil;
import com.sunyue.myblog.entity.BaseResult;
import com.sunyue.myblog.entity.Sorts;
import com.sunyue.myblog.service.SortService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping(value = "admin")
public class SortController {
    @Autowired
    private SortService sortService;

    @RequestMapping(value = "/sortList", method = RequestMethod.GET)
    private String sortList(Model model, @RequestParam(defaultValue = "1") int pageNum,
                            @RequestParam(required = false) String sortName,
                            @RequestParam(required = false) Integer flag) {
        Sorts sorts = new Sorts();
        if (!EmptyUtil.isEmpty(sortName)) {
            sorts.setSortName(sortName);
        }
        if (!EmptyUtil.isEmpty(flag)) {
            if (flag != 2) {
                sorts.setSortStatus(flag);
            }
        }
        //pageNum:当前页码，pageSize:每页显示条数,条件为对象
        PageInfo<Sorts> page = sortService.page(pageNum, 5, sorts);

        //保证不是空指针，index是用于页码设置的
        if (page.getList() != null) {
            List<Integer> index = PageUtil.getIndex(pageNum, page.getPages(), 2);
            model.addAttribute("index", index);
        }
        model.addAttribute("page", page);
        model.addAttribute("flag", sorts.getSortStatus());
        model.addAttribute("sortName", sorts.getSortName());
        return "admin/sort/sort_list";
    }

    @RequestMapping(value = "/sortAdd", method = RequestMethod.GET)
    private String sortAdd(Model model) {
        model.addAttribute("sort", new Sorts());
        return "admin/sort/sort_add";
    }

    @RequestMapping(value = "/sortAdd", method = RequestMethod.POST)
    private String sortAdd(RedirectAttributes redirectAttributes, Model model, Sorts sorts, @RequestParam(required = false) String status) {
        /*判断是否启用账号*/
        if (status == null || status.length() == 0) {
            sorts.setSortStatus(0);
        } else {
            sorts.setSortStatus(1);
        }
        BaseResult baseResult = sortService.add(sorts);
        if (baseResult.getStatus() == 500) {
            model.addAttribute("sort", sorts);
            model.addAttribute("messages", baseResult.getMessage());
            return "admin/sort/sort_add";
        } else {
            redirectAttributes.addFlashAttribute("messages", baseResult.getMessage());
            return "redirect:sortList";
        }
    }


    @RequestMapping(value = "/sortUpdate", method = RequestMethod.GET)
    private String sortUpdate(Model model, @RequestParam String sortId) {
        Sorts sorts = sortService.selectById(sortId);
        model.addAttribute("sort", sorts);
        return "admin/sort/sort_update";
    }

    @RequestMapping(value = "/sortUpdate", method = RequestMethod.POST)
    private String sortUpdate(RedirectAttributes redirectAttributes, Model model, Sorts sorts, @RequestParam(required = false) String status) {
        /*判断是否启用账号*/
        if (status == null || status.length() == 0) {
            sorts.setSortStatus(0);
        } else {
            sorts.setSortStatus(1);
        }
        BaseResult baseResult = sortService.update(sorts);
        if (baseResult.getStatus() == 500) {
            model.addAttribute("sort", sorts);
            model.addAttribute("messages", baseResult.getMessage());
            return "admin/sort/sort_update";
        }
        redirectAttributes.addFlashAttribute("messages", baseResult.getMessage());
        return "redirect:sortList";
    }

    @RequestMapping(value = "/sortDelete", method = RequestMethod.GET)
    private String sortDelete(RedirectAttributes redirectAttributes, @RequestParam String sortId) {
        BaseResult baseResult = sortService.delete(sortId);
        redirectAttributes.addFlashAttribute("messages", baseResult.getMessage());
        return "redirect:sortList";
    }
}
