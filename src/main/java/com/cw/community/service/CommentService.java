package com.cw.community.service;

import com.cw.community.enums.CommentTypeEnum;
import com.cw.community.exception.CustomerErrorCode;
import com.cw.community.exception.CustomizeException;
import com.cw.community.mapper.CommentMapper;
import com.cw.community.mapper.QuestionMapper;
import com.cw.community.model.Comment;
import com.cw.community.model.Question;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * created by coffeecw 2019/09/24
 */
@Service
public class CommentService {
    @Autowired
    private CommentMapper commentMapper;
    @Autowired
    private QuestionMapper questionMapper;
    //添加事务
    @Transactional
    public void insertComment(Comment comment) {
        if(comment.getParentId()==null||comment.getParentId()==0){
            throw new CustomizeException(CustomerErrorCode.TARGET_PARAM_NOT_FOUND);
        }
        if(comment.getType()==null || !CommentTypeEnum.isExist(comment.getType())){
            throw new CustomizeException(CustomerErrorCode.TYPE_PARAM_WRONG);
        }
        if(comment.getType()==CommentTypeEnum.COMMENT.getType()){
            //回复评论,根据父类id查找对应的评论
            Comment dbComment = commentMapper.getById(comment.getParentId());
            if(dbComment==null){
                throw new CustomizeException(CustomerErrorCode.COMMENT_NOT_FOUND);
            }
            commentMapper.insert(comment);
        }else{
            //回复问题
            Question question = questionMapper.getById(comment.getParentId());
            if(question==null){
                throw new CustomizeException(CustomerErrorCode.QUESTION_NOT_FIND);
            }
            commentMapper.insert(comment);
            questionMapper.incCommentCount(question);
        }


    }
}
