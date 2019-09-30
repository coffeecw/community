package com.cw.community.controller;

import com.cw.community.dto.NotificationDTO;
import com.cw.community.enums.NotificationTypeEnum;
import com.cw.community.model.User;
import com.cw.community.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.servlet.http.HttpServletRequest;

/**
 * created by coffeecw 2019/09/30
 */
@Controller
public class NotificationController {
    @Autowired
    private NotificationService notificationService;

    @GetMapping("/notification/{id}")
    public String profile(@PathVariable(name = "id") Integer id,
                          HttpServletRequest request) {
        //获取拦截器中设置的session
        User user = (User) request.getSession().getAttribute("user");
        //用户未登录直接返回首页
        if (user == null) {
            return "redirect:/";
        }
        NotificationDTO notificationDTO = notificationService.read(id, user);
        if (NotificationTypeEnum.REPLY_COMMENT.getType() == notificationDTO.getType() || NotificationTypeEnum.REPLY_QUESTION.getType()==notificationDTO.getType()) {
            return "redirect:/question/"+notificationDTO.getOuterId();
        }else{
            return "redirect:/";
        }

    }
}
