package com.old2dimension.OCEANIA.vo;

import com.old2dimension.OCEANIA.po.User;

import java.util.Objects;

public class UserVO {
    public UserVO() {
    }

    public UserVO(int id, String name, String pwd) {
        this.id = id;
        this.name = name;
        this.pwd = pwd;
    }

    public UserVO(User u) {
        this.id = u.getId();
        this.name = u.getName();
        this.pwd = u.getPwd();
    }

    private int id;

    private String name;

    private String pwd;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserVO userVO = (UserVO) o;
        return id == userVO.id &&
                Objects.equals(name, userVO.name) &&
                Objects.equals(pwd, userVO.pwd);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, pwd);
    }
}
