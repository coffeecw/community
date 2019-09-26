package com.cw.community.controller;

import com.cw.community.dto.CommentCreateDTO;
import com.cw.community.dto.CommentDTO;
import com.cw.community.dto.ResultDTO;
import com.cw.community.exception.CustomerErrorCode;
import com.cw.community.model.Comment;
import com.cw.community.model.User;
import com.cw.community.service.CommentService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;


/**
 * created by coffeecw 2019/09/24
 */
@Controller
public class CommentController {

    @Autowired
    private CommentService commentService;
    @ResponseBody
    @RequestMapping(value = "/comment",method = RequestMethod.POST)
    public Object comment(@RequestBody CommentCreateDTO commentDTO, HttpServletRequest request){

        User user = (User) request.getSession().getAttribute("user");
        if(user==null){
            //提示用户未登录
            return ResultDTO.error0f(CustomerErrorCode.NO_LOGIN);
        }
        //服务器端校验回复内容
        if(commentDTO==null|| StringUtils.isBlank(commentDTO.getContent())){
            return ResultDTO.error0f(CustomerErrorCode.CONTENT_NOT_EMPTY);
        }
        Comment comment = new Comment();
        comment.setParentId(commentDTO.getParentId());
        comment.setType(commentDTO.getType());
        comment.setCommentator(user.getId());
        comment.setContent(commentDTO.getContent());
        comment.setGmtCreate(System.currentTimeMillis());
        comment.setGmtModified(System.currentTimeMillis());
        comment.setLikeCount(1L);
        System.out.println(comment);
        commentService.insertComment(comment);
        return ResultDTO.okOf();
    }
}
