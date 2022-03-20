package com.nuedu.pojo;


import java.io.Serializable;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.format.annotation.DateTimeFormat;
import sun.text.resources.FormatData;


@Data
@EqualsAndHashCode(callSuper = false)
public class User extends BasePojo  implements Serializable   {

    private static final long serialVersionUID = 1L;

    /**
     * 登录名
     */
    private String loginName;

    /**
     * 名字Name
     */
    private String name;

    /**
     * 密码
     */
    @JsonIgnore
    private String password;

    /**
     * 手机号
     */
    private String phone;

    /**
     * 邮箱
     */
    private String mail;

    /**
     * 0-无效 1-有效
     */
    private Integer active;

    /**
     * 最后一次时间
     */

    @JsonFormat(pattern="yyyy-MM-dd",timezone = "GMT+8")
    private java.util.Date	lasttime;

    @Override
    public String toString() {
        return "User{" +
                "loginName='" + loginName + '\'' +
                ", name='" + name + '\'' +
                ", password='" + password + '\'' +
                ", phone='" + phone + '\'' +
                ", mail='" + mail + '\'' +
                ", active=" + active +
                ", lasttime=" + lasttime +
                '}';
    }
}
