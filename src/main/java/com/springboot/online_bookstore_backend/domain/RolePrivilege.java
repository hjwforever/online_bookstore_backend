package com.springboot.online_bookstore_backend.domain;

public class RolePrivilege {
    private long id;
    private long role_id;
    private long priv_id;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getRole_id() {
        return role_id;
    }

    public void setRole_id(long role_id) {
        this.role_id = role_id;
    }

    public long getPriv_id() {
        return priv_id;
    }

    public void setPriv_id(long priv_id) {
        this.priv_id = priv_id;
    }
}
