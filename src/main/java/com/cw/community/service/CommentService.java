package com.cw.community.service;

import com.cw.community.dto.CommentDTO;
import com.cw.community.enums.CommentTypeEnum;
import com.cw.community.exception.CustomerErrorCode;
import com.cw.community.exception.CustomizeException;
import com.cw.community.mapper.CommentMapper;
import com.cw.community.mapper.QuestionMapper;
import com.cw.community.mapper.UserMapper;
import com.cw.community.model.Comment;
import com.cw.community.model.Question;
import com.cw.community.model.User;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * created by coffeecw 2019/09/24
 */
@Service
public class CommentService {
    @Autowired
    private CommentMapper commentMapper;
    @Autowired
    private QuestionMapper questionMapper;
    @Autowired
    private UserMapper userMapper;
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

    public List<CommentDTO> listByTargetId(Integer id, CommentTypeEnum type) {
        List<Comment> comments = commentMapper.selectByQuestionIdAndType(id,type.getType());
        if(comments.size()==0){
            return new ArrayList<>();
        }
        //获取去重的评论人
        Set<Integer> commentators = comments.stream().map(commnent -> commnent.getCommentator()).collect(Collectors.toSet());
        ArrayList<Integer> userIds = new ArrayList<>();
        userIds.addAll(commentators);

        //获取评论人并转换为map
        List<User> users = userMapper.findByIds(userIds);
        Map<Integer, User> userMap = users.stream().collect(Collectors.toMap(user -> user.getId(), user -> user));

        //转换comment为commentDTO
        List<CommentDTO> commentDTOS = comments.stream().map(comment -> {
            CommentDTO commentDTO = new CommentDTO();
            BeanUtils.copyProperties(comment,commentDTO);
            commentDTO.setUser(userMap.get(comment.getCommentator()));
            return commentDTO;
        }).collect(Collectors.toList());
        return commentDTOS;


    }
}
