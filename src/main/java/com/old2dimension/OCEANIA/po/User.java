package com.old2dimension.OCEANIA.po;

import com.old2dimension.OCEANIA.vo.UserVO;

import javax.persistence.*;

@Entity
@Table(name = "user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "name")
    private String name;
    @Column(name = "pwd")
    private String pwd;
    public User() {
    }
    public User(int id, String name, String pwd) {
        this.id = id;
       this.name=name;
       this.pwd=pwd;
    }

    public User(UserVO u) {
        this.id = u.getId();
        this.name=u.getName();
        this.pwd=u.getPwd();
    }

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



    //Getters, Setters, Constructors.
    //You can generate them using your IDE, or use Lombok to do that job.
}