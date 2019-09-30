package com.cw.community.service;

import com.cw.community.dto.NotificationDTO;
import com.cw.community.dto.PaginationDTO;
import com.cw.community.dto.QuestionDTO;
import com.cw.community.enums.NotificationStatusEnum;
import com.cw.community.enums.NotificationTypeEnum;
import com.cw.community.exception.CustomerErrorCode;
import com.cw.community.exception.CustomizeException;
import com.cw.community.mapper.NotificationMapper;
import com.cw.community.mapper.UserMapper;
import com.cw.community.model.Notification;
import com.cw.community.model.Question;
import com.cw.community.model.User;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * created by coffeecw 2019/09/30
 */
@Service
public class NotificationService {

    @Autowired
    private NotificationMapper notificationMapper;

    @Autowired
    private UserMapper userMapper;

    public PaginationDTO list(Integer userId, Integer page, Integer size) {
        PaginationDTO<NotificationDTO> paginationDTO = new PaginationDTO<>();
        Integer totalCount = notificationMapper.countByUserId(userId);
        paginationDTO.setPagination(totalCount,page,size);
        //防止页码数为负
        if(page<=1){
            page=1;
        }else if(page>paginationDTO.getTotalPage()){//防止页码数超过最大页数
            page = paginationDTO.getTotalPage();
        }



        //size*(page-1)
        Integer offset = size*(page-1);
        List<Notification> notifications=notificationMapper.listByUser(userId,offset,size);


        if(notifications.size()==0){
            return paginationDTO;
        }
        List<NotificationDTO> notificationDTOS = new ArrayList<>();
        for (Notification notification : notifications) {
            NotificationDTO notificationDTO = new NotificationDTO();
            BeanUtils.copyProperties(notification,notificationDTO);
            notificationDTO.setTypeName(NotificationTypeEnum.nameOf(notification.getType()));
            notificationDTOS.add(notificationDTO);
        }


        paginationDTO.setData(notificationDTOS);

        return paginationDTO;
    }
    //查询未读通知的条数
    public Integer unreadCount(Integer userId) {
        return notificationMapper.countByUserIdAndStatus(userId,NotificationStatusEnum.UNREAD.getStatus());
    }

    public NotificationDTO read(Integer id, User user) {
        Notification notification= notificationMapper.selectById(id);
        if(notification==null){
            throw new CustomizeException(CustomerErrorCode.NOTIFICATION_NOT_FIND);
        }
        if(!notification.getReceiver().equals(user.getId())){
            throw new CustomizeException(CustomerErrorCode.READ_NOTIFICATION_FAIL);
        }

        notification.setStatus(NotificationStatusEnum.READ.getStatus());
        notificationMapper.updateById(notification);

        NotificationDTO notificationDTO = new NotificationDTO();
        BeanUtils.copyProperties(notification,notificationDTO);
        notificationDTO.setTypeName(NotificationTypeEnum.nameOf(notification.getType()));
        return notificationDTO;
    }
}
