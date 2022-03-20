package com.nuedu.config;


import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nuedu.pojo.Mail;
import com.nuedu.pojo.User;
import com.nuedu.util.JwtUtil;
import com.nuedu.util.ResultJson;
import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang.StringUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.mvc.condition.RequestConditionHolder;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;


@Component
@Aspect
public class FkAop {
    @Resource
    RabbitTemplate rabbitTemplate;
    @Resource
    BCryptPasswordEncoder passwordEncoder;
    @Resource
    ObjectMapper objectMapper;


//    @Before("execution(* com.nuedu.controller.UserController.list(..))")
//    public void before(JoinPoint joinPoint){
//        System.out.println(joinPoint.getArgs());
//        System.out.println(joinPoint.getKind());
//        System.out.println(joinPoint.toString());
//        System.out.println("方法之前");
//    }
//    @AfterReturning(value = "execution(* com.nuedu.controller.UserController.list(..))",returning = "result")
//    public void after(JoinPoint joinPoint,ResultJson result) throws JsonProcessingException {
//        System.out.println(objectMapper.writeValueAsString(result));
//        System.out.println(result);
//        System.out.println("方法之后");
//
//    }
//成功返回结果之后
//    @AfterThrowing("execution(* com.nuedu.controller.UserController.list(..))")
//    public void throwException(){
//        System.out.println("异常");
//    }
//环绕通知会代替原有的方法
//        @Around("execution(* com.nuedu.controller.UserController.list(..))")
//                public void around(){
//                    System.out.println("方法之后");
//
//        }
//
    @Around("execution(* com.nuedu.controller.UserController.add(..))")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
        //获取参数
        Object[] params = joinPoint.getArgs();
        User user=(User) params[0];
        String random = RandomStringUtils.random(6,true,true);
        user.setPassword(passwordEncoder.encode(random));
        Date date = new Date();
        user.setLasttime(date);
        Object o = joinPoint.proceed();
        Mail mail=new Mail("2234076313@qq.com",
                user.getMail(),
                "系统消息","已经为您创建用户，密码是"+random+"" +
                ",请您妥善保管，不要告诉他人");

        rabbitTemplate.convertAndSend("mail",objectMapper.writeValueAsString(mail));
        System.out.println("队列信息发送完毕");
        return o;
        }
    @Around("execution(* com.nuedu.controller.*.*(..))")
    public Object updataToken(ProceedingJoinPoint joinpoint) throws Throwable {
        //拿到返回值
        Object obj=joinpoint.proceed();
        //判断返回值的类型 返回ResultJson类型说明 用户进行了访问操作
        if(obj instanceof ResultJson){
            //先是找到token 在请求头里面
            //利用已经有的token进行解析，并且赋予新的值
            ResultJson resultJson=(ResultJson)obj;
            RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
            if (requestAttributes instanceof ServletRequestAttributes){
                HttpServletRequest request=((ServletRequestAttributes) requestAttributes).getRequest();
                String token = request.getHeader("token");
                if (StringUtils.isNotBlank(token)){
                    DecodedJWT decode = JWT.decode(token);
                    String username=decode.getClaim("User_name").asString();
                    String newtoken = JwtUtil.creatToken(username);
                    resultJson.setToken(newtoken);
                    return resultJson;
                }else{
                    return obj;
                }
            }else {
                return obj;
            }


        }else {
            return obj;
        }
    }


}
