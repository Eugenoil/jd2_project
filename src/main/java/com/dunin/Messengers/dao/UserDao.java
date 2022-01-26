package com.dunin.Messengers.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import com.dunin.Messengers.model.User;

public interface UserDao extends JpaRepository<User, Long> {
    User findByUsername(String username);

    User findByActivationCode(String code);
}