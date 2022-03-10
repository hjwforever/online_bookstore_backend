package com.springboot.online_bookstore_backend.domain;

public class Clickstream_Log {

    private String ipaddress;

    private String uniqueid;

    private String url;

    private String sessionid;

    private String sessiontime;

    private String areaaddress;

    private String localaddress;

    private String browsertype;

    private String operationsys;

    private String referurl;

    private String receivetime;

    private String userid;

    private String csvp;

    public String getIpaddress() {
        return ipaddress;
    }

    public void setIpaddress(String ipaddress) {
        this.ipaddress = ipaddress;
    }

    public String getUniqueid() {
        return uniqueid;
    }

    public void setUniqueid(String uniqueid) {
        this.uniqueid = uniqueid;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getSessionid() {
        return sessionid;
    }

    public void setSessionid(String sessionid) {
        this.sessionid = sessionid;
    }

    public String getSessiontime() {
        return sessiontime;
    }

    public void setSessiontime(String sessiontime) {
        this.sessiontime = sessiontime;
    }

    public String getAreaaddress() {
        return areaaddress;
    }

    public void setAreaaddress(String areaaddress) {
        this.areaaddress = areaaddress;
    }

    public String getLocaladdress() {
        return localaddress;
    }

    public void setLocaladdress(String localaddress) {
        this.localaddress = localaddress;
    }

    public String getBrowsertype() {
        return browsertype;
    }

    public void setBrowsertype(String browsertype) {
        this.browsertype = browsertype;
    }

    public String getOperationsys() {
        return operationsys;
    }

    public void setOperationsys(String operationsys) {
        this.operationsys = operationsys;
    }

    public String getReferurl() {
        return referurl;
    }

    public void setReferurl(String referurl) {
        this.referurl = referurl;
    }

    public String getReceivetime() {
        return receivetime;
    }

    public void setReceivetime(String receivetime) {
        this.receivetime = receivetime;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getCsvp() {
        return csvp;
    }

    public void setCsvp(String csvp) {
        this.csvp = csvp;
    }
}
