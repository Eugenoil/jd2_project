package com.dunin.Messengers.service;

import com.dunin.Messengers.controller.ControllerUtils;
import com.dunin.Messengers.dao.PostDao;
import com.dunin.Messengers.service.util.FileSaver;
import com.dunin.Messengers.model.Post;
import com.dunin.Messengers.model.User;
import com.dunin.Messengers.model.dto.PostDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.UUID;

@Service
public class PostService extends FileSaver {
    @Value("${upload.path.posts}")
    private String uploadPathPost;

    @Autowired
    private PostDao postRepo;

    public Iterable<Post> findAll() {
        Iterable<Post> posts;
        posts = postRepo.findAll();
        return posts;
    }

    public Iterable<PostDto> filterPost(@RequestParam String filter, User user) {
        Iterable<PostDto> posts;
        if (!filter.isEmpty()) {
            posts = postRepo.findByTag(filter, user);
        } else {
            posts = postRepo.findAll(user);
        }
        return posts;
    }

    public void checkPost(@AuthenticationPrincipal User user, @Valid Post post, @RequestParam("file") MultipartFile file, BindingResult bindingResult, Model model) throws IOException {
        post.setAuthor(user);
        if (bindingResult.hasErrors()) {
            Map<String, String> errorsMap = ControllerUtils.getErrors(bindingResult);
            model.mergeAttributes(errorsMap);
            model.addAttribute("posts", post);
        } else {
            saveFile(post, file);
            postRepo.save(post);
        }
    }

    public Iterable<PostDto> postListForUser(User currentUser, User author) {
        return postRepo.findByUser(author, currentUser);
    }

    private void saveFile(@Valid Post post, @RequestParam("file") MultipartFile file) throws IOException {
        if (file != null && !file.getOriginalFilename().isEmpty()) {
            File uploadDir = new File(uploadPathPost);
            if (!uploadDir.exists()) {
                uploadDir.mkdir();
            }
            String uuidFile = UUID.randomUUID().toString();
            String resultFilename = uuidFile + "." + file.getOriginalFilename();
            file.transferTo(new File(uploadPathPost + "/" + resultFilename));
            post.setFilename(resultFilename);
        }
    }
}

