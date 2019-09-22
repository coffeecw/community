package com.cw.community.controller;

import com.cw.community.dto.QuestionDTO;
import com.cw.community.model.Question;
import com.cw.community.model.User;
import com.cw.community.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

@Controller
public class PublishController {


    @Autowired
    private QuestionService questionService;

    @GetMapping("/publish/{id}")
    public String edit(@PathVariable(name = "id") Integer id,Model model){
        QuestionDTO question = questionService.findById(id);
        model.addAttribute("title",question.getTitle());
        model.addAttribute("description",question.getDescription());
        model.addAttribute("tag",question.getTag());
        //标识问题的id存放到页面
        model.addAttribute("id",question.getId());
        return "publish";
    }


    @GetMapping("/publish")
    public String publish() {
        return "publish";
    }
    @PostMapping("/publish")
    public String  doPublish(@RequestParam(value = "title")String title,
                             @RequestParam(value = "description")String description,
                             @RequestParam(value = "tag")String tag,
                             @RequestParam(value = "id")Integer id,
                             HttpServletRequest request,
                             Model model){
        model.addAttribute("title",title);
        model.addAttribute("description",description);
        model.addAttribute("tag",tag);
        if(title==null || title==""){
            model.addAttribute("error","标题不能为空");
            return "publish";
        }
        if(description==null || description==""){
            model.addAttribute("error","问题补充不能为空");
            return "publish";
        }
        if(tag==null || tag==""){
            model.addAttribute("error","标签不能为空");
            return "publish";
        }
        //获取拦截器中设置的session
        User user = (User) request.getSession().getAttribute("user");
        //用户未登录
        if(user==null){
            model.addAttribute("error","用户未登录");
            return "publish";
        }
        Question question = new Question();
        question.setTitle(title);
        question.setDescription(description);
        question.setTag(tag);
        //用户id
        question.setCreator(user.getId());
        question.setId(id);
        questionService.createOrUpdate(question);
        return "redirect:/";
    }


}

