package com.springboot.online_bookstore_backend.domain;

public class Privilege {
    private long priv_id;
    private String privname;
    private String description;

    public long getPriv_id() {
        return priv_id;
    }

    public void setPriv_id(long priv_id) {
        this.priv_id = priv_id;
    }

    public String getPrivname() {
        return privname;
    }

    public void setPrivname(String privname) {
        this.privname = privname;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
