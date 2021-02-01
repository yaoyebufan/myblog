package com.sunyue.myblog.controller.uiController;

import com.sunyue.myblog.entity.Admins;
import com.sunyue.myblog.entity.BaseResult;
import com.sunyue.myblog.entity.Comments;
import com.sunyue.myblog.entity.Users;
import com.sunyue.myblog.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

@Controller
public class DiscussController {
    @Autowired
    private CommentService commentService;
    @RequestMapping(value = "/discuss", method = RequestMethod.POST)
    private String discuss(Model model, HttpServletRequest request, RedirectAttributes redirectAttributes) {
        Users user = (Users) request.getSession().getAttribute("user");
        String com = request.getParameter("com");
        String articleId = request.getParameter("articleId");
        Comments comment = new Comments();
        comment.setCommentStatus(0);
        comment.setUserId(user.getUserId());
        comment.setCommentContent(com);
        comment.setArticleId(Long.parseLong(articleId));
        comment.setCommentLikes((long) 0);
        BaseResult baseResult = commentService.add(comment);
        /*无效，重定向了*/
        redirectAttributes.addFlashAttribute("messages", baseResult.getMessage());
        return "redirect:"+"/article?articleId="+articleId;
    }
}
