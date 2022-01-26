package com.dunin.Messengers.service;

import com.dunin.Messengers.controller.ControllerUtils;
import com.dunin.Messengers.model.User;
import com.dunin.Messengers.model.dto.CaptchaResponseDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;
import java.util.Collections;
import java.util.Map;

@Service
public class RegistrationService {
    private final static String CAPTCHA_URL = "https://www.google.com/recaptcha/api/siteverify?secret=%s&response=%s";

    @Autowired
    private UserService userService;

    @Autowired
    private RestTemplate restTemplate;

    @Value("${recaptcha.secret}")
    private String secret;

    public String checkUser(@RequestParam("password2") String passwordConfirm,
                            @RequestParam("g-recaptcha-response") String captchaResponce,
                            @Valid User user,
                            @RequestParam("file") MultipartFile file,
                            BindingResult bindingResult, Model model) throws IOException {
        String url = String.format(CAPTCHA_URL, secret, captchaResponce);
        CaptchaResponseDto response = restTemplate.postForObject(url, Collections.emptyList(), CaptchaResponseDto.class);
        if (!response.isSuccess()) {
            model.addAttribute("captchaError", "Fill captcha");
        }
        boolean isConfirmEmpty = StringUtils.isEmpty(passwordConfirm);
        if (isConfirmEmpty) {
            model.addAttribute("password2Error", "Password confirmation cannot be empty");
        }
        if (user.getPassword() != null && !user.getPassword().equals(passwordConfirm)) {
            model.addAttribute("passwordError", "Passwords are different!");
        }
        if (isConfirmEmpty || bindingResult.hasErrors() || !response.isSuccess()) {
            Map<String, String> errors = ControllerUtils.getErrors(bindingResult);
            model.mergeAttributes(errors);
            return "registration";
        }
        if (!userService.addUser(user, file)){
            model.addAttribute("usernameError", "User exists!");
            return "registration";
        }
        return "redirect:/login";
    }
}