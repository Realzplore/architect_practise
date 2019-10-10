package com.practise.realz.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

/**
 * @author: realz
 * @package: com.practise.realz.domain
 * @date: 2019-09-23
 * @email: zlp951116@hotmail.com
 */
@Data
@TableName("rl_user")
public class User {
    @JsonSerialize(using = ToStringSerializer.class)
    @TableField(value = "user_id")
    private Long id;
    @NotBlank
    @Size(min = 3, max = 18)
    private String login;
    @NotBlank
    @Size(min = 8, max = 24)
    private String password;
    @NotBlank
    @Size(min = 3, max = 20)
    private String username;
    private String nickname;
    private Boolean isActive = Boolean.TRUE;
}
