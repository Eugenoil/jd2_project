package com.dunin.Messengers.model.dto;

import com.dunin.Messengers.model.Post;
import com.dunin.Messengers.model.User;
import lombok.Getter;

@Getter
public class PostDto {
    private Long id;
    private String postname;
    private String text;
    private String tag;
    private User author;
    private String filename;
    private Long likes;
    private Boolean meLiked;

    public PostDto(Post post, Long likes, Boolean meLiked) {
        this.id = post.getId();
        this.postname = post.getPostname();
        this.text = post.getText();
        this.tag = post.getTag();
        this.author = post.getAuthor();
        this.filename = post.getFilename();
        this.likes = likes;
        this.meLiked = meLiked;
    }

    public String getAuthorName() {
        return PostHelper.getAuthorName(author);
    }

    @Override
    public String toString() {
        return "PostDto{" +
                "id=" + id +
                ", author=" + author +
                ", Likes=" + likes +
                ", meLiked=" + meLiked +
                '}';
    }
}
