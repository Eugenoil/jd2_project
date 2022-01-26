package com.dunin.Messengers.controller;


import com.dunin.Messengers.model.User;
import com.dunin.Messengers.service.RegistrationService;
import com.dunin.Messengers.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;

@Controller
public class RegistrationController {
    @Autowired
    private RegistrationService registrationService;

    @Autowired
    private UserService userService;

    @GetMapping("/registration")
    public String registration() {
        return "registration";
    }

    @PostMapping("/registration")
    public String addUser(
            @RequestParam String name,
            @RequestParam String surname,
            @RequestParam String gender,
            @RequestParam String phoneNumber,
            @RequestParam String info,
            @RequestParam("file") MultipartFile file,
            @RequestParam("password2") String passwordConfirm,
            @RequestParam("g-recaptcha-response") String captchaResponce,
            @Valid User user,
            BindingResult bindingResult,
            Model model
    ) throws IOException {
        registrationService.checkUser(passwordConfirm, captchaResponce, user, file, bindingResult, model);
        return "login";
    }

//    @GetMapping("/activate/{code}")
//    public String activate(Model model, @PathVariable String code) {
//        boolean isActivated = userService.activateUser(code);
//        if (isActivated) {
//            model.addAttribute("messageType", "success");
//            model.addAttribute("message", "User successfully activated");
//        } else {
//            model.addAttribute("messageType", "danger");
//            model.addAttribute("message", "Activation code is not found!");
//        }
//        return "login";
//    }
}