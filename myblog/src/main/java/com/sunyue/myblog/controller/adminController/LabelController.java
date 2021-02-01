package com.sunyue.myblog.controller.adminController;

import com.github.pagehelper.PageInfo;
import com.sunyue.myblog.commons.EmptyUtil;
import com.sunyue.myblog.commons.PageUtil;
import com.sunyue.myblog.entity.BaseResult;
import com.sunyue.myblog.entity.Labels;
import com.sunyue.myblog.service.LabelService;
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
public class LabelController {
    @Autowired
    private LabelService labelService;

    @RequestMapping(value = "/labelList", method = RequestMethod.GET)
    private String labelList(Model model, @RequestParam(defaultValue = "1") int pageNum,
                             @RequestParam(required = false) String labelName,
                             @RequestParam(required = false) Integer flag) {
        Labels labels = new Labels();
        if (!EmptyUtil.isEmpty(labelName)) {
            labels.setLabelName(labelName);
        }
        if (!EmptyUtil.isEmpty(flag)) {
            if (flag != 2) {
                labels.setLabelStatus(flag);
            }
        }
        //pageNum:当前页码，pageSize:每页显示条数,条件为对象
        PageInfo<Labels> page = labelService.page(pageNum, 5, labels);

        //保证不是空指针，index是用于页码设置的
        if (page.getList() != null) {
            List<Integer> index = PageUtil.getIndex(pageNum, page.getPages(), 2);
            model.addAttribute("index", index);
        }
        model.addAttribute("page", page);
        model.addAttribute("flag", labels.getLabelStatus());
        model.addAttribute("labelName", labels.getLabelName());
        return "admin/label/label_list";
    }

    @RequestMapping(value = "/labelAdd", method = RequestMethod.GET)
    private String labelAdd(Model model) {
        model.addAttribute("label", new Labels());
        return "admin/label/label_add";
    }

    @RequestMapping(value = "/labelAdd", method = RequestMethod.POST)
    private String labelAdd(RedirectAttributes redirectAttributes, Model model, Labels labels, @RequestParam(required = false) String status) {
        /*判断是否启用账号*/
        if (status == null || status.length() == 0) {
            labels.setLabelStatus(0);
        } else {
            labels.setLabelStatus(1);
        }
        BaseResult baseResult = labelService.add(labels);
        if (baseResult.getStatus() == 500) {
            model.addAttribute("label", labels);
            model.addAttribute("messages", baseResult.getMessage());
            return "admin/label/label_add";
        } else {
            redirectAttributes.addFlashAttribute("messages", baseResult.getMessage());
            return "redirect:labelList";
        }
    }


    @RequestMapping(value = "/labelUpdate", method = RequestMethod.GET)
    private String labelUpdate(Model model, @RequestParam String labelId) {
        Labels labels = labelService.selectById(labelId);
        model.addAttribute("label", labels);
        return "admin/label/label_update";
    }

    @RequestMapping(value = "/labelUpdate", method = RequestMethod.POST)
    private String labelUpdate(RedirectAttributes redirectAttributes, Model model, Labels labels, @RequestParam(required = false) String status) {
        /*判断是否启用账号*/
        if (status == null || status.length() == 0) {
            labels.setLabelStatus(0);
        } else {
            labels.setLabelStatus(1);
        }
        BaseResult baseResult = labelService.update(labels);
        if (baseResult.getStatus() == 500) {
            model.addAttribute("label", labels);
            model.addAttribute("messages", baseResult.getMessage());
            return "admin/label/label_update";
        }
        redirectAttributes.addFlashAttribute("messages", baseResult.getMessage());
        return "redirect:labelList";
    }

    @RequestMapping(value = "/labelDelete", method = RequestMethod.GET)
    private String labelDelete(RedirectAttributes redirectAttributes, @RequestParam String labelId) {
        BaseResult baseResult = labelService.delete(labelId);
        redirectAttributes.addFlashAttribute("messages", baseResult.getMessage());
        return "redirect:labelList";
    }
}
