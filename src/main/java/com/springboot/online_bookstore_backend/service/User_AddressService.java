package com.springboot.online_bookstore_backend.service;

import com.springboot.online_bookstore_backend.controller.dto.UserMsgDto;
import com.springboot.online_bookstore_backend.controller.dto.newAddressDto;
import com.springboot.online_bookstore_backend.domain.User_Address;

import java.util.List;

public interface User_AddressService {
    /**
     * 获取该用户所有地址
     * @param userId
     * @return
     */
    List<User_Address> findAddressService(long userId);

    /**
     *添加地址
     * @param userMsg
     * @param newAddress
     * @return
     */
    String  addAddress(UserMsgDto userMsg, newAddressDto newAddress);

    /**
     * 修改地址
     * @param userMsg
     * @param newAddress
     * @return
     */
    String  updateAddress(UserMsgDto userMsg,newAddressDto newAddress);

    /**
     * 删除地址
     * @param addrId
     * @return
     */
    String  deleteAddress(long addrId);

    /**
     * 设置默认地址
     * @param addrId
     * @param userId
     * @return
     */
    String  setDefaultAddress(long addrId,long userId);

}
