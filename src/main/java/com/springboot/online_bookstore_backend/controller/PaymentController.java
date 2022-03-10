package com.springboot.online_bookstore_backend.controller;

import com.springboot.online_bookstore_backend.controller.dto.UserMsgDto;
import com.springboot.online_bookstore_backend.service.OrdersService;
import com.springboot.online_bookstore_backend.utils.MyProps;
import com.springboot.online_bookstore_backend.utils.QRCodeUtil;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.OutputStream;


@RestController
@RequestMapping("/payment")
public class PaymentController {
    @Resource
    private MyProps myProps;

    @Resource
    private OrdersService ordersService;

    @GetMapping("/createQrCode/{order_id}")
    public void createQrCode(@PathVariable long order_id, HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession();
        UserMsgDto userMsg = (UserMsgDto)session.getAttribute("userMsg");
        System.out.println(order_id);

//        StringBuffer url = request.getRequestURL();

//        String url = "http://172.30.71.244:8081/payment/success/" + String.valueOf(userMsg.getUser_id()) + "/" + String.valueOf(order_id);
        String url = "http://"+myProps.getDomainname()+":"+myProps.getPort()+"/payment/success/" + String.valueOf(userMsg.getUser_id()) + "/" + String.valueOf(order_id);

        try {
            OutputStream os = response.getOutputStream();
            //从配置文件读取需要生成二维码的连接
//            String requestUrl = GraphUtils.getProperties("requestUrl");
            //requestUrl:需要生成二维码的连接，logoPath：内嵌图片的路径，os：响应输出流，needCompress:是否压缩内嵌的图片

            QRCodeUtil.encode(url, "/static/images/logo.png", os, true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @GetMapping("/success/{user_id}/{order_id}")
    public String getPaymentPage(@PathVariable long user_id, @PathVariable long order_id) {

        if(ordersService.isOrderPaymentDelay(order_id)){
            ordersService.updateOrderStatus(order_id,4);
            return "用户" + String.valueOf(user_id)+"，您的订单" + String.valueOf(order_id)+" 已超过支付时间，请重新下单！";
        }

        if(!ordersService.updateOrderStatus(order_id,1)){
            return "支付失败！";
        }else{
            return "用户" + String.valueOf(user_id)+"，您的订单" + String.valueOf(order_id)+" 支付成功！";
        }
    }


    /**
     * 测试二维码，可删
     * @param request
     * @param response
     */
    @GetMapping("/testQrCode/{order_id}")
    public void testQrCode(@PathVariable long order_id, HttpServletRequest request, HttpServletResponse response) {
        String url = "http://192.168.0.110:8081/payment/success/1431/"+String.valueOf(order_id);

        try {
            OutputStream os = response.getOutputStream();
            //从配置文件读取需要生成二维码的连接
//            String requestUrl = GraphUtils.getProperties("requestUrl");
            //requestUrl:需要生成二维码的连接，logoPath：内嵌图片的路径，os：响应输出流，needCompress:是否压缩内嵌的图片

            QRCodeUtil.encode(url, "/static/images/logo.png", os, true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
