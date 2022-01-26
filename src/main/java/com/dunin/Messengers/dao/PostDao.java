package com.dunin.Messengers.dao;

import com.dunin.Messengers.model.Post;
import com.dunin.Messengers.model.User;
import com.dunin.Messengers.model.dto.PostDto;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PostDao extends CrudRepository<Post, Long> {
 @Query("select new com.dunin.Messengers.model.dto.PostDto(" +
            "   p, " +
            "   count(pl), " +
            "   sum(case when pl = :user then 1 else 0 end) > 0" +
            ") " +
            "from Post p left join p.likes pl " +
            "group by p")
    List<PostDto> findAll(@Param("user") User user);

    @Query("select new com.dunin.Messengers.model.dto.PostDto(" +
            "   p, " +
            "   count(pl), " +
            "   sum(case when pl = :user then 1 else 0 end) > 0" +
            ") " +
            "from Post p left join p.likes pl " +
            "where p.tag = :tag " +
            "group by p")
    List<PostDto> findByTag(@Param("tag") String tag, @Param("user") User author);


    @Query("select new com.dunin.Messengers.model.dto.PostDto(" +
            "   p, " +
            "   count(pl), " +
            "   sum(case when pl = :user then 1 else 0 end) > 0" +
            ") " +
            "from Post p left join p.likes pl " +
            "where p.author = :author " +
            "group by p")
    List<PostDto> findByUser(@Param("author") User author, @Param("user") User user);
}
