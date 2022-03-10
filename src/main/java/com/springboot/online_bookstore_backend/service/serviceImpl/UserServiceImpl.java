package com.springboot.online_bookstore_backend.service.serviceImpl;

import com.springboot.online_bookstore_backend.controller.dto.UserMsgDto;
import com.springboot.online_bookstore_backend.domain.*;
import com.springboot.online_bookstore_backend.repository.*;
import com.springboot.online_bookstore_backend.service.UserService;
import com.springboot.online_bookstore_backend.service.User_AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    @Resource
    private UserDao userDao;

    @Resource
    private UserRoleDao userRoleDao;

    @Resource
    private RolePrivilegeDao rolePrivilegeDao;

    @Resource
    private PrivilegeDao privilegeDao;

    @Resource
    private RoleDao roleDao;

    @Resource
    private User_AddressService userAddressService;

    @Autowired
    private BCryptPasswordEncoder encoding;

    /**
     * 根据角色列表获取权限列表
     *
     * @param rolesList 角色列表
     * @return
     */
    public List<Privilege> getAllPrivilegesByRole(List<Role> rolesList) {
        List<Privilege> requestPrivilegeList = new ArrayList<>();
        List<Long> privIdList = new ArrayList<>();
        // 遍历所有的角色
        for (Role role : rolesList) {
            List<RolePrivilege> rolesRightsList = rolePrivilegeDao.findByRoleId(role.getRole_id());
            // 遍历该角色的所有权限id
            for (RolePrivilege rolePrivilege : rolesRightsList) {
                Privilege privilege = privilegeDao.findById(rolePrivilege.getPriv_id());
                // 去重
                if (privilege != null && !privIdList.contains(privilege.getPriv_id())) {
                    privIdList.add(privilege.getPriv_id());
                    requestPrivilegeList.add(privilege);
                }
            }
        }
        return requestPrivilegeList;
    }

    /**
     * 根据用户id获取角色列表
     *
     * @param userid
     * @return
     */
    public List<Role> getUserAllRoles(long userid) {
        List<UserRole> usersRolesList = userRoleDao.findByUserid(userid);
        List<Role> roleList = new ArrayList<>();
        for (UserRole usersRoles : usersRolesList) {
            Role role = roleDao.findById(usersRoles.getRole_id());
            roleList.add(role);
        }
        return roleList;
    }

    @Override
    public UserMsgDto loginService(String username, String password) {
        UserMsgDto userMsgDto = null;

        // 如果账号密码都对则返回登录的用户对象，若有一个错误则返回null
        Users user = userDao.findByUsername(username);
//        Users user = userDao.findByUsernameAndPassword(username, password);

        if (user != null) {
            if (encoding.matches(password, user.getPassword())) {
                // 重要信息置空
                user.setPassword("");

                List<Role> roleList = getUserAllRoles(user.getUser_id());
                List<Privilege> privilegeList = getAllPrivilegesByRole(roleList);
                List<User_Address> userAddressList = userAddressService.findAddressService(user.getUser_id());
                userMsgDto = new UserMsgDto(user, roleList, privilegeList,userAddressList);
            } else {
                userMsgDto = null;
            }
        }


        return userMsgDto;
    }

    @Override
    public Users registService(UserMsgDto userMsg) {
        //当新用户的用户名已存在时
        if (userDao.findByUsername(userMsg.getUsername()) != null) {
            // 无法注册
            return null;
        } else {
            userMsg.setPassword(encoding.encode(userMsg.getPassword()));

            //返回创建好的用户对象(带uid)
            Users user = userMsg.getUser();
            userDao.save(user);
            if (user != null) {
                user.setPassword("");
            }
            return user;
        }
    }

    @Override
    public List<Users> getUsersListService(String specificUsers) {
        List<Users> usersList = userDao.findSpecificUserList(specificUsers);
        for (Users users : usersList) {
            users.setPassword("");
        }
        return usersList;
    }

    @Override
    public List<Role> addUsersRoleList(long user_id, List<Long> roleIdList) {
        List<UserRole> userRoleList = new LinkedList<>();
        List<Role> roleList = roleDao.findAllRole();
        List<Long> isExistRoleId = new LinkedList<>(roleIdList);
        List<UserRole> isExistUserRoleList = userRoleDao.findAllUserRole();
        UserRole userRole = null;

        for (Role role : roleList) {
            for (Long roleId : roleIdList) {
                if (role.getRole_id() == roleId) {
                    isExistRoleId.remove(roleId);
                }
            }
        }

        //判断为该用户添加的角色在role表中是否存在（不允许user_role中有role没有的角色）
        if (isExistRoleId.isEmpty()) {
            for (Long roleId : roleIdList) {
                userRole = new UserRole();
                userRole.setRole_id(roleId);
                userRole.setUser_id(user_id);
                System.out.println(roleId);
                userRoleList.add(userRole);
            }
            //删除该用户原来的角色
            userRoleDao.deleteUserRoleByUserId(user_id);

            if (userRoleDao.insertUserRole(userRoleList)) {
                return roleList;
            } else {
                return null;
            }
        } else {
            return null;
        }
    }
}
