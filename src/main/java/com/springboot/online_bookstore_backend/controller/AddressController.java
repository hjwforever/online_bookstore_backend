package com.springboot.online_bookstore_backend.controller;

import com.springboot.online_bookstore_backend.controller.dto.UserMsgDto;
import com.springboot.online_bookstore_backend.controller.dto.newAddressDto;
import com.springboot.online_bookstore_backend.domain.User_Address;
import com.springboot.online_bookstore_backend.domain.Users;
import com.springboot.online_bookstore_backend.service.User_AddressService;
import com.springboot.online_bookstore_backend.utils.Result;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

@RestController
@RequestMapping("/user/address")
public class AddressController {

    @Resource
    private User_AddressService userAddressService;

    // 获取某个用户的收货地址列表
    @GetMapping("/getList")
    public Result<List<User_Address>> getAddressList(HttpServletRequest request){
        HttpSession session = request.getSession();
        UserMsgDto userMsg = (UserMsgDto)session.getAttribute("userMsg");

        List<User_Address> userAddressList = userAddressService.findAddressService(userMsg.getUser_id());

        if(userAddressList!=null){
            return Result.success(userAddressList,"成功");
        }
        return Result.error("344","当前用户暂无地址");
    }

    // 新建收货地址，可设为默认地址，但要注意数据库中已有默认地址时要先将其改为非默认地址
    @PostMapping("/add")
    public Result addAddress(@RequestBody newAddressDto newAddress,HttpServletRequest request){
        HttpSession session = request.getSession();
        UserMsgDto userMsg = (UserMsgDto)session.getAttribute("userMsg");

        userAddressService.addAddress(userMsg,newAddress);
        return Result.success("成功");
    }

    // 修改收货地址
    @PostMapping("/modify")
    public Result updateAddress(@RequestBody newAddressDto newAddress,HttpServletRequest request){
        HttpSession session = request.getSession();
        UserMsgDto userMsg = (UserMsgDto)session.getAttribute("userMsg");

        if (userAddressService.updateAddress(userMsg,newAddress) == "该地址不存在"){
            return Result.error("88","要修改的地址不存在");
        }
        return Result.success(userAddressService.updateAddress(userMsg,newAddress));
    }

    //  删除收货地址
    @GetMapping("/del")
    public Result deleteAddress(@RequestParam long addr_id, HttpServletRequest request){
        HttpSession session = request.getSession();
        UserMsgDto userMsg = (UserMsgDto)session.getAttribute("userMsg");

        String info = userAddressService.deleteAddress(addr_id);

        if (info.equals("删除成功")){
            return Result.success("删除成功");
        }
        else if (info.equals("该地址是默认地址，请重新选择")){
            return Result.error("828","该地址是默认地址，请重新选择!");
        }
        return Result.error("818","要删除的地址不存在!");

    }

    //  设置某个收货地址为默认地址
    //  注意地址编号可能不存在要返回Result.error("地址编号不存在！")
    @PostMapping("/setdefault")
    public Result setDefaultAddr(@RequestParam long addr_id, HttpServletRequest request){
        HttpSession session = request.getSession();
        UserMsgDto userMsg = (UserMsgDto)session.getAttribute("userMsg");

        String info = userAddressService.setDefaultAddress(addr_id,userMsg.getUser_id());

        if (info.equals("设置默认地址成功")){
            return Result.success("设置默认地址成功");
        }
        else if (info.equals("该地址是默认地址，请重新选择")){
            return Result.error("728","该地址已经是默认地址，请重新选择!");
        }
        return Result.error("718","您选择的地址不存在!");
    }
}
