package com.example.imagecaptionbackend.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.*;

@Entity
@ApiModel("用户实体类")
@Table(name = "user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @ApiModelProperty(value = "用户名", required = true, example = "username")
    @Column(unique = true, name = "username")
    private String username;
    @ApiModelProperty(value = "密码", required = true, example = "password")
    @Column(name = "password")
    private String password;
    @ApiModelProperty(value = "角色", required = true, example = "0 代表管理员, 1 代表用户")
    @Column(name = "role")
    private String role;

    public User(String username, String password, String role) {
        this.username = username;
        this.password = password;
        this.role = role;
    }

    public User() {
    }

    public Long getId() {
        return id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }
}
