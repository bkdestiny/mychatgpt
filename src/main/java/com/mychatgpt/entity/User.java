package com.mychatgpt.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.ToString;

import java.time.LocalDateTime;

@Data
@ToString
@TableName(value = "user")
public class User {
    @TableId(value = "id",type = IdType.AUTO)
    private Long id;
    private String openId;
    private String sessionKey;
    private String avatarUrl;
    private String nickName;
    private LocalDateTime lastVisitTime;
    private LocalDateTime createTime;
}
