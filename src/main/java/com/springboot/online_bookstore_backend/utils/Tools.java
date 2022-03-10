package com.springboot.online_bookstore_backend.utils;

import org.apache.commons.mail.HtmlEmail;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;

public class Tools {
    public static String getRandomString(int length){
        String str="abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        Random random=new Random();
        StringBuffer sb=new StringBuffer();
        for(int i=0;i<length;i++){
            int number=random.nextInt(62);
            sb.append(str.charAt(number));
        }
        return sb.toString();
    }

    //邮箱验证码
    public static boolean sendEmail(String emailaddress,String code){
        try {
            HtmlEmail email = new HtmlEmail();//不用更改
            email.setHostName("smtp.qq.com");//需要修改，126邮箱为smtp.126.com,163邮箱为163.smtp.com，QQ为smtp.qq.com
            email.setCharset("UTF-8");
            email.addTo(emailaddress);// 收件地址

            email.setFrom("937617975@qq.com", "网上书城开发团队");//此处填邮箱地址和用户名,用户名可以任意填写

            email.setAuthentication("937617975@qq.com", "fweingkjwzphbffd");//此处填写邮箱地址和客户端授权码

            email.setSubject("网上书城验证码");//此处填写邮件名，邮件名可任意填写
            email.setMsg("尊敬的用户您好,您本次的验证码是:" + code);//此处填写邮件内容

            email.send();
            return true;
        }
        catch(Exception e){
            e.printStackTrace();
            return false;
        }
    }


    public static Date getTimeMinAfter(int min){
        Date date = new Date();
        Calendar now = Calendar.getInstance();
        now.setTime(date);
        now.add(Calendar.MINUTE,min);
        Date afterFiveMin = now.getTime();
        return afterFiveMin;
    }

    public static boolean isDelay(Date time){
        long currentTime =System.currentTimeMillis();
        long createTime = time.getTime();
        long diff=currentTime-createTime;
        if(diff > 0){
            return true;
        }else{
            return false;
        }
    }


}
