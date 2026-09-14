package com.example.demo.model;

public class StudentResponse {
    private String name;
    private int age;
    private boolean adult;


    public StudentResponse(String name, int age, boolean adult) {
        this.name = name;
        this.age = age;
        this.adult = adult;
    }

    public boolean isAdult() {
        return adult;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }


}
