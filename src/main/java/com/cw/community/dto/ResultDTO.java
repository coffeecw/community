package com.cw.community.dto;

import com.cw.community.exception.CustomerErrorCode;
import com.cw.community.exception.CustomizeException;
import lombok.Data;

/**
 * created by coffeecw 2019/09/25
 * 根据后端逻辑，给前端返回一个状态码和信息
 */
@Data
public class ResultDTO {
    private String message;
    private Integer code;
    //根据后端逻辑，返回给前端信息和状态码
    public static ResultDTO error0f(String message,Integer code){
        ResultDTO resultDTO = new ResultDTO();
        resultDTO.setMessage(message);
        resultDTO.setCode(code);
        return resultDTO;
    }
    //客户端请求成功
    public static ResultDTO okOf(){
        ResultDTO resultDTO = new ResultDTO();
        resultDTO.setCode(200);
        resultDTO.setMessage("请求成功");
        return resultDTO;
    }

    public static ResultDTO error0f(CustomerErrorCode noLogin) {
        ResultDTO resultDTO = new ResultDTO();
        resultDTO.setMessage(noLogin.getMessage());
        resultDTO.setCode(noLogin.getCode());
        return resultDTO;
    }

    public static ResultDTO errorOf(CustomizeException e) {
        ResultDTO resultDTO = new ResultDTO();
        resultDTO.setMessage(e.getMessage());
        resultDTO.setCode(e.getCode());
        return resultDTO;
    }
}
