package com.springboot.online_bookstore_backend.controller;

import com.springboot.online_bookstore_backend.controller.dto.*;
import com.springboot.online_bookstore_backend.domain.*;
import com.springboot.online_bookstore_backend.service.*;
import com.springboot.online_bookstore_backend.utils.QRCodeUtil;
import com.springboot.online_bookstore_backend.utils.Result;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.OutputStream;
import java.util.LinkedList;
import java.util.List;

@RestController
@RequestMapping("/user/order")
public class OrderController {

    @Resource
    private OrdersService ordersService;

    @Resource
    private User_AddressService userAddressService;

    @Resource
    private BookInfoService bookInfoService;

    @Resource
    private UserBookRatingService userBookRatingService;

    @Resource
    private CommentService commentService;

    //  实现一个复杂查询，能根据
    //  searchAll（默认值false）、
    //  status订单状态（默认值为-1）、
    //  keyword（默认值为 ”“）获取**指定用户**订单的对应列表
    @GetMapping("/search")
    public Result searchOrders(@RequestParam (value = "searchAll",required = false,defaultValue = "false")  boolean searchAll,
                                             @RequestParam (value = "status",required = false,defaultValue = "-1") int status ,
                                             @RequestParam (value = "keyword",required = false,defaultValue = "") String keyword,
                                             @RequestParam (required = false, defaultValue = "1") int pageNum,
                                             @RequestParam (required = false, defaultValue = "10") int pageSize,
                                             HttpServletRequest request){
        System.out.println("searchAll = "+searchAll);
        System.out.println("status = "+status);
        System.out.println("keyword = "+keyword);

        HttpSession session = request.getSession();
        UserMsgDto userMsg = (UserMsgDto)session.getAttribute("userMsg");

        boolean isAdmin = true;

//        for(Privilege privilege: userMsg.getPrivilegeList()){
//            System.out.println("privilege = "+privilege.getDescription());
//            if (privilege.getDescription().equals("系统管理员")){//判断是否为系统管理员
//                isAdmin = true;
//            }
//        }

        List<Orders> ordersList = new LinkedList<>();

        if (searchAll && isAdmin){//返回数据库所有订单（系统管理员）

            if(status == -1){
                if(keyword.equals("")){ //返回系统所有订单
                    ordersList = ordersService.findAllOrdersService();
                    if (ordersList.isEmpty()) return Result.error("3300","系统无订单");
                    return Result.success(ordersService.converListToPage(ordersList, pageNum, pageSize));
                }
                else {//根据关键词查找某用户所有状态下订单
                    if (ordersService.findKeywordOrderService(keyword).isEmpty()){
                        return Result.error("3305","订单不存在");
                    }
                    ordersList = ordersService.findKeywordOrderService(keyword);
                    return Result.success(ordersService.converListToPage(ordersList, pageNum, pageSize));
                }



            }else if(status != -1){//
                if(keyword.equals("")){ //返回对应状态下所有订单
                    ordersList = ordersService.findStatusOrderService(status);
                    if (ordersList.isEmpty()) return Result.error("3600","此状态下无订单");
                    return Result.success(ordersService.converListToPage(ordersList, pageNum, pageSize));
                }
                else {//根据关键词查找某状态下订单
                    if(ordersService.findKeywordStatusOrderService(status,keyword).isEmpty()){
                        return Result.error("3307","该状态下订单不存在");
                    }
                    ordersList = ordersService.findKeywordStatusOrderService(status,keyword);
                    return Result.success(ordersService.converListToPage(ordersList, pageNum, pageSize));
                }


            }

        }else if(searchAll && !isAdmin){
            return Result.error("788","非系统管理员，操作失败");
        }
        else if(status == -1){//用户操作
            if(keyword.equals("")){ //返回某用户所有订单
                ordersList =  ordersService.findUserAllOrdersService(userMsg.getUser_id());
                if (ordersList.isEmpty()) return Result.error("7300","此用户无订单");
                return Result.success(ordersService.converListToPage(ordersList, pageNum, pageSize));
            }
            else {//根据关键词查找某用户所有状态下订单
                if (ordersService.findUserKeywordOrderService(keyword,userMsg.getUser_id()).isEmpty()){
                    return Result.error("335","用户此订单不存在");
                }
                ordersList = ordersService.findUserKeywordOrderService(keyword,userMsg.getUser_id());
                return Result.success(ordersService.converListToPage(ordersList, pageNum, pageSize));
            }

        }else if(status != -1){//
            if(keyword.equals("")){ //返回某用户对应状态下所有订单
                ordersList = ordersService.findUserStatusOrderService(status,userMsg.getUser_id());
                if (ordersList.isEmpty()) return Result.error("3301","该用户此状态无订单");
                return Result.success(ordersService.converListToPage(ordersList, pageNum, pageSize));
            }
            else {//根据关键词查找某状态下订单
                if(ordersService.findUserKeywordStatusOrderService(status,keyword,userMsg.getUser_id()).isEmpty()){
                    return Result.error("337","该状态下用户订单不存在");
                }
                ordersList = ordersService.findUserKeywordStatusOrderService(status,keyword,userMsg.getUser_id());
                return Result.success(ordersService.converListToPage(ordersList, pageNum, pageSize));
            }
        }

        return Result.error("999","输入参数有误！");
    }

