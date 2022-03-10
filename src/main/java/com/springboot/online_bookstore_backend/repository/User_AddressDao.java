package com.springboot.online_bookstore_backend.repository;

import com.springboot.online_bookstore_backend.domain.User_Address;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository("user_addressDao")
public interface User_AddressDao {
    List <User_Address> findByUserId(long user_id);//根据user_id查找
    List <User_Address> findByAddrId(long addr_id);//根据addr_id查找
    boolean checkDefaultByAddrId(long addr_id);//判断前端发来的addr_id对应地址是否为默认地址
    boolean setDefaultByAddrId(long addr_id);//根据前端发来的addr_id将对应地址修改为默认地址
    boolean serNotDefaultByUserId(long user_id);//根据已经登陆的user_id将原来的默认地址修改为非默认
    void insert(User_Address userAddress);//
    void update(User_Address userAddress);//
    void delete(long addrId);//
}
