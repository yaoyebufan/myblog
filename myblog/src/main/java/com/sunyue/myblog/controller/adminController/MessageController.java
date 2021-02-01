package com.sunyue.myblog.controller.adminController;

import com.github.pagehelper.PageInfo;
import com.sunyue.myblog.commons.EmptyUtil;
import com.sunyue.myblog.commons.PageUtil;
import com.sunyue.myblog.entity.BaseResult;
import com.sunyue.myblog.entity.Messages;
import com.sunyue.myblog.entity.Users;
import com.sunyue.myblog.service.MessageService;
import com.sunyue.myblog.service.UserService;
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
public class MessageController {
    @Autowired
    private MessageService messageService;
    @Autowired
    private UserService userService;

    @RequestMapping(value = "/messageList", method = RequestMethod.GET)
    private String messageList(Model model, @RequestParam(defaultValue = "1") int pageNum,
                               @RequestParam(required = false) Integer flag,
                               @RequestParam(required = false) String userName) {
        Messages messages = new Messages();
        Users users = userService.selectByUserName(userName);
        if (!EmptyUtil.isEmpty(users)) {
            messages.setUserId(users.getUserId());
        }
        if (!EmptyUtil.isEmpty(flag)) {
            if (flag != 2) {
                messages.setMessageStatus(flag);
            }
        }
        //pageNum:当前页码，pageSize:每页显示条数,条件为对象
        PageInfo<Messages> page = messageService.page(pageNum, 5, messages,userName);

        //保证不是空指针，index是用于页码设置的
        if (page.getList() != null) {
            List<Integer> index = PageUtil.getIndex(pageNum, page.getPages(), 2);
            model.addAttribute("index", index);
        }

        model.addAttribute("page", page);
        model.addAttribute("flag", messages.getMessageStatus());
        model.addAttribute("userName", userName);
        return "admin/message/message_list";
    }

    @RequestMapping(value = "/messageAdd", method = RequestMethod.GET)
    private String messageAdd(Model model) {
        model.addAttribute("message", new Messages());
        return "admin/message/message_add";
    }

    @RequestMapping(value = "/messageAdd", method = RequestMethod.POST)
    private String messageAdd(RedirectAttributes redirectAttributes, Model model, Messages messages, @RequestParam(required = false) String status) {
        /*判断是否启用评论*/
        if (status == null || status.length() == 0) {
            messages.setMessageStatus(0);
        } else {
            messages.setMessageStatus(1);
        }
        BaseResult baseResult = messageService.add(messages);
        if (baseResult.getStatus() == 500) {
            model.addAttribute("messages",baseResult.getMessage().toString());
            model.addAttribute("message", messages);
            return "admin/message/message_add";
        } else {
            /*无效，重定向了*/
            redirectAttributes.addFlashAttribute("messages", baseResult.getMessage());
            return "redirect:messageList";
        }
    }

    @RequestMapping(value = "/messageUpdate", method = RequestMethod.GET)
    private String messageUpdate(Model model, @RequestParam String messageId) {
        Messages messages = messageService.selectById(messageId);
        model.addAttribute("message", messages);
        return "admin/message/message_update";
    }

    @RequestMapping(value = "/messageUpdate", method = RequestMethod.POST)
    private String messageUpdate(RedirectAttributes redirectAttributes,Model model, Messages messages, @RequestParam(required = false) String status) {
        /*判断是否启用评论*/
        if (status == null || status.length() == 0) {
            messages.setMessageStatus(0);
        } else {
            messages.setMessageStatus(1);
        }
        messages.setParentId(1L);
        BaseResult baseResult = messageService.update(messages);
        ;
        if (baseResult.getStatus() == 500) {
            model.addAttribute("message", messages);
            model.addAttribute("messages",baseResult.getMessage());
            return "admin/message/message_update";
        }
        redirectAttributes.addFlashAttribute("messages", baseResult.getMessage());
        return "redirect:messageList";
    }

    @RequestMapping(value = "/messageDelete", method = RequestMethod.GET)
    private String messageDelete(RedirectAttributes redirectAttributes, @RequestParam String messageId) {
        BaseResult baseResult = messageService.delete(messageId);
        redirectAttributes.addFlashAttribute("messages", baseResult.getMessage());
        return "redirect:messageList";
    }
}

