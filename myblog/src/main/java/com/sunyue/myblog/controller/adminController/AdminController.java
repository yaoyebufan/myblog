package com.sunyue.myblog.controller.adminController;

import com.github.pagehelper.PageInfo;
import com.sunyue.myblog.commons.EmptyUtil;
import com.sunyue.myblog.commons.PageUtil;
import com.sunyue.myblog.entity.Admins;
import com.sunyue.myblog.entity.BaseResult;
import com.sunyue.myblog.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping(value = "admin")
public class AdminController {
    @Autowired
    private AdminService adminService;

    @RequestMapping(value = "", method = RequestMethod.GET)
    private String index(Model model,HttpServletRequest request) {
        return "admin/index";
    }

    @RequestMapping(value = "/adminList", method = RequestMethod.GET)
    private String adminList(Model model, @RequestParam(defaultValue = "1") int pageNum,
                             @RequestParam(required = false) String adminName,
                             @RequestParam(required = false) Integer flag) {
        Admins admins = new Admins();
        if (!EmptyUtil.isEmpty(adminName)) {
            admins.setAdminName(adminName);
        }
        if (!EmptyUtil.isEmpty(flag)) {
            if (flag != 2) {
                admins.setAdminStatus(flag);
            }
        }
        //pageNum:当前页码，pageSize:每页显示条数,条件为对象
        PageInfo<Admins> page = adminService.page(pageNum, 5, admins);
        //保证不是空指针，index是用于页码设置的
        if (page.getList() != null) {
            List<Integer> index = PageUtil.getIndex(pageNum, page.getPages(), 2);
            model.addAttribute("index", index);
        }
        model.addAttribute("page", page);
        model.addAttribute("flag", admins.getAdminStatus());
        model.addAttribute("adminName", admins.getAdminName());
        return "admin/admin/admin_list";
    }

    @RequestMapping(value = "/adminAdd", method = RequestMethod.GET)
    private String adminAdd(Model model) {
        model.addAttribute("admin", new Admins());
        return "admin/admin/admin_add";
    }

    @RequestMapping(value = "/adminAdd", method = RequestMethod.POST)
    private String adminAdd(RedirectAttributes redirectAttributes, Model model, Admins admins, @RequestParam(required = false) String status) { ;
        /*判断是否启用管理员账号*/
        if (status == null || status.length() == 0) {
            admins.setAdminStatus(0);
        } else {
            admins.setAdminStatus(1);
        }
        /*设置时间*/
        admins.setAdminCreateTime(new Date());
        admins.setAdminLoginTime(new Date());
        BaseResult baseResult = adminService.add(admins);
        if (baseResult.getStatus() == 500) {
            model.addAttribute("admin", admins);
            model.addAttribute("messages",baseResult.getMessage());
            return "admin/admin/admin_add";
        } else {
            redirectAttributes.addFlashAttribute("messages", baseResult.getMessage());
            return "redirect:adminList";
        }
    }


    @RequestMapping(value = "/adminUpdate", method = RequestMethod.GET)
    private String adminUpdate(Model model, @RequestParam String adminId) {
        Admins admins = adminService.selectById(adminId);
        model.addAttribute("admin", admins);
        return "admin/admin/admin_update";
    }

    @RequestMapping(value = "/adminUpdate", method = RequestMethod.POST)
    private String adminUpdate(RedirectAttributes redirectAttributes,Model model, Admins admins,@RequestParam(required = false) String status) {
        /*判断是否启用管理员账号*/
        if (status == null || status.length() == 0) {
            admins.setAdminStatus(0);
        } else {
            admins.setAdminStatus(1);
        }
        BaseResult baseResult = adminService.update(admins);;
        if (baseResult.getStatus()==500){
            model.addAttribute("admin", admins);
            model.addAttribute("messages",baseResult.getMessage());
            return "admin/admin/admin_update";
        }
        redirectAttributes.addFlashAttribute("messages", baseResult.getMessage());
        return "redirect:adminList";
    }

    @RequestMapping(value = "/adminDelete", method = RequestMethod.GET)
    private String adminDelete(RedirectAttributes redirectAttributes, @RequestParam String adminId) {
        BaseResult baseResult = adminService.delete(adminId);
        redirectAttributes.addFlashAttribute("messages", baseResult.getMessage());
        return "redirect:adminList";
    }


    @ResponseBody
    @RequestMapping(value = "upload", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
    private String upload(@RequestParam("fileName") MultipartFile file, HttpServletRequest request) {
        System.out.println("a");
        System.out.println(file.getOriginalFilename());
        return "aaa";
    }

}
