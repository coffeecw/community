package com.cw.community.advice;

import com.alibaba.fastjson.JSON;
import com.cw.community.dto.ResultDTO;
import com.cw.community.exception.CustomerErrorCode;
import com.cw.community.exception.CustomizeException;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * created by coffeecw 2019/09/23
 * 拦截SpringMVC可以Handler的异常
 */
@ControllerAdvice
public class CustomizeExceptionHandler {
    @ExceptionHandler(Exception.class)
    ModelAndView handleControllerException(Throwable e, Model model,
                                           HttpServletRequest request,
                                           HttpServletResponse response) {
        //获取请求类型
        String contentType = request.getContentType();
        if("application/json".equals(contentType)){
            //返回JSON
            ResultDTO resultDTO;
            if(e instanceof CustomizeException){
                 //用户操作引发的异常
                 resultDTO = ResultDTO.errorOf((CustomizeException)e);
            }else{
                 //服务器内部异常
                 resultDTO = ResultDTO.error0f(CustomerErrorCode.SYS_ERROR);
            }

            try {
                response.setContentType("application/json");
                response.setStatus(200);
                response.setCharacterEncoding("utf-8");
                String jsonString = JSON.toJSONString(resultDTO);
                PrintWriter writer = response.getWriter();
                writer.write(jsonString);
                writer.close();
            } catch (IOException ioe) {
            }
            return null;
        }else{//返回错误页面
            if(e instanceof CustomizeException){
                model.addAttribute("message",e.getMessage());
            }else{
                model.addAttribute("message",CustomerErrorCode.SYS_ERROR.getMessage());
            }
            return new ModelAndView("error");
        }


    }

}
