package com.cw.community.controller;

import com.cw.community.dto.CommentCreateDTO;
import com.cw.community.dto.CommentDTO;
import com.cw.community.dto.QuestionDTO;
import com.cw.community.enums.CommentTypeEnum;
import com.cw.community.service.CommentService;
import com.cw.community.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

/**
 * created by coffeecw 2019/09/21
 */
@Controller
public class QuestionController {
    @Autowired
    private QuestionService questionService;
    @Autowired
    private CommentService commentService;

    @GetMapping("/question/{id}")
    public String question(@PathVariable("id") Integer id, Model model){
        QuestionDTO questionDTO =  questionService.findById(id);
        //增加阅读数
        questionService.incView(id);
        //查询所有的回复
        List<CommentDTO> comment = commentService.listByTargetId(id, CommentTypeEnum.QUESTION);
        System.out.println(comment);
        model.addAttribute("questionDTO",questionDTO);
        model.addAttribute("comments",comment);
        return "question";
    }

}
