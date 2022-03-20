package com.nuedu.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.nuedu.pojo.User;

import java.util.Date;

public class JwtUtil {
    public static  String creatToken(String  username){
        String sign = JWT.create().withClaim("User_name", username)
                .withExpiresAt(new Date())
                .sign(Algorithm.HMAC256("fankai"));
        return sign;


    }
}
