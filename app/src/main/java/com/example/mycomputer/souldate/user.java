package com.example.mycomputer.souldate;

public class user {
    private String f_name;
    private String l_name;
    private int age;
    private String gender;
    private String about;


    user(String firstName, String lastName, int age, String gender , String about){
        this.f_name = firstName;
        this.l_name =lastName;
        this.age = age;
        this.about = about;
        this.gender = gender;
    }


    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getAbout() {
        return about;
    }

    public void setAbout(String about) {
        this.about = about;
    }

    public String getF_name() {
        return f_name;
    }

    public String getL_name() {
        return l_name;
    }

    public void setF_name(String f_name) {
        this.f_name = f_name;
    }

    public void setL_name(String l_name) {
        this.l_name = l_name;
    }
}
