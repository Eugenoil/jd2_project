package com.dunin.Messengers.controller;

import com.dunin.Messengers.model.Message;
import com.dunin.Messengers.model.User;
import com.dunin.Messengers.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;
import java.util.Set;

@Controller
public class MessageController {
    @Autowired
    private MessageService messageService;

    @GetMapping("/")
    public String greeting(Model model) {
        return "greeting";
    }

    @GetMapping("/main")
    public String main(@AuthenticationPrincipal User user, @RequestParam(required = false, defaultValue = "") String filter, Model model) {
        Iterable<Message> messages = messageService.filterMessage(filter);
        model.addAttribute("messages", messages);
        return "main";
    }

    @PostMapping("/main")
    public String add(@AuthenticationPrincipal User user, @Valid Message message, @RequestParam("file") MultipartFile file, BindingResult bindingResult, Model model) throws IOException {
        messageService.checkMessage(user, message, file, bindingResult, model);
        Iterable<Message> messages = messageService.findAll();
        model.addAttribute("messages", messages);
        return "main";
    }

    @GetMapping("/user-messages/{user}")
    public String userMessages(
            @AuthenticationPrincipal User currentUser,
            @PathVariable User user,
            Model model,
            @RequestParam(required = false) Message message
    ) {
        Set<Message> messages = user.getMessages();
        model.addAttribute("userChannel", user);
        model.addAttribute("subscriptionsCount", user.getSubscriptions().size());
        model.addAttribute("subscribersCount", user.getSubscribers().size());
        model.addAttribute("isSubscriber", user.getSubscribers().contains(currentUser));
        model.addAttribute("userPhoto", user.getFilename());
        model.addAttribute("userName", user.getName());
        model.addAttribute("userSurname", user.getSurname());
        model.addAttribute("info", user.getInfo());
        model.addAttribute("messages", messages);
        model.addAttribute("message", message);
        model.addAttribute("isCurrentUser", currentUser.equals(user));
        return "userMessages";
    }

    @PostMapping("/user-messages/{user}")
    public String updateMessage(
            @AuthenticationPrincipal User currentUser,
            @PathVariable Long user,
            @RequestParam("id") Message message,
            @RequestParam("text") String text,
            @RequestParam("tag") String tag,
            @RequestParam("file") MultipartFile file
    ) throws IOException {
            messageService.updateMessage(currentUser, user, message, text, tag, file);
        return "redirect:/user-messages/" + user;
    }
}