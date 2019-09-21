package com.cw.community.controller;

import com.cw.community.dto.PaginationDTO;
import com.cw.community.dto.QuestionDTO;
import com.cw.community.mapper.UserMapper;
import com.cw.community.model.User;
import com.cw.community.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.List;


@Controller
public class IndexController {
    @Autowired
    private UserMapper userMapper;

    @Autowired
    private QuestionService questionService;
    //主页
    @GetMapping("/")
    public String index(@RequestParam(name = "page",defaultValue = "1") Integer page,
                        @RequestParam(name = "size",defaultValue = "5") Integer size,
            HttpServletRequest request, Model model) {

        //获取服务器发送的cookie
        Cookie[] cookies = request.getCookies();
        //循环遍历cookie

        if(cookies!=null&&cookies.length!=0)//先判断浏览器cookie是否为空
            for (Cookie cookie:cookies) {
                if(cookie.getName().equals("token")){
                    //验证服务器发送过来的cookie的值是否跟浏览器端匹配
                    String token = cookie.getValue();
                    User user = userMapper.queryByToken(token);
                    if(user!=null){
                        request.getSession().setAttribute("user",user);
                    }
                    break;
                }
            }

        PaginationDTO pagination = questionService.list(page,size);
        model.addAttribute("pagination",pagination);
        return "index";
    }

}



