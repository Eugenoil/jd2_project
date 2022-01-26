package com.dunin.Messengers.dao;

import com.dunin.Messengers.model.Message;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface MessageDao extends CrudRepository<Message, Long> {

    List<Message> findByTag(String tag);

}