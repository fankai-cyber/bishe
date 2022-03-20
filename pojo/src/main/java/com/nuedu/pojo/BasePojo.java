package com.nuedu.pojo;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

@Data
public class BasePojo {
    @TableId(type = IdType.AUTO)
    private Integer id;
    @JsonIgnore
    @TableField(exist = false)
    private Integer pageSize=8;
    @JsonIgnore
    @TableField(exist = false)
    private Integer pageNo=1;

}
