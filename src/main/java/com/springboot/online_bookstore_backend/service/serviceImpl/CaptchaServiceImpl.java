package com.springboot.online_bookstore_backend.service.serviceImpl;

import com.springboot.online_bookstore_backend.domain.Captcha;
import com.springboot.online_bookstore_backend.repository.CaptchaDao;
import com.springboot.online_bookstore_backend.repository.UserDao;
import com.springboot.online_bookstore_backend.service.CaptchaService;
import com.springboot.online_bookstore_backend.utils.Tools;
import org.apache.commons.mail.HtmlEmail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
//import org.apache.commons.mail.EmailException;
//import org.apache.commons.mail.HtmlEmail;


import javax.annotation.Resource;
import java.util.Date;
@Service
public class CaptchaServiceImpl implements CaptchaService {
    @Resource
    private CaptchaDao captchaDao;
    @Resource
    private UserDao userDao;
    @Autowired
    private BCryptPasswordEncoder encoding;


    @Override
    public String insertCaptchaService(long userId, String keyword, Date ddl_time, String captcha) {
        if (keyword.equals("password")){
            //判断验证码表中是否存在userid，若存在，则对该数据修改，若不存在，则新建
            Captcha record = new Captcha();
            record.setUser_id(userId);
            record.setKeyword(keyword);
            record.setDdl_time(ddl_time);
            record.setCaptcha(captcha);
            if (captchaDao.findByUserIdForPasswordChange(userId) == null){
                captchaDao.insert(record);
            }else {
                captchaDao.updatePasswordCaptcha(record);
            }

            //将生成的验证码发送至邮箱
            Tools.sendEmail(userDao.findByUserId(userId).getEmail(),captcha);

            //return "成功";
        }else {
            //现判断此邮箱是否已经被注册
            if (!userDao.findByEmail(keyword).isEmpty()) {//判断前端发来的邮箱是否已经被注册
                return "该邮箱已被注册";

            }
            //判断验证码表中是否存在userid，若存在，则对该数据修改，若不存在，则新建
            Captcha record = new Captcha();
            record.setUser_id(userId);
            record.setKeyword(keyword);
            record.setDdl_time(ddl_time);
            record.setCaptcha(captcha);
            if (captchaDao.findByUserId(userId) == null){
                captchaDao.insert(record);
            }else {
                captchaDao.update(record);

            }
            //发送邮件
            Tools.sendEmail(userDao.findByUserId(userId).getEmail(),captcha);

        }

        return "成功";

    }

    @Override
    public boolean registerCaptchaService(String email) {

        //现判断此邮箱是否已经被注册
        if (!userDao.findByEmail(email).isEmpty()) {//判断前端发来的邮箱是否已经被注册
            return false;
        }
        Captcha captchaEmail = captchaDao.findByEmail(email);
        if (captchaEmail != null){
            if(Tools.isDelay(captchaEmail.getDdl_time())){
                String captcha = Tools.getRandomString(4);//生成4位数验证码
                Date ddlTime = Tools.getTimeMinAfter(1);//生成失效时间
                captchaDao.registerUpdate(captcha,ddlTime,email);
                Tools.sendEmail(email,captcha);
            }else{
                Tools.sendEmail(email, captchaEmail.getCaptcha());
            }
        }else{
            String captcha = Tools.getRandomString(4);//生成4位数验证码
            Date ddl_Time = Tools.getTimeMinAfter(1);//生成失效时间
            //若此邮箱尚未被注册，则发送验证码
            Tools.sendEmail(email,captcha);
            //将验证码数据插入验证码表captcha中
            Captcha record = new Captcha();
            record.setKeyword(email);
            record.setDdl_time(ddl_Time);
            record.setCaptcha(captcha);
            captchaDao.insert(record);
        }

        return true;
    }

    @Override
    public boolean registerValidateCaptcha(String email, String captcha) {

        Captcha captchaEmail = captchaDao.findByEmail(email);
        if(captchaEmail==null){
            return false;
        }else{
            if(Tools.isDelay(captchaEmail.getDdl_time())){  // 如果超时
                return false;
            }else{
                if(captchaEmail.getCaptcha().equals(captcha)){
                    return true;
                }else{
                    return false;
                }
            }
        }
    }


    @Override
    public String passwordChangeService(String password, String captcha, long userId) {
        //先判断是否已经发送验证码
        if (captchaDao.findByUserIdForPasswordChange(userId) == null){

            return "请先发送验证码";
        }
        //再判断验证码是否过期
        if (Tools.isDelay(captchaDao.findByUserIdForPasswordChange(userId).getDdl_time())){
            return "验证码已过期，请重新发送！";
        }
        //接着判断验证码是否正确
        if (!captcha.equals(captchaDao.findByUserIdForPasswordChange(userId).getCaptcha())){
            return "验证码错误请重新输入";
        }

        //进行以上判断后将修改后的密码存到user表中
        userDao.updatePassword(userId,encoding.encode(password));

        captchaDao.passwordDelete(userId);
        return "成功";
    }

    @Override
    public String insertEmail(String email, String captcha, long userId) {
        //先判断是否已经发送验证码
        if (captchaDao.findByUserId(userId) == null){
            return "请先发送验证码";
        }
        //接着判断邮箱是否与发送验证码时的邮箱相同
        if (!email.equals(captchaDao.findByUserId(userId).getKeyword())){
            return "邮箱输入错误，请重新输入！";
        }
        //再判断验证码是否过期
        if (Tools.isDelay(captchaDao.findByUserId(userId).getDdl_time())){
            return "验证码已过期，请重新发送！";
        }
        //接着判断验证码是否正确
        if (!captcha.equals(captchaDao.findByUserId(userId).getCaptcha())){
            return "验证码错误请重新输入";
        }

        //进行以上判断后将邮箱存到user表中
        userDao.updateEmail(userId, email);

        //然后删除验证码表中对应userID的数据
        captchaDao.emailDelete(userId);

        return "成功";
    }
}
