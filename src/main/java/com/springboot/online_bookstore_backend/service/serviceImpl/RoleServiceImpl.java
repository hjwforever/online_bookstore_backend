package com.springboot.online_bookstore_backend.service.serviceImpl;

import com.springboot.online_bookstore_backend.controller.dto.RoleDto;
import com.springboot.online_bookstore_backend.domain.Role;
import com.springboot.online_bookstore_backend.domain.RolePrivilege;
import com.springboot.online_bookstore_backend.domain.UserRole;
import com.springboot.online_bookstore_backend.repository.PrivilegeDao;
import com.springboot.online_bookstore_backend.repository.RoleDao;
import com.springboot.online_bookstore_backend.repository.RolePrivilegeDao;
import com.springboot.online_bookstore_backend.repository.UserRoleDao;
import com.springboot.online_bookstore_backend.service.RoleService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.LinkedList;
import java.util.List;
@Service
public class RoleServiceImpl implements RoleService {
    @Resource
    private RoleDao roleDao;
    @Resource
    private RolePrivilegeDao rolePrivilegeDao;
    @Resource
    private PrivilegeDao privilegeDao;

    @Override
    public List<Role> getAllRoles() {
        return roleDao.findAllRole();
    }

    @Override
    public String addRole(RoleDto newRole) {
        //判断数据库是否已经存在要添加的角色
        if (roleDao.findByRoleName(newRole.getRolename()) != null){
            return "已经存在要添加的角色";
        }


        //判断添加的权限是否存在
        List<Long> privList = newRole.getPrivilegeList();
        for(long priv : privList){
            if (privilegeDao.findById(priv) == null){
                return "所选择权限不存在";
            }
        }

        Role role = new Role();
        role.setRolename(newRole.getRolename());
        role.setDescription(newRole.getDescription());

        //角色表中新增角色
        roleDao.insertRole(role);

        //角色-权限表中添加角色对应权限
        Long roleId = roleDao.findByRoleName(newRole.getRolename()).getRole_id();
        RolePrivilege rolePrivilege = new RolePrivilege();

        for(long priv : privList){
                rolePrivilege.setRole_id(roleId);
                rolePrivilege.setPriv_id(priv);
                rolePrivilegeDao.insertRolePrivilege(rolePrivilege);
        }

        return "成功";
    }

    @Override
    public String editRole(RoleDto newRole) {


        if (roleDao.findById(newRole.getRole_id()) == null){
            return "roleid不存在";
        }

        //判断修改的权限是否存在
        List<Long> privList = newRole.getPrivilegeList();
        for(long priv : privList){
            if (privilegeDao.findById(priv) == null){
                return "所选择权限不存在";
            }
        }

        Role role = new Role();
        role.setRole_id(newRole.getRole_id());
        role.setRolename(newRole.getRolename());
        role.setDescription(newRole.getDescription());

        //角色表中修改角色

        //根据前端发来的角色名rolename，
        //若不能在角色表中找到roleid，
        //若能在角色表中找到roleid，比较前端传来的roleid
        //如果不一致，则修改失败，不能有重复的角色名，一致则进行update
       if(newRole.getRole_id() != roleDao.findRoleIdByRolename(newRole.getRolename())){
            //修改失败，不能有重复的角色名
            return "修改失败，不能有重复的角色名";
        }
        //更新操作
        roleDao.update(role);



        //角色-权限表中添加角色对应权限
        //先把角色权限表中roleid对应的数据删除
        rolePrivilegeDao.deleteRolePrivilege(newRole.getRole_id());
        //再把新的权限列表插入
        Long roleId = roleDao.findByRoleName(newRole.getRolename()).getRole_id();
        RolePrivilege rolePrivilege = new RolePrivilege();

        for(long priv : privList){
            rolePrivilege.setRole_id(roleId);
            rolePrivilege.setPriv_id(priv);
            rolePrivilegeDao.insertRolePrivilege(rolePrivilege);
        }

        return "成功";
    }

}
