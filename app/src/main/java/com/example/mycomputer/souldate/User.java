package com.example.mycomputer.souldate;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class User {
    private String f_name,l_name;
    private int age;
    private String gender;
    private String about;
    private String email;
    private ArrayList<String> friends;
    public User(){}
    public User(String firstName, String lastName, int age, String gender , String about, String email){
        this.f_name = firstName;
        this.l_name =lastName;
        this.age = age;
        this.about = about;
        this.gender = gender;
        this.email = email;
        friends = new ArrayList<String>();// Might have to add an item to show at first!!
        friends.add("My list of friends");
    }

    public User(User other) {
        this.f_name = other.f_name;
        this.l_name = other.l_name;
        this.age = other.age;
        this.gender = other.gender;
        this.about = other.about;
        this.email = other.email;
        this.friends.addAll(other.friends);
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public ArrayList<String> getFriends() {
        return friends;
    }
    public void addFriend(String friendUID) {
        friends.add(friendUID);
    }
    public boolean isFriend(String friendUID){
        return friends.contains(friendUID);
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

    @Override
    public String toString() {
        return "User{" +
                "f_name='" + f_name + '\'' +
                ", l_name='" + l_name + '\'' +
                ", age=" + age +
                ", gender='" + gender + '\'' +
                ", about='" + about + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
