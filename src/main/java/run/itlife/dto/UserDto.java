package run.itlife.dto;

import run.itlife.entity.Post;
import run.itlife.entity.Role;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

public class UserDto {

    private Long userId;
    private String username;
    private String password;
    private String surname;
    private String firstname;
    private String photo;
    private String info;
    private String www;
    private String email;
    private String phone;
    private String sex;

    public UserDto() {}

    public UserDto(Long userId, String username, String password, String surname, String firstname, String photo, String info, String www, String email, String phone, String sex) {
        this.userId = userId;
        this.username = username;
        this.password = password;
        this.surname = surname;
        this.firstname = firstname;
        this.photo = photo;
        this.info = info;
        this.www = www;
        this.email = email;
        this.phone = phone;
        this.sex = sex;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }


    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public String getWww() {
        return www;
    }

    public void setWww(String www) {
        this.www = www;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

}
