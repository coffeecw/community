package com.cw.community.mapper;

import com.cw.community.model.User;
import org.apache.ibatis.annotations.*;

import java.util.ArrayList;
import java.util.List;

@Mapper
public interface UserMapper {
    @Select("select * from user where token = #{token}")
    //注意单个参数要用@Param,对象则不需要
     User queryByToken(@Param("token") String token);
    //插入用户数据
    @Insert("insert into user (name,account_id,token,gmt_create,gmt_modified,avatar_url) values (#{name},#{accountId},#{token},#{gmtCreate},#{gmtModified},#{avatarUrl})")
     void insert(User user);
    @Select("select * from user where id = #{id}")
    User findById(@Param("id") Integer id);
    @Select("select * from user where account_id = #{accountId}")
    User findByAccount(@Param("accountId")String accountId);
    @Update("update user set name = #{name},token=#{token},gmt_modified=#{gmtModified},avatar_url=#{avatarUrl} where id = #{id}")
    void update(User user);
    //查询所有的评论人
    @Select("<script>" + "SELECT * FROM user WHERE id IN " + "<foreach item='item' index='index' collection='userIds' open='(' separator=',' close=')'>" + "#{item}"+ "</foreach>"+ "</script>")
    List<User> findByIds(@Param("userIds") ArrayList<Integer> userIds);
}
