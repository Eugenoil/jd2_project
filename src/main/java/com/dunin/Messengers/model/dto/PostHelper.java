package com.dunin.Messengers.model.dto;

import com.dunin.Messengers.model.User;

public abstract class PostHelper {
    public static String getAuthorName(User author){
        return author != null ? author.getUsername() : "<none>";
    }
}
