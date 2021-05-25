package com.example.trucks_order.service.model;


import javax.validation.constraints.NotBlank;

/**
 * user领域模型
 * @author lmwis on 2019-04-09 15:13
 */
public class UserModel {

    private Integer id;
//    @NotBlank(message = "昵称不能为空")
    private String nickname;
    @NotBlank(message = "邮箱不能为空")
    private String email;
    @NotBlank(message = "密码不能为空")
    private String password;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
