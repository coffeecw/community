package com.cw.community.mapper;

import com.cw.community.model.Comment;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * created by coffeecw 2019/09/24
 */
@Mapper
public interface CommentMapper {
    @Insert("insert into comment (parent_id,type,commentator,content,gmt_modified,like_count,gmt_create) values(#{parentId},#{type},#{commentator},#{content},#{gmtModified},#{likeCount},#{gmtCreate})")
    void insert(Comment comment);

    @Select("select * from comment where id = #{parentId}")
    Comment getById(@Param("parentId") Integer parentId);
}
