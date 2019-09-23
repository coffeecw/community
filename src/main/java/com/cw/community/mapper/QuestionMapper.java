package com.cw.community.mapper;

import com.cw.community.model.Question;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface QuestionMapper {
   //新增问题
   @Insert("insert into question (title,description,gmt_create,gmt_modified,creator,tag) values (#{title},#{description},#{gmtCreate},#{gmtModified},#{creator},#{tag})")
    void create(Question question);
   //分页查询
    @Select("select * from question limit #{offset},#{size}")
    List<Question> list(@Param("offset") Integer offset, @Param("size") Integer size);
    //查询问题总数
    @Select("select count(1) from question")
    Integer count();
    //根据用户id查询问题分页总数
    @Select("select * from question where creator = #{userId} limit #{offset},#{size}")
    List<Question> listByUser(@Param("userId") Integer userId, @Param("offset") Integer offset, @Param("size") Integer size);
    //根据用户id查询问题总数
    @Select("select count(1) from question where creator = #{userId}")
    Integer countByUserId(@Param("userId")Integer userId);
    //查询问题的信息
    @Select("select * from question where id = #{id}")
    Question getById(@Param("id")Integer id);
    //更新问题的信息
    @Update("update question set title=#{title},description=#{description},gmt_create=#{gmtCreate},gmt_modified=#{gmtModified},tag=#{tag} where id = #{id}")
    int updateById(Question question);
    //根据用户id更新阅读数
    @Update("update question set  view_count=#{viewCount}+1 where id = #{id}")
    int incViewQuestion(Question updatedQuestion);



}
