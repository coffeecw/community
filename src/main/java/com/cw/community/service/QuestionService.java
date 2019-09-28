package com.cw.community.service;

import com.cw.community.dto.PaginationDTO;
import com.cw.community.dto.QuestionDTO;
import com.cw.community.exception.CustomerErrorCode;
import com.cw.community.exception.CustomizeException;
import com.cw.community.mapper.QuestionMapper;
import com.cw.community.mapper.UserMapper;
import com.cw.community.model.Question;
import com.cw.community.model.User;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class QuestionService {
    @Autowired
    private QuestionMapper questionMapper;
    @Autowired
    private UserMapper userMapper;

    public PaginationDTO list(Integer page, Integer size) {
        PaginationDTO paginationDTO = new PaginationDTO();
        Integer totalCount = questionMapper.count();//获取问题总数
        paginationDTO.setPagination(totalCount,page,size);
        //防止页码数为负
        if(page<1){
            page=1;
        }
        //防止页码数超过最大页数
        if(page>paginationDTO.getTotalPage()){
            page = paginationDTO.getTotalPage();
        }

        //size*(page-1)
        Integer offset = size*(page-1);
        List<Question> questions=questionMapper.list(offset,size);
        List<QuestionDTO> questionDTOList = new ArrayList<>();


        for (Question question : questions) {
            User user =  userMapper.findById(question.getCreator());
            QuestionDTO questionDTO = new QuestionDTO();
            BeanUtils.copyProperties(question,questionDTO);
            questionDTO.setUser(user);
            questionDTOList.add(questionDTO);
        }
        paginationDTO.setQuestions(questionDTOList);

        return paginationDTO;
    }

    public PaginationDTO list(Integer userId, Integer page, Integer size) {
        PaginationDTO paginationDTO = new PaginationDTO();
        Integer totalCount = questionMapper.countByUserId(userId);
        paginationDTO.setPagination(totalCount,page,size);
        //防止页码数为负
        if(page<1){
            page=1;
        }
        //防止页码数超过最大页数
        if(page>paginationDTO.getTotalPage()){
            page = paginationDTO.getTotalPage();
        }

        //size*(page-1)
        Integer offset = size*(page-1);
        List<Question> questions=questionMapper.listByUser(userId,offset,size);
        List<QuestionDTO> questionDTOList = new ArrayList<>();


        for (Question question : questions) {
            User user =  userMapper.findById(question.getCreator());
            QuestionDTO questionDTO = new QuestionDTO();
            BeanUtils.copyProperties(question,questionDTO);
            questionDTO.setUser(user);
            questionDTOList.add(questionDTO);
        }
        paginationDTO.setQuestions(questionDTOList);

        return paginationDTO;
    }
    //问题详情
    public QuestionDTO findById(Integer id) {
        Question question = questionMapper.getById(id);
        //异常处理(当客户向浏览器地址栏输入数据库不存在的问题id时)
        if(question==null){
            //抛出自定义异常
            throw new CustomizeException(CustomerErrorCode.QUESTION_NOT_FIND);
        }
        User user =  userMapper.findById(question.getCreator());
        QuestionDTO questionDTO = new QuestionDTO();
        BeanUtils.copyProperties(question,questionDTO);
        questionDTO.setUser(user);
        return questionDTO;
    }

    public void createOrUpdate(Question question) {
        if (question.getId()==null){
            //创建
            question.setGmtCreate(System.currentTimeMillis());
            question.setGmtModified(question.getGmtCreate());
            questionMapper.create(question);
        }else{
            //更新
            question.setGmtCreate(System.currentTimeMillis());
            question.setGmtModified(question.getGmtCreate());
            int updated = questionMapper.updateById(question);
            if(updated!=1){//说明更新失败，因为这条数据可能已经被删除
                throw new CustomizeException(CustomerErrorCode.QUESTION_NOT_FIND);
            }
        }
    }
    //增加阅读数
    public void incView(Integer id) {
        //查询数据库中的数据
        Question question = questionMapper.getById(id);
        Question updatedQuestion = new Question();
        BeanUtils.copyProperties(question,updatedQuestion);
        questionMapper.incViewQuestion(updatedQuestion);

    }

    public List<QuestionDTO> selectRelated(QuestionDTO queryDTO) {
        //判断标签是否为空
        if(StringUtils.isBlank(queryDTO.getTag())){
            return new ArrayList<>();
        }
        String[] tags = StringUtils.split(queryDTO.getTag(), ",");
        String regexpTag = Arrays.stream(tags).collect(Collectors.joining("|"));
        Question question = new Question();
        question.setId(queryDTO.getId());
        question.setTag(regexpTag);
        List<Question> questions = questionMapper.selectRelated(question);
        List<QuestionDTO> questionDTOS = questions.stream().map(q -> {
            QuestionDTO questionDTO = new QuestionDTO();
            BeanUtils.copyProperties(q, questionDTO);
            return questionDTO;
        }).collect(Collectors.toList());
        return questionDTOS;
    }
}
