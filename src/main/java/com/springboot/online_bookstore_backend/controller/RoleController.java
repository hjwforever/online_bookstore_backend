package com.springboot.online_bookstore_backend.controller;

import com.springboot.online_bookstore_backend.controller.dto.RoleDto;
import com.springboot.online_bookstore_backend.controller.dto.UserMsgDto;
import com.springboot.online_bookstore_backend.domain.Privilege;
import com.springboot.online_bookstore_backend.domain.Role;
import com.springboot.online_bookstore_backend.service.RoleService;
import com.springboot.online_bookstore_backend.utils.Result;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

@RestController
@RequestMapping("/role")
public class RoleController {


    @Resource
    private RoleService roleService;

    // 获取角色列表
    @GetMapping("/roleList")
    public Result<List<Role>> getRoleListController(HttpServletRequest request){
        HttpSession session = request.getSession();
        UserMsgDto userMsg = (UserMsgDto)session.getAttribute("userMsg");

        boolean isAdmin = true;

//        for(Privilege privilege: userMsg.getPrivilegeList()){
//            //System.out.println("privilege = "+privilege.getDescription());
//            if (privilege.getDescription().equals("系统管理员")){//判断是否为系统管理员
//                isAdmin = true;
//            }
//        }
        if (isAdmin){
            List<Role> roleList = roleService.getAllRoles();
            return Result.success(roleList);
        }

        return Result.error("8800","查看失败，当前登录用户非系统管理员！");
    }

    // 创建角色
    @PostMapping("/add")
    public Result<Role> addRoleController(@RequestBody RoleDto newRole,HttpServletRequest request){
        HttpSession session = request.getSession();
        UserMsgDto userMsg = (UserMsgDto)session.getAttribute("userMsg");

        boolean isAdmin = true;

//        for(Privilege privilege: userMsg.getPrivilegeList()){
//            if (privilege.getDescription().equals("系统管理员")){//判断是否为系统管理员
//                isAdmin = true;
//            }
//        }
        if (isAdmin){
            String info = roleService.addRole(newRole);
            if (info.equals("成功")){
                return Result.success();
            }
            return Result.error("8890",info);

        }

        return Result.error("8800","查看失败，当前登录用户非系统管理员！");
    }

    //  编辑角色
    @PostMapping("/edit")
    public Result<Role> editRoleController(@RequestBody RoleDto newRole,HttpServletRequest request){
        HttpSession session = request.getSession();
        UserMsgDto userMsg = (UserMsgDto)session.getAttribute("userMsg");

        boolean isAdmin = true;

//        for(Privilege privilege: userMsg.getPrivilegeList()){
//            if (privilege.getDescription().equals("系统管理员")){//判断是否为系统管理员
//                isAdmin = true;
//            }
//        }
        if (isAdmin){
            String info = roleService.editRole(newRole);
            if (info.equals("成功")){
                return Result.success();
            }
            return Result.error("8890",info);

        }

        return Result.error("8800","查看失败，当前登录用户非系统管理员！");
    }
}
