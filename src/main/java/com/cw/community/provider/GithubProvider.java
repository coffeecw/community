package com.cw.community.provider;

import com.alibaba.fastjson.JSON;
import com.cw.community.dto.AccessTokenDTO;
import com.cw.community.dto.GithubUser;
import okhttp3.*;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class GithubProvider {
    //获取access_token
    public String getAccessToken(AccessTokenDTO accessTokenDTO){
        //创建OkHttp客户端
        OkHttpClient client = new OkHttpClient();
        //发送请求的数据格式
        MediaType mediaType = MediaType.get("application/json; charset=utf-8");
        //请求发送json数据
        RequestBody body = RequestBody.create(mediaType,JSON.toJSONString(accessTokenDTO) );
        //根据json数据中的参数拼接请求地址
        Request request = new Request.Builder()
                .url("https://github.com/login/oauth/access_token")
                .post(body)
                .build();
        try (Response response = client.newCall(request).execute()) {
            //获取响应的字符串
            String str = response.body().string();
            //打印的内容access_token=a279d119c604935bd7a275bd5ffa4538464ff787&scope=user&token_type=bearer
            System.out.println(str);
            String accessToken = str.split("&")[0].split("=")[1];
            //返回响应的access_token
            return accessToken;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
   }

   public GithubUser getUser(String accessToken){
       //创建OkHttp客户端
       OkHttpClient client = new OkHttpClient();
       //请求地址
       Request request = new Request.Builder()
               .url("https://api.github.com/user?access_token=" + accessToken)
               .build();
       try {
           Response response = client.newCall(request).execute();
           //获取响应的字符串
           String str = response.body().string();
           //将字符串转换为GithubUser对象
           GithubUser githubUser = JSON.parseObject(str,GithubUser.class);
           return githubUser;
       } catch (IOException e) {
           e.printStackTrace();
       }
        return null;
   }

}
