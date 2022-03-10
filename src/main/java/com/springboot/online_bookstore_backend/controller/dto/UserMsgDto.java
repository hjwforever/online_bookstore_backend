package com.springboot.online_bookstore_backend.controller.dto;

import com.springboot.online_bookstore_backend.domain.Privilege;
import com.springboot.online_bookstore_backend.domain.Role;
import com.springboot.online_bookstore_backend.domain.User_Address;
import com.springboot.online_bookstore_backend.domain.Users;
import com.springboot.online_bookstore_backend.repository.RolePrivilegeDao;
import com.springboot.online_bookstore_backend.repository.UserRoleDao;

import java.sql.Timestamp;
import java.util.List;

public class UserMsgDto {

    private long user_id;
    // 用户名属性varchar对应String
    private String username;
    private String nickname;
    private String password;
    private boolean gender;
    private String email;
    private String captcha;
    private int age;
    private boolean active;
    private Timestamp updated;
    private Timestamp created;
    private List<Role> roleList;
    private List<Privilege> privilegeList;
    private List<User_Address> addressesList;

    public UserMsgDto(){

    }

    public UserMsgDto(Users user, List<Role> roleList, List<Privilege> privilegeList, List<User_Address> addressesList){
        this.user_id = user.getUser_id();
        this.username = user.getUsername();
        this.nickname = user.getNickname();
        this.gender = user.isGender();
        this.email = user.getEmail();
        this.age = user.getAge();
        this.active = user.isActive();
        this.updated = user.getUpdated();
        this.created = user.getCreated();
        this.roleList = roleList;
        this.privilegeList = privilegeList;
        this.addressesList = addressesList;
    }

    public UserMsgDto(Users user){
        this.user_id = user.getUser_id();
        this.username = user.getUsername();
        this.nickname = user.getNickname();
        this.gender = user.isGender();
        this.email = user.getEmail();
        this.age = user.getAge();
        this.active = user.isActive();
        this.updated = user.getUpdated();
        this.created = user.getCreated();
    }

    public long getUser_id() {
        return user_id;
    }

    public void setUser_id(long user_id) {
        this.user_id = user_id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isGender() {
        return gender;
    }

    public void setGender(boolean gender) {
        this.gender = gender;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCaptcha() {
        return captcha;
    }

    public void setCaptcha(String captcha) {
        this.captcha = captcha;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public Timestamp getUpdated() {
        return updated;
    }

    public void setUpdated(Timestamp updated) {
        this.updated = updated;
    }

    public Timestamp getCreated() {
        return created;
    }

    public void setCreated(Timestamp created) {
        this.created = created;
    }

    public List<Role> getRoleList() {
        return roleList;
    }

    public void setRoleList(List<Role> roleList) {
        this.roleList = roleList;
    }

    public List<Privilege> getPrivilegeList() {
        return privilegeList;
    }

    public void setPrivilegeList(List<Privilege> privilegeList) {
        this.privilegeList = privilegeList;
    }

    public List<User_Address> getAddressesList() {
        return addressesList;
    }

    public void setAddressesList(List<User_Address> addressesList) {
        this.addressesList = addressesList;
    }

    public Users getUser(){
        Users user = new Users();
        user.setUsername(this.username);
        user.setNickname(this.nickname);
        user.setPassword(this.password);
        user.setGender(this.gender);
        user.setEmail(this.email);
        user.setAge(this.age);
        return user;
    }
}
