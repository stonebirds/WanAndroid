package com.stone.wanandroid.bean;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 * 作者：李飞磊
 * 描述：please add a description here
 * 时间：2019-08-07
 */
@Entity
public class UserInfoBean {
    @Id
    public long id;
    public boolean admin;
    public String email;
    public String icon;
    public String nickname;
    public String password;
    public String token;
    public int type;
    public String username;
    @Generated(hash = 1313160075)
    public UserInfoBean(long id, boolean admin, String email, String icon,
            String nickname, String password, String token, int type,
            String username) {
        this.id = id;
        this.admin = admin;
        this.email = email;
        this.icon = icon;
        this.nickname = nickname;
        this.password = password;
        this.token = token;
        this.type = type;
        this.username = username;
    }
    @Generated(hash = 1818808915)
    public UserInfoBean() {
    }
    public long getId() {
        return this.id;
    }
    public void setId(long id) {
        this.id = id;
    }
    public boolean getAdmin() {
        return this.admin;
    }
    public void setAdmin(boolean admin) {
        this.admin = admin;
    }
    public String getEmail() {
        return this.email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getIcon() {
        return this.icon;
    }
    public void setIcon(String icon) {
        this.icon = icon;
    }
    public String getNickname() {
        return this.nickname;
    }
    public void setNickname(String nickname) {
        this.nickname = nickname;
    }
    public String getPassword() {
        return this.password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public String getToken() {
        return this.token;
    }
    public void setToken(String token) {
        this.token = token;
    }
    public int getType() {
        return this.type;
    }
    public void setType(int type) {
        this.type = type;
    }
    public String getUsername() {
        return this.username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
}
