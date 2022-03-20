package com.nuedu.config;

import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nuedu.util.ResultJson;
import org.apache.commons.lang.StringUtils;
import org.mockito.internal.util.StringUtil;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.servlet.*;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Component
public class TokenFliter implements Filter {
    @Resource
    ObjectMapper objectMapper;
    //白名单
    List<String> whites=new ArrayList<String>();
    public void init(FilterConfig filterConfig) throws ServletException {
        whites.add("/user/login");
    }

    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        //因为有一些是图片的话就直接过去，如果是httpservlet的话 就需要判断一下 是不是有token
        if(servletRequest instanceof HttpServletRequest){
            HttpServletRequest request=(HttpServletRequest)servletRequest;
            HttpServletResponse response=(HttpServletResponse)servletResponse;
            //如果是option请求（如果请求头有header）需要先发一个options请求
            if(request.getMethod().equals("OPTIONS")){
                filterChain.doFilter(servletRequest,servletResponse);
            }
            String path=request.getServletPath();
            for (int i = 0; i <whites.size() ; i++) {
                 if (path.equals(whites.get(i))){
                     filterChain.doFilter(servletRequest,servletResponse);
                     return;
                 }
                 else{
                     response.setContentType("application/json;charset=utf-8");
                     response.setHeader("Access-Control-Allow-Origin","*");
                     //不在白名单 判断一下是否存在 token
                     String token = request.getHeader("token");
                     if (StringUtils.isNotBlank(token)){
                         try {
                             DecodedJWT decoded= JWT.decode(token);
                             //超过三十分钟
                             Date start=decoded.getExpiresAt();
                             Date now=new Date();
                             if ((now.getTime()-start.getTime()>30*60*1000)){
                                 response.getWriter().write(objectMapper.writeValueAsString(ResultJson.auth()));
                                 return;
                             }else {
                                 filterChain.doFilter(servletRequest,servletResponse);
                                 return;
                             }
                         }catch (Exception ex){
                             ex.printStackTrace();
                             response.getWriter().write(objectMapper.writeValueAsString(ResultJson.auth()));
                         }
                     }else {
                         response.getWriter().write(objectMapper.writeValueAsString(ResultJson.auth()));
                         return;
                     }
                 }
            }

        }else {
            filterChain.doFilter(servletRequest,servletResponse);
        }
    }
}
