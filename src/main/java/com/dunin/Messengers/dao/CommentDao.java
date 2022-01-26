package com.dunin.Messengers.dao;

import com.dunin.Messengers.model.Comment;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface CommentDao extends CrudRepository<Comment, Long> {

    List<Comment> findByTag(String tag);

}