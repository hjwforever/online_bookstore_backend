package com.springboot.online_bookstore_backend.controller.dto;

import java.util.List;

public class RoleDto {
    private long role_id;
    private String rolename;
    private String description;
    private List<Long> privilegeList;

    public long getRole_id() {
        return role_id;
    }

    public void setRole_id(long role_id) {
        this.role_id = role_id;
    }

    public String getRolename() {
        return rolename;
    }

    public void setRolename(String rolename) {
        this.rolename = rolename;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<Long> getPrivilegeList() {
        return privilegeList;
    }

    public void setPrivilegeList(List<Long> privilegeList) {
        this.privilegeList = privilegeList;
    }
}
