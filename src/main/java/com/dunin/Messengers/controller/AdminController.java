package com.dunin.Messengers.controller;


import com.dunin.Messengers.model.Role;
import com.dunin.Messengers.model.User;
import com.dunin.Messengers.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

@Controller
@RequestMapping("/user")
public class AdminController {
    @Autowired
    private UserService userService;

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping
    public String userList(Model model) {
        model.addAttribute("users", userService.findAll());
        return "userList";
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("{user}")
    public String userEditForm(@PathVariable User user, Model model) {
        model.addAttribute("user", user);
        model.addAttribute("roles", Role.values());
        return "userEdit";
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping
    public String userSave(
            @RequestParam String username,
            @RequestParam(required = false) MultipartFile file,
            @RequestParam Map<String, String> form,
            @RequestParam("userId") User user
    ) throws IOException {
        userService.saveUser(user, username, file, form);
        return "redirect:/user";
    }

    @GetMapping("settings")
    public String getProfile(Model model, @AuthenticationPrincipal User user) {
        model.addAttribute("username", user.getUsername());
        model.addAttribute("gender", user.getGender());
        model.addAttribute("phoneNumber", user.getPhoneNumber());
        model.addAttribute("email", user.getEmail());
        model.addAttribute("avatar", user.getFilename());
        return "settings";
    }

    @PostMapping("settings")
    public String updateProfile(
            @AuthenticationPrincipal User user,
            @RequestParam String gender,
            @RequestParam String phoneNumber,
            @RequestParam String password,
            @RequestParam String email
    ) {
        userService.updateProfile(user, password, gender, phoneNumber, email);
        return "redirect:/";
    }

    @GetMapping("subscribe/{user}")
    public String subscribe(
            @AuthenticationPrincipal User currentUser,
            @PathVariable User user
    ) {
        userService.subscribe(currentUser, user);
        return "redirect:/user-messages/" + user.getId();
    }

    @GetMapping("unsubscribe/{user}")
    public String unsubscribe(
            @AuthenticationPrincipal User currentUser,
            @PathVariable User user
    ) {
        userService.unsubscribe(currentUser, user);
        return "redirect:/user-messages/" + user.getId();
    }

    @GetMapping("{type}/{user}/list")
    public String userList(
            Model model,
            @PathVariable User user,
            @PathVariable String type
    ) {
        model.addAttribute("userChannel", user);
        model.addAttribute("type", type);
        if ("subscriptions".equals(type)) {
            model.addAttribute("users", user.getSubscriptions());
        } else {
            model.addAttribute("users", user.getSubscribers());
        }
        return "subscriptions";
    }
}