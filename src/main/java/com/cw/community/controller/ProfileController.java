package com.cw.community.controller;

import com.cw.community.dto.PaginationDTO;
import com.cw.community.mapper.UserMapper;
import com.cw.community.model.User;
import com.cw.community.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

@Controller
public class ProfileController {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private QuestionService questionService;
    @GetMapping("/profile/{action}")
    public String profile(@RequestParam(name = "page",defaultValue = "1") Integer page,
                          @RequestParam(name = "size",defaultValue = "1") Integer size,
                          @PathVariable(name = "action") String action, Model model,
                          HttpServletRequest request){


        //获取服务器发送的cookie
        Cookie[] cookies = request.getCookies();
        User user = null;
        //循环遍历cookie
        if(cookies!=null&&cookies.length!=0){//先判断浏览器cookie是否为空
            for (Cookie cookie:cookies) {
                if(cookie.getName().equals("token")){
                    //验证服务器发送过来的cookie的值是否跟浏览器端匹配
                    String token = cookie.getValue();
                    user =userMapper.queryByToken(token);
                    if(user!=null){
                        request.getSession().setAttribute("user",user);
                    }
                    break;
                }
            }
        }
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
