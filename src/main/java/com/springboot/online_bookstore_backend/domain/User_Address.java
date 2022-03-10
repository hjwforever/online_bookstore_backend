package com.springboot.online_bookstore_backend.domain;

public class User_Address {
    private long addr_id;

    private long user_id;

    private String phone;

    private String zip_code;

    private String receiver_state;

    private String receiver_city;

    private String receiver_district;

    private String detail_address;

    private String country;

    private boolean default_addr;

    public long getAddr_id() {
        return addr_id;
    }

    public void setAddr_id(long addr_id) {
        this.addr_id = addr_id;
    }

    public long getUser_id() {
        return user_id;
    }

    public void setUser_id(long user_id) {
        this.user_id = user_id;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getZip_code() {
        return zip_code;
    }

    public void setZip_code(String zip_code) {
        this.zip_code = zip_code;
    }

    public String getReceiver_state() {
        return receiver_state;
    }

    public void setReceiver_state(String receiver_state) {
        this.receiver_state = receiver_state;
    }

    public String getReceiver_city() {
        return receiver_city;
    }

    public void setReceiver_city(String receiver_city) {
        this.receiver_city = receiver_city;
    }

    public String getReceiver_district() {
        return receiver_district;
    }

    public void setReceiver_district(String receiver_district) {
        this.receiver_district = receiver_district;
    }

    public String getDetail_address() {
        return detail_address;
    }

    public void setDetail_address(String detail_address) {
        this.detail_address = detail_address;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public boolean isDefault_addr() {
        return default_addr;
    }

    public void setDefault_addr(boolean default_addr) {
        this.default_addr = default_addr;
    }
}
