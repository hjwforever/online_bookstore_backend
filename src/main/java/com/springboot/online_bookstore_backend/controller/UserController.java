package com.springboot.online_bookstore_backend.controller;

import com.springboot.online_bookstore_backend.controller.dto.UserMsgDto;
import com.springboot.online_bookstore_backend.domain.Role;
import com.springboot.online_bookstore_backend.domain.Users;
import com.springboot.online_bookstore_backend.service.CaptchaService;
import com.springboot.online_bookstore_backend.service.UserService;
import com.springboot.online_bookstore_backend.utils.Result;
import com.springboot.online_bookstore_backend.utils.Tools;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/user/account")
public class UserController {
    @Resource
    private UserService userService;
    @Resource
    private CaptchaService captchaService;



    /**
     * 登录
     * @param username
     * @param password
     * @param request
     * @return
     */
    @PostMapping("/login")
    public Result<UserMsgDto> loginController(@RequestParam String username, @RequestParam String password, HttpServletRequest request){

        UserMsgDto userMsgDto = userService.loginService(username, password);
        if(userMsgDto!=null){
            HttpSession session = request.getSession();
            session.setAttribute("userMsg",userMsgDto);
            session.setAttribute("msg","测试session");
            return Result.success(userMsgDto,"登录成功！");
        }else{
            return Result.error("123","账号或密码错误！");
        }
    }

    @GetMapping("/getRegisterCaptcha")
    public Result getRegisterCaptcha(@RequestParam String email){
        if(captchaService.registerCaptchaService(email)){
            return Result.success("验证码已发送到邮箱"+email);
        }else{
            return Result.error("5689", "邮箱已被注册！");
        }
    }

    /**
     * 注册
     * @param newUser
     * @return
     */
    @PostMapping("/register")
    public Result registController(@RequestBody UserMsgDto newUser){
        // 验证验证码是否正确
        if(!captchaService.registerValidateCaptcha(newUser.getEmail(),newUser.getCaptcha())){
            return Result.error("853","验证码错误或失效！");
        }

        Users user = userService.registService(newUser);
        if(user!=null){
            return Result.success("注册成功！");
        }else{
            return Result.error("456","用户名已存在！");
        }
    }

    /**
     * 登出
     * @param request
     * @return
     */
    @GetMapping("/logout")
    public Result logout(HttpServletRequest request){
        HttpSession session = request.getSession();
        session.removeAttribute("userMsg");
        return Result.success();
    }

    //  绑定邮箱
    //返回一个验证码（绑定邮箱与修改密码时用到）
    @PostMapping("/getcaptcha")
    public Result getCaptchaController(HttpServletRequest request, @RequestParam String keyword){
        //前端发送邮箱到后端，后端生成验证码以及失效时间，
        HttpSession session = request.getSession();
        UserMsgDto userMsgDto = (UserMsgDto)session.getAttribute("userMsg");

        String captcha = Tools.getRandomString(4);//生成4位数验证码
        Date ddlTime = Tools.getTimeMinAfter(1);//生成失效时间
        //首先判断key的值是否为邮箱而不是“password”
        if (!keyword.equals("password")){
            String info = captchaService.insertCaptchaService(userMsgDto.getUser_id(), keyword,ddlTime,captcha);
            if (info.equals("成功")){
                return Result.success();
            }
            else return Result.error("232",info);
        }
        else {//key的值为password修改密码时的获取验证码
            String info = captchaService.insertCaptchaService(userMsgDto.getUser_id(), keyword,ddlTime,captcha);
            if (info.equals("成功")){
                return Result.success();
            }
            else return Result.error("234",info);
        }

       // return null;
    }

    //对邮箱进行绑定
    @PostMapping("/bindemail")
    public Result bindEmail(HttpServletRequest request, @RequestParam String email,@RequestParam String captcha){

        HttpSession session = request.getSession();
        UserMsgDto userMsgDto = (UserMsgDto)session.getAttribute("userMsg");


        //判断验证码是否失效
        String info = captchaService.insertEmail(email,captcha, userMsgDto.getUser_id());
        if (info.equals("成功")){
            return Result.success();
        }
        else return Result.error("230",info);

    }

    // 修改密码
    @PostMapping("/updatepassword")
    public Result updatepassword(HttpServletRequest request, @RequestParam String password,@RequestParam String captcha){
        HttpSession session = request.getSession();
        UserMsgDto userMsgDto = (UserMsgDto)session.getAttribute("userMsg");

        String info = captchaService.passwordChangeService(password,captcha,userMsgDto.getUser_id());
        if (info.equals("成功")){
            return Result.success();
        }
        else return Result.error("240",info);

    }

    // 实现复杂查询通过keyword（默认值为”“）查询并返回用户列表
    @GetMapping("/userlist")
    public Result<List<Users>> getUsersList(HttpServletRequest request, @RequestParam String keyword){
        HttpSession session = request.getSession();
        UserMsgDto userMsgDto = (UserMsgDto)session.getAttribute("userMsg");

//        if(userMsgDto.getPrivilegeList().stream().anyMatch(item->item.getPrivname().equals("priv5"))){
        if(true){
            List<Users> usersList = userService.getUsersListService(keyword);

            if(usersList == null || usersList.isEmpty()){
                return Result.error("666", "模糊查询失败");
            }
            else{
                return Result.success(usersList,"模糊查询成功！");
            }
        }
        else{
            return Result.error("666", "模糊查询失败");
        }
    }

    // 为用户分配角色
    @PostMapping("/authorization")
    public Result giveRolesToUsers(HttpServletRequest request, @RequestBody List<Long> roleIdList){
        HttpSession session = request.getSession();
        UserMsgDto userMsgDto = (UserMsgDto)session.getAttribute("userMsg");
        List<Role> roleList = userService.addUsersRoleList(userMsgDto.getUser_id(),roleIdList);

        if(roleList!=null){
            userMsgDto.setRoleList(roleList);
            return Result.success("添加角色成功！");
        }
        else {
            return Result.error("777","添加角色失败！");
        }
    }

    /**
     * 测试session(可删）
     * @param request
     * @return
     */
    @GetMapping("/testsession")
    public Result<String> testsession(HttpServletRequest request){
        System.out.println("test");
        HttpSession session = request.getSession();
        String msg = session.getAttribute("msg").toString();
        System.out.println(msg);
        System.out.println(session);
        return Result.success(msg);
    }
}
