package com.example.stafflist.models;

import java.io.Serializable;

public class Worker implements Serializable {
    private String image;
    private String name;
    private String surname;
    private String age;
    private String post;

    public Worker(String image, String name, String surname, String age, String post) {
        this.image = image;
        this.name = name;
        this.surname = surname;
        this.age = age;
        this.post = post;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getPost() {
        return post;
    }

    public void setPost(String post) {
        this.post = post;
    }
}
