package com.dunin.Messengers.service;

import com.dunin.Messengers.controller.ControllerUtils;
import com.dunin.Messengers.dao.MessageDao;
import com.dunin.Messengers.service.util.FileSaver;
import com.dunin.Messengers.model.Message;
import com.dunin.Messengers.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.UUID;


@Service
public class MessageService extends FileSaver {
    @Value("${upload.path}")
    private String uploadPath;

    @Autowired
    private MessageDao messageRepo;

    public Iterable<Message> findAll() {
        Iterable<Message> messages;
        messages = messageRepo.findAll();
        return messages;
    }

    public Iterable<Message> filterMessage(@RequestParam String filter) {
        Iterable<Message> messages;
        if (!filter.isEmpty()) {
            messages = messageRepo.findByTag(filter);
        } else {
            messages = messageRepo.findAll();
        }
        return messages;
    }

    public void checkMessage(@AuthenticationPrincipal User user, @Valid Message message, @RequestParam("file") MultipartFile file,
                             BindingResult bindingResult, Model model) throws IOException {
        message.setAuthor(user);
        if (bindingResult.hasErrors()) {
            Map<String, String> errorsMap = ControllerUtils.getErrors(bindingResult);
            model.mergeAttributes(errorsMap);
            model.addAttribute("message", message);
        } else {
            saveFile(message, file);
            model.addAttribute("message", null);
            messageRepo.save(message);
        }
    }

    public void updateMessage(
            @AuthenticationPrincipal User currentUser,
            @PathVariable Long user,
            @RequestParam("id") Message message,
            @RequestParam("text") String text,
            @RequestParam("tag") String tag,
            @RequestParam("file") MultipartFile file
    ) throws IOException {
        if (message.getAuthor().equals(currentUser)) {
            if (!StringUtils.isEmpty(text)) {
                message.setText(text);
            }
            if (!StringUtils.isEmpty(tag)) {
                message.setTag(tag);
            }
            saveFile(message, file);
            messageRepo.save(message);
        }
    }

    public void saveFile(@Valid Message message, @RequestParam("file") MultipartFile file) throws IOException {
        if (file != null && !file.getOriginalFilename().isEmpty()) {
            File uploadDir = new File(uploadPath);
            if (!uploadDir.exists()) {
                uploadDir.mkdir();
            }
            String uuidFile = UUID.randomUUID().toString();
            String resultFilename = uuidFile + "." + file.getOriginalFilename();
            file.transferTo(new File(uploadPath + "/" + resultFilename));
            message.setFilename(resultFilename);
        }
    }
}