package com.cw.community.mapper;

import com.cw.community.model.Comment;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * created by coffeecw 2019/09/24
 */
@Mapper
public interface CommentMapper {
    //插入comment
    @Insert("insert into comment (parent_id,type,commentator,content,gmt_modified,like_count,gmt_create) values(#{parentId},#{type},#{commentator},#{content},#{gmtModified},#{likeCount},#{gmtCreate})")
    void insert(Comment comment);
    //根据parentId(问题id)查询comment
    @Select("select * from comment where id = #{parentId}")
    Comment getById(@Param("parentId") Integer parentId);
    //根据parentId(问题id)和type(问题)查询所有的comment,按创建时间的降序排列
    @Select("select * from comment where parent_id = #{id} and type = #{type} order by gmt_create desc")
    List<Comment> selectByQuestionIdAndType(@Param("id") Integer id, @Param("type") Integer type);
    @Update("update comment set  comment_count=#{commentCount}+1 where id = #{id}")
    int incCommentCount(Comment parentComment);
}
