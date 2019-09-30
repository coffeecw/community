package com.cw.community.mapper;

import com.cw.community.model.Notification;
import com.cw.community.model.Question;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface NotificationMapper {
    @Insert("insert into notification (notifier,receiver,outer_id,type,gmt_create,status,notifier_name,outer_title) values (#{notifier},#{receiver},#{outerId},#{type},#{gmtCreate},#{status},#{notifierName},#{outerTitle})")
    int insert(Notification notification);
    @Select("select count(1) from notification where receiver = #{userId}")
    Integer countByUserId(@Param("userId") Integer userId);
    @Select("select * from notification where receiver = #{userId} limit #{offset},#{size}")
    List<Notification> listByUser(@Param("userId") Integer userId, @Param("offset") Integer offset, @Param("size") Integer size);
    @Select("select * from notification where id = #{id}")
    Notification selectById(@Param("id") Integer id);
    @Update("update notification set notifier = #{notifier},receiver=#{receiver},outer_id=#{outerId},type=#{type},gmt_create=#{gmtCreate},status = #{status},notifier_name=#{notifierName},outer_title=#{outerTitle} where id = #{id}")
    int updateById(Notification notification);
    @Select("select count(1) from notification where receiver = #{userId} and status = #{status}")
    Integer countByUserIdAndStatus(@Param("userId") Integer userId,@Param("status") Integer status);
}
