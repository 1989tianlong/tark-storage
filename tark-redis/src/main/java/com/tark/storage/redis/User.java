package com.tark.storage.redis;

import java.io.Serializable;

/**
 * Created by jh on 2017/5/6.
 */
public class User implements Serializable {

    private Long id;
    private String userName;
    private Integer age;

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", userName='" + userName + '\'' +
                ", age=" + age +
                '}';
    }

    public static User getUser() {
        User user= new User();
        user.setUserName("ytl");
        user.setAge(23);
        return user;
    }
}
