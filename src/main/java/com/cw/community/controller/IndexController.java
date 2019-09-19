package com.cw.community.controller;

import com.cw.community.mapper.UserMapper;
import com.cw.community.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;


@Controller
public class IndexController {
    @Autowired
    private UserMapper userMapper;
    //主页
    @GetMapping("/")
    public String index(HttpServletRequest request) {
        //获取服务器发送的cookie
        Cookie[] cookies = request.getCookies();
        //循环遍历cookie
        if(cookies!=null&&cookies.length!=0){//先判断浏览器cookie是否为空
            for (Cookie cookie:cookies) {
                if(cookie.getName().equals("token")){
                    //验证服务器发送过来的cookie的值是否跟浏览器端匹配
                    String token = cookie.getValue();
                    User user =userMapper.queryByToken(token);
                    if(user!=null){
                        request.getSession().setAttribute("user",user);
                    }
                    break;
                }
            }
        }

        return "index";
    }

}



