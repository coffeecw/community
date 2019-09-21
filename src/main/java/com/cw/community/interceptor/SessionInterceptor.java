package com.cw.community.interceptor;

import com.cw.community.mapper.UserMapper;
import com.cw.community.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * created by coffeecw 2019/09/21
 */
@Service
public class SessionInterceptor implements HandlerInterceptor {
    @Autowired
    private UserMapper userMapper;
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //获取服务器发送的cookie
        Cookie[] cookies = request.getCookies();
        //循环遍历cookie
        if(cookies!=null&&cookies.length!=0){//先判断浏览器cookie是否为空
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
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}
