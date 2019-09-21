package com.cw.community.controller;

import com.cw.community.dto.PaginationDTO;
import com.cw.community.model.User;
import com.cw.community.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

@Controller
public class ProfileController {
    @Autowired
    private QuestionService questionService;
    @GetMapping("/profile/{action}")
    public String profile(@RequestParam(name = "page",defaultValue = "1") Integer page,
                          @RequestParam(name = "size",defaultValue = "1") Integer size,
                          @PathVariable(name = "action") String action, Model model,
                          HttpServletRequest request){
        //获取拦截器中设置的session
        User user = (User) request.getSession().getAttribute("user");
        //用户未登录直接返回首页
        if (user==null){
            return "redirect:/";
        }
        if ("questions".equals(action)){//这种写法可以防止空指针
            model.addAttribute("section","questions");
            model.addAttribute("sectionName","我的问题");
        }else if("replies".equals(action)){
            model.addAttribute("section","replies");
            model.addAttribute("sectionName","最新回复");
        }
        PaginationDTO pagination = questionService.list(user.getId(), page, size);
        model.addAttribute("pagination",pagination);
        return "profile";
    }

}
