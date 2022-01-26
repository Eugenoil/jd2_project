package com.dunin.Messengers.controller;


import com.dunin.Messengers.model.Comment;
import com.dunin.Messengers.model.User;
import com.dunin.Messengers.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;

@Controller
public class CommentController {
    @Autowired
    private CommentService commentService;

    @GetMapping("/comment")
    public String main(@RequestParam(required = false, defaultValue = "") String filter, Model model) {
        Iterable<Comment> comments = commentService.filterComment(filter);
        model.addAttribute("comments", comments);
        return "comment";
    }

    @PostMapping("/comment")
    public String addComment(@AuthenticationPrincipal User user, @Valid Comment comment, BindingResult bindingResult, Model model) {
        commentService.checkComment(user, comment, bindingResult, model);
        Iterable<Comment> comments = commentService.findAll();
        model.addAttribute("comments", comments);
        return "comment";
    }
}