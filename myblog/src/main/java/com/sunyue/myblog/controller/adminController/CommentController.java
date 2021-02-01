package com.sunyue.myblog.controller.adminController;

import com.github.pagehelper.PageInfo;
import com.sunyue.myblog.commons.EmptyUtil;
import com.sunyue.myblog.commons.PageUtil;
import com.sunyue.myblog.entity.BaseResult;
import com.sunyue.myblog.entity.Comments;
import com.sunyue.myblog.entity.Users;
import com.sunyue.myblog.service.CommentService;
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
public class CommentController {
    @Autowired
    private CommentService commentService;
    @Autowired
    private UserService userService;

    @RequestMapping(value = "/commentList", method = RequestMethod.GET)
    private String commentList(Model model, @RequestParam(defaultValue = "1") int pageNum,
                               @RequestParam(required = false) Integer flag,
                               @RequestParam(required = false) String userName) {
        Comments comments = new Comments();
        Users users = userService.selectByUserName(userName);
        if (!EmptyUtil.isEmpty(users)) {
            comments.setUserId(users.getUserId());
        }
        if (!EmptyUtil.isEmpty(flag)) {
            if (flag != 2) {
                comments.setCommentStatus(flag);
            }
        }
        //pageNum:当前页码，pageSize:每页显示条数,条件为对象
        PageInfo<Comments> page = commentService.page(pageNum, 5, comments,userName);

        //保证不是空指针，index是用于页码设置的
        if (page.getList() != null) {
            List<Integer> index = PageUtil.getIndex(pageNum, page.getPages(), 2);
            model.addAttribute("index", index);
        }

        model.addAttribute("page", page);
        model.addAttribute("flag", comments.getCommentStatus());
        model.addAttribute("userName", userName);
        return "admin/comment/comment_list";
    }

    @RequestMapping(value = "/commentAdd", method = RequestMethod.GET)
    private String commentAdd(Model model) {
        model.addAttribute("comment", new Comments());
        return "admin/comment/comment_add";
    }

    @RequestMapping(value = "/commentAdd", method = RequestMethod.POST)
    private String commentAdd(RedirectAttributes redirectAttributes, Model model, Comments comments, @RequestParam(required = false) String status) {
        /*判断是否启用评论*/
        if (status == null || status.length() == 0) {
            comments.setCommentStatus(0);
        } else {
            comments.setCommentStatus(1);
        }
        BaseResult baseResult = commentService.add(comments);
        if (baseResult.getStatus() == 500) {
            model.addAttribute("comment", comments);
            model.addAttribute("messages",baseResult.getMessage());
            return "admin/comment/comment_add";
        } else {
            redirectAttributes.addFlashAttribute("messages", baseResult.getMessage());
            return "redirect:commentList";
        }
    }

    @RequestMapping(value = "/commentUpdate", method = RequestMethod.GET)
    private String commentUpdate(Model model, @RequestParam String commentId) {
        Comments comments = commentService.selectById(commentId);
        model.addAttribute("comment", comments);
        return "admin/comment/comment_update";
    }

    @RequestMapping(value = "/commentUpdate", method = RequestMethod.POST)
    private String commentUpdate(RedirectAttributes redirectAttributes,Model model, Comments comments, @RequestParam(required = false) String status) {
        /*判断是否启用评论*/
        if (status == null || status.length() == 0) {
            comments.setCommentStatus(0);
        } else {
            comments.setCommentStatus(1);
        }
        comments.setParentId(1L);
        BaseResult baseResult = commentService.update(comments);;
        if (baseResult.getStatus() == 500) {
            model.addAttribute("comment", comments);
            model.addAttribute("messages",baseResult.getMessage());
            return "admin/comment/comment_update";
        }
        redirectAttributes.addFlashAttribute("messages", baseResult.getMessage());
        return "redirect:commentList";
    }

    @RequestMapping(value = "/commentDelete", method = RequestMethod.GET)
    private String commentDelete(RedirectAttributes redirectAttributes, @RequestParam String commentId) {
        BaseResult baseResult = commentService.delete(commentId);
        redirectAttributes.addFlashAttribute("messages", baseResult.getMessage());
        return "redirect:commentList";
    }
}
