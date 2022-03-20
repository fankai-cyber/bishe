package test;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.nuedu.AdminApp;
import com.nuedu.pojo.User;
import com.nuedu.service.IUserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.boot.autoconfigure.security.oauth2.resource.OAuth2ResourceServerProperties;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.Date;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = AdminApp.class)//添加springboot起到扫包的作用
public class BootTest {

    @Resource
    BCryptPasswordEncoder bCryptPasswordEncoder;
    @Resource
    IUserService userService;
    @Resource
    RabbitTemplate rabbitTemplate;
//    @Test
//    public void hander(){
//        System.out.println(userService.list());
//    }
//    @Test
////    public void hander(){
////        rabbitTemplate.convertAndSend("mailqueue","我是一个消息");
////
////    }
    @Test
    public void hander(){
        String sign = JWT.create()
                .withClaim("username", "fankai")
                .withClaim("password", "111")
                .withExpiresAt(new Date())
                .sign(Algorithm.HMAC256("fankai"));
        System.out.println(sign);
        DecodedJWT jwt = JWT.decode(sign);
        System.out.println(jwt.getClaim("username").asString());
        System.out.println(jwt.getClaim("password").asString());



    }
}
