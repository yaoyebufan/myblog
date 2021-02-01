package com.sunyue.myblog.controller.uiController;

import com.sunyue.myblog.dao.MessagesMapper;
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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class InformationController {
    @Autowired
    private UserService userService;
    @Autowired
    private MessageService messageService;

    @RequestMapping(value = "/message", method = RequestMethod.GET)
    private String message(Model model) {
        List<Users> usersList = userService.list();
        model.addAttribute("usersList", usersList);
        List<Messages> messagesList = messageService.list();
        model.addAttribute("messagesList", messagesList);
        return "ui/message";
    }

    @RequestMapping(value = "/message", method = RequestMethod.POST)
    private String message(Model model, HttpServletRequest request, RedirectAttributes redirectAttributes) {
        Users user = (Users) request.getSession().getAttribute("user");
        String mes = request.getParameter("mes");
        Messages message = new Messages();
        message.setMessageStatus(0);
        message.setUserId(user.getUserId());
        message.setMessageContent(mes);
        BaseResult baseResult = messageService.add(message);
        redirectAttributes.addFlashAttribute("messages", baseResult.getMessage());
        return "redirect:/message";
    }
}