    // 生成订单，状态是待付款，返回支付链接
    @PostMapping("/generateOrder")
    public Result<GeneratedOrderDto> generateOrder(@RequestBody OrderDto orderDto, HttpServletRequest request){
        HttpSession session = request.getSession();
        UserMsgDto userMsg = (UserMsgDto)session.getAttribute("userMsg");

        List<User_Address> userAddressList = userAddressService.findAddressService(userMsg.getUser_id());
        if(userAddressList == null){
            return Result.error("425","请先添加地址！");
        }
        if(bookInfoService.isOutOfStock(orderDto.getOrderDetailList())){
            return Result.error("853","存在商品库存不足！");
        }
        GeneratedOrderDto requestGeneratedOrderDto = ordersService.generateOrders(userMsg.getUser_id(), orderDto);
        return Result.success(requestGeneratedOrderDto,"订单创建成功！");
    }

    // 支付订单，状态改为待发货
    // 实现见PaymentController

    // 确认收货，状态改为待评价
    @PostMapping("/confirmReceipt")
    public Result confirmReceipt(@RequestParam long order_id){
        if(ordersService.updateOrderStatus(order_id,3)){
            return Result.success("收货完成！");
        }else{
            return Result.error("582", "订单还未到待收货状态！");
        }
    }

    // 取消订单，状态改为已取消
    @PostMapping("/cancelOrder")
    public Result cancelOrder(@RequestParam long order_id){
        if(ordersService.updateOrderStatus(order_id,4)){
            return Result.success("订单已取消！");
        }else{
            return Result.error("647", "订单已是取消状态！");
        }
    }

    // 发货，状态改为待收货
    @PostMapping("/ship")
    public Result ship(@RequestParam long order_id, @RequestParam String shipping_name, @RequestParam String shipping_code){

        if(ordersService.canShip(order_id)){
            ordersService.ship(order_id,shipping_name,shipping_code);
            ordersService.updateOrderStatus(order_id,2);
            return Result.success("发货成功！");
        }else{
            return Result.error("948", "不是待发货状态");
        }

    }

    @GetMapping("/getOrder")
    public Result<GeneratedOrderDto> getOrder(@RequestParam long order_id, HttpServletRequest request){
        HttpSession session = request.getSession();
        UserMsgDto userMsg = (UserMsgDto)session.getAttribute("userMsg");
        GeneratedOrderDto generatedOrderDto = ordersService.getUsersOrder(userMsg.getUser_id(), order_id);

        if(generatedOrderDto == null){
            return Result.error("4259","订单不存在！");
        }else{
            return Result.success(generatedOrderDto,"成功获取订单！");
        }
    }

    @PostMapping("/comment")
    public Result commentAndRate(@RequestBody OrderCommentRateDto orderCommentRateDto, HttpServletRequest request){
        HttpSession session = request.getSession();
        UserMsgDto userMsg = (UserMsgDto)session.getAttribute("userMsg");

        System.out.println(orderCommentRateDto.getOrder_id());
        System.out.println(orderCommentRateDto.getCommentAndRatesList().size());
        System.out.println(userMsg);


        for(CommentAndRateDto commentAndRateDto : orderCommentRateDto.getCommentAndRatesList()){

            User_Book_Ratings userBookRatings = new User_Book_Ratings();
            System.out.println(userMsg.getUser_id());
            userBookRatings.setUser_id(userMsg.getUser_id());
            userBookRatings.setBook_id(commentAndRateDto.getBook_id());
            userBookRatings.setRate(commentAndRateDto.getRate());
            userBookRatingService.insertUserBookRate(userBookRatings);

            if(commentAndRateDto.getComment() != ""){
                Comment comment = new Comment();

                comment.setBook_id(commentAndRateDto.getBook_id());
                comment.setContent(commentAndRateDto.getComment());
                comment.setUser_id(userMsg.getUser_id());
                comment.setNickname(userMsg.getNickname());

                List<Comment> commentList = commentService.insertCommentService(comment);
            }
        }
        ordersService.updateOrderStatus(orderCommentRateDto.getOrder_id(),5);
        return Result.success("评价成功！");
    }


}
