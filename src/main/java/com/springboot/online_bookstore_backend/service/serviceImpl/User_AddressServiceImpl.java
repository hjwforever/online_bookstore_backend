package com.springboot.online_bookstore_backend.service.serviceImpl;

import com.springboot.online_bookstore_backend.controller.dto.UserMsgDto;
import com.springboot.online_bookstore_backend.controller.dto.newAddressDto;
import com.springboot.online_bookstore_backend.domain.User_Address;
import com.springboot.online_bookstore_backend.repository.User_AddressDao;
import com.springboot.online_bookstore_backend.service.User_AddressService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class User_AddressServiceImpl implements User_AddressService {
    @Resource
    private User_AddressDao userAddressDao;


    @Override
    public List<User_Address> findAddressService(long userId) {
        List<User_Address> userAddressList = userAddressDao.findByUserId(userId);

        if (userAddressList.isEmpty()){
            return null;
        }
        return userAddressList;
    }

    @Override
    public String  addAddress(UserMsgDto userMsg, newAddressDto newAddress) {

        User_Address userAddress = new User_Address();

        userAddress.setUser_id(userMsg.getUser_id());
        userAddress.setPhone(newAddress.getPhone());
        userAddress.setCountry(newAddress.getCountry());
        userAddress.setReceiver_state(newAddress.getReceiver_state());
        userAddress.setReceiver_city(newAddress.getReceiver_city());
        userAddress.setReceiver_district(newAddress.getReceiver_district());
        userAddress.setDetail_address(newAddress.getDetail_address());
        userAddress.setZip_code(newAddress.getZip_code());

        if(userAddressDao.findByUserId(userMsg.getUser_id()).isEmpty()){//如果该用户暂无地址则新增的地址设置为默认地址，否则设置默认地址为false
            userAddress.setDefault_addr(true);
        }else{
            userAddress.setDefault_addr(false);
        }


        userAddressDao.insert(userAddress);
        return "添加成功";
    }

    @Override
    public String updateAddress(UserMsgDto userMsg,newAddressDto newAddress) {
        User_Address userAddress = new User_Address();

        userAddress.setUser_id(userMsg.getUser_id());
        userAddress.setAddr_id(newAddress.getAddr_id());
        userAddress.setPhone(newAddress.getPhone());
        userAddress.setCountry(newAddress.getCountry());
        userAddress.setReceiver_state(newAddress.getReceiver_state());
        userAddress.setReceiver_city(newAddress.getReceiver_city());
        userAddress.setReceiver_district(newAddress.getReceiver_district());
        userAddress.setDetail_address(newAddress.getDetail_address());
        userAddress.setZip_code(newAddress.getZip_code());

        if (userAddressDao.findByAddrId(newAddress.getAddr_id()).isEmpty()){//
            return "该地址不存在";
        }else {
            userAddressDao.update(userAddress);
            return "更新成功";
        }



    }

    @Override
    public String deleteAddress(long addrId) {
        if (userAddressDao.findByAddrId(addrId).isEmpty()){//
            return "该地址不存在";
        }else if (userAddressDao.checkDefaultByAddrId(addrId)){
            return "该地址是默认地址，请重新选择";
        }else {
            userAddressDao.delete(addrId);
            return "删除成功";
        }

    }

    @Override
    public String setDefaultAddress(long addrId,long userId) {
        if (userAddressDao.findByAddrId(addrId).isEmpty()){//
            return "该地址不存在";
        }else if (userAddressDao.checkDefaultByAddrId(addrId)){
            System.out.println("addrid="+ addrId);
            return "该地址是默认地址，请重新选择";
        }else {
            userAddressDao.serNotDefaultByUserId(userId);
            userAddressDao.setDefaultByAddrId(addrId);
            return "设置默认地址成功";
        }

    }
}
