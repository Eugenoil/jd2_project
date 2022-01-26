package com.dunin.Messengers.service;

import com.dunin.Messengers.dao.UserDao;
import com.dunin.Messengers.service.util.FileSaver;
import com.dunin.Messengers.model.Role;
import com.dunin.Messengers.model.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class UserService extends FileSaver implements UserDetailsService {
    @Value("${upload.path.avatar}")
    private String uploadPathAvatar;

    @Value("${send.message.path1}")
    private String uploadMessagePath;

    @Autowired
    private UserDao userRepo;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepo.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("User not found");
        }
        return user;
    }

    public boolean addUser(User user, MultipartFile file) throws IOException {
        User userFromDb = userRepo.findByUsername(user.getUsername());
        if (userFromDb != null) {
            return false;
        }
        user.setActive(true);
        user.setRoles(Collections.singleton(Role.USER));
        user.setActivationCode(UUID.randomUUID().toString());
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        saveAvatar(user, file);
        userRepo.save(user);
        return true;
    }


    public boolean activateUser(String code) {
        User user = userRepo.findByActivationCode(code);
        if (user == null) {
            return false;
        }
        user.setActivationCode(null);
        userRepo.save(user);
        return true;
    }

    public List<User> findAll() {
        return userRepo.findAll();
    }
    public void saveUser(User user, String username, MultipartFile file, Map<String, String> form) throws IOException {
        user.setUsername(username);
        Set<String> roles = Arrays.stream(Role.values())
                .map(Role::name)
                .collect(Collectors.toSet());

        user.getRoles().clear();
        for (String key : form.keySet()) {
            if (roles.contains(key)) {
                user.getRoles().add(Role.valueOf(key));
            }
        }
        saveAvatar(user, file);
        userRepo.save(user);
    }

    public void updateProfile(User user, String password, String gender, String phoneNumber, String email) {
        String userEmail = user.getEmail();
        boolean isGenderChanged = (gender != null);
        boolean isPhoneChanged = (phoneNumber != null);
        boolean isEmailChanged = (email != null && !email.equals(userEmail)) ||
                (userEmail != null && !userEmail.equals(email));
        if (isEmailChanged) {
            user.setEmail(email);
            if (!StringUtils.isEmpty(email)) {
                user.setActivationCode(UUID.randomUUID().toString());
            }
        }
        if (isGenderChanged) {
            user.setGender(gender);
        }
        if (isPhoneChanged) {
            user.setPhoneNumber(phoneNumber);
        }
        if (!StringUtils.isEmpty(password)) {
            user.setPassword(password);
        }
        userRepo.save(user);
        if (isEmailChanged) {
        }
    }

    private void saveAvatar(@Valid User user, @RequestParam("file") MultipartFile file) throws IOException {
        if (file != null && !file.getOriginalFilename().isEmpty()) {
            File uploadDir = new File(uploadPathAvatar);
            if (!uploadDir.exists()) {
                uploadDir.mkdir();
            }
            String uuidFile = UUID.randomUUID().toString();
            String resultFilename = uuidFile + "_" + file.getOriginalFilename();
            file.transferTo(new File(uploadPathAvatar + "/" + resultFilename));
            user.setFilename(resultFilename);
        }
    }

    public void subscribe(User currentUser, User user) {
        user.getSubscribers().add(currentUser);
        userRepo.save(user);
    }

    public void unsubscribe(User currentUser, User user) {
        user.getSubscribers().remove(currentUser);
        userRepo.save(user);
    }
}