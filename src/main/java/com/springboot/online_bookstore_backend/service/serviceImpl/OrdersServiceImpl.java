package com.springboot.online_bookstore_backend.service.serviceImpl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.springboot.online_bookstore_backend.controller.dto.GeneratedOrderDto;
import com.springboot.online_bookstore_backend.controller.dto.OrderDto;
import com.springboot.online_bookstore_backend.controller.dto.OrderItemDto;
import com.springboot.online_bookstore_backend.domain.Book_Info;
import com.springboot.online_bookstore_backend.domain.Order_Detail;
import com.springboot.online_bookstore_backend.domain.Orders;
import com.springboot.online_bookstore_backend.domain.User_Address;
import com.springboot.online_bookstore_backend.repository.Book_InfoDao;
import com.springboot.online_bookstore_backend.repository.Order_DetailDao;
import com.springboot.online_bookstore_backend.repository.OrdersDao;
import com.springboot.online_bookstore_backend.repository.User_AddressDao;
import com.springboot.online_bookstore_backend.service.OrdersService;
import com.springboot.online_bookstore_backend.utils.Tools;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

@Service
public class OrdersServiceImpl implements OrdersService {
    @Resource
    private OrdersDao ordersDao;
    @Resource
    private Book_InfoDao bookInfoDao;
    @Resource
    private Order_DetailDao orderDetailDao;
    @Resource
    private User_AddressDao userAddressDao;

    @Override
    public GeneratedOrderDto generateOrders(long user_id, OrderDto orderDto) {
        String order_number = Tools.getRandomString(8);
        String buyer_message = orderDto.getBuyer_message();
        double post_fee = Math.random() * 20;
        double payment = 0;
        int payment_type = orderDto.getPayment_type();


        // 获取订单书籍列表
        List<Book_Info> orderBookInfoList = bookInfoDao.findByOrderDetailList(orderDto.getOrderDetailList());
        int item_amount = orderBookInfoList.size();
        int status = 0;

        Date payment_ddl_time = Tools.getTimeMinAfter(1);

        for (Book_Info item : orderBookInfoList) {
            payment += item.getPrice();
        }

        User_Address address = new User_Address();
        User_Address defaultAddr = new User_Address();
        List<User_Address> userAddressList = userAddressDao.findByUserId(user_id);
        for (User_Address userAddress : userAddressList) {
            if (userAddress.getAddr_id() == orderDto.getAddr_id()) {
                address = userAddress;
            }
            if (userAddress.isDefault_addr()) {
                defaultAddr = userAddress;
            }
        }

        if (address.getAddr_id() == 0) {
            address = defaultAddr;
        }

        Orders newOrder = new Orders();
        newOrder.setUser_id(user_id);
        newOrder.setOrder_number(order_number);
        newOrder.setBuyer_message(buyer_message);
        newOrder.setPost_fee(post_fee);
        newOrder.setPayment(payment);
        newOrder.setPayment_type(payment_type);
        newOrder.setItem_amount(item_amount);
        newOrder.setStatus(status);
        newOrder.setPayment_ddl_time(payment_ddl_time);
        newOrder.setCountry(address.getCountry());
        newOrder.setReceiver_state(address.getReceiver_state());
        newOrder.setReceiver_city(address.getReceiver_city());
        newOrder.setReceiver_district(address.getReceiver_district());
        newOrder.setDetail_address(address.getDetail_address());
        newOrder.setZip_code(address.getZip_code());
        newOrder.setPhone(address.getPhone());
        ordersDao.insertOrder(newOrder);

        List<OrderItemDto> orderItemDtoList = new ArrayList<>();
        for (Order_Detail orderDetail : orderDto.getOrderDetailList()) {
            orderDetail.setOrder_id(newOrder.getOrder_id());
            orderDetailDao.insert(orderDetail);

            //将订单商品信息添加到列表里
            Book_Info bookinfo = bookInfoDao.findBookInfo(orderDetail.getBook_id());
            OrderItemDto orderItem = new OrderItemDto(bookinfo);
            orderItem.setAmount(orderDetail.getAmount());
            orderItemDtoList.add(orderItem);

            // 减少商品库存
            bookInfoDao.decreaseStoreAmount(orderDetail.getBook_id(), orderDetail.getAmount());
        }


        GeneratedOrderDto generatedOrderDto = new GeneratedOrderDto();
        generatedOrderDto.setInitOrder(newOrder);
        generatedOrderDto.setOrderItemList(orderItemDtoList);


        return generatedOrderDto;
    }

    @Override
    public List<Orders> findAllOrdersService() {
        return ordersDao.findAll();
    }

    @Override
    public List<Orders> findUserAllOrdersService(long userId) {

        return ordersDao.findByUserId(userId);
    }

    @Override
    public List<Orders> findUserStatusOrderService(int status, long userId) {

        return ordersDao.findByUserIdAndStatus(status, userId);
    }

    @Override
    public List<Orders> findStatusOrderService(int status) {

        return ordersDao.findByStatus(status);
    }

    @Override
    public List<Orders> findUserKeywordOrderService(String keyword, long userId) {
        System.out.println("bookIdList1 = " + bookInfoDao.fuzzyFindBookName(keyword));
        List<Long> bookIdList = bookInfoDao.fuzzyFindBookName(keyword);
        List<Long> orderIdList = new LinkedList<>();
        List<Orders> ordersList = ordersDao.findByUserIdAndKeyword(userId, keyword);//先把对订单号搜索结果存在ordersList中

        for (long bookId : bookIdList) {
            //在订单明细表中找到对应的orderId并添加至orderIdList，orderIdList表示含有书名包含关键字的订单的orderid集合
            for (long orderId : orderDetailDao.findOrderIdByBookId(bookId)) {
                if (!orderIdList.contains(orderId)) {//对orderIdList的去重
                    orderIdList.add(orderId);
                }
            }
        }
        System.out.println("orderIdList1 = " + orderIdList);


        //根据当前登录用户在订单表中找到属于登录用户的ordersLiST
        for (long orderId : orderIdList) {
            if (ordersDao.findByUserIdAndOrderId(userId, orderId) != null) {//判断订单表是否存在对应订单
                if (!ordersList.contains(ordersDao.findByUserIdAndOrderId(userId, orderId))) {//对ordersList进行去重
                    ordersList.add(ordersDao.findByUserIdAndOrderId(userId, orderId));
                }

            }
        }

        return ordersList;
        //return ordersDao.findByUserIdAndKeyword(userId,keyword);
    }

    @Override
    public List<Orders> findKeywordOrderService(String keyword) {
        System.out.println("bookIdList1 = " + bookInfoDao.fuzzyFindBookName(keyword));
        List<Long> bookIdList = bookInfoDao.fuzzyFindBookName(keyword);
        List<Long> orderIdList = new LinkedList<>();
        List<Orders> ordersList = ordersDao.findByKeyword(keyword);//先把对订单号搜索结果存在ordersList中

        for (long bookId : bookIdList) {
            //在订单明细表中找到对应的orderId并添加至orderIdList，orderIdList表示含有书名包含关键字的订单的orderid集合
            for (long orderId : orderDetailDao.findOrderIdByBookId(bookId)) {
                if (!orderIdList.contains(orderId)) {//对orderIdList的去重
                    orderIdList.add(orderId);
                }
            }
        }
        System.out.println("orderIdList1 = " + orderIdList);


        //根据当前登录用户在订单表中找到属于登录用户的ordersLiST
        for (long orderId : orderIdList) {
            if (ordersDao.findByOrderId(orderId) != null) {//判断订单表是否存在对应订单
                if (!ordersList.contains(ordersDao.findByOrderId(orderId))) {//对ordersList进行去重
                    ordersList.add(ordersDao.findByOrderId(orderId));
                }

            }
        }

        return ordersList;
    }

    @Override
    public List<Orders> findUserKeywordStatusOrderService(int status, String keyword, long userId) {
        System.out.println("bookIdList2 = " + bookInfoDao.fuzzyFindBookName(keyword));
        List<Long> bookIdList = bookInfoDao.fuzzyFindBookName(keyword);
        List<Long> orderIdList = new LinkedList<>();
        List<Orders> ordersList = ordersDao.findByUserIdAndKeywordAndStatus(userId, keyword, status);//先把对订单号搜索结果存在ordersList中

        for (long bookId : bookIdList) {
            //在订单明细表中找到对应的orderId并添加至orderIdList，orderIdList表示含有书名包含关键字的订单的orderid集合
            for (long orderId : orderDetailDao.findOrderIdByBookId(bookId)) {
                if (!orderIdList.contains(orderId)) {//对orderIdList的去重
                    orderIdList.add(orderId);
                }
            }
        }
        System.out.println("orderIdList2 = " + orderIdList);

        //根据当前登录用户在订单表中找到属于登录用户的ordersList
        for (long orderId : orderIdList) {
            if (ordersDao.findByUserIdAndOrderIdAndStatus(userId, orderId, status) != null) {//判断订单表是否存在对应订单
                if (!ordersList.contains(ordersDao.findByUserIdAndOrderIdAndStatus(userId, orderId, status))) {//对ordersList进行去重
                    ordersList.add(ordersDao.findByUserIdAndOrderIdAndStatus(userId, orderId, status));
                }

            }
        }

        return ordersList;
        //return ordersDao.findByUserIdAndKeywordAndStatus(userId,keyword,status);
    }

    @Override
    public List<Orders> findKeywordStatusOrderService(int status, String keyword) {
        System.out.println("admin bookIdList2 = " + bookInfoDao.fuzzyFindBookName(keyword));
        List<Long> bookIdList = bookInfoDao.fuzzyFindBookName(keyword);
        List<Long> orderIdList = new LinkedList<>();
        List<Orders> ordersList = ordersDao.findByKeywordAndStatus(keyword, status);//先把对订单号搜索结果存在ordersList中

        for (long bookId : bookIdList) {
            //在订单明细表中找到对应的orderId并添加至orderIdList，orderIdList表示含有书名包含关键字的订单的orderid集合
            for (long orderId : orderDetailDao.findOrderIdByBookId(bookId)) {
                if (!orderIdList.contains(orderId)) {//对orderIdList的去重
                    orderIdList.add(orderId);
                }
            }
        }
        System.out.println("admin orderIdList2 = " + orderIdList);

        //根据当前登录用户在订单表中找到属于登录用户的ordersList
        for (long orderId : orderIdList) {
            if (ordersDao.findByOrderIdAndStatus(orderId, status) != null) {//判断订单表是否存在对应订单
                if (!ordersList.contains(ordersDao.findByOrderIdAndStatus(orderId, status))) {//对ordersList进行去重
                    ordersList.add(ordersDao.findByOrderIdAndStatus(orderId, status));
                }

            }
        }

        return ordersList;
    }

    @Override
    public boolean updateOrderStatus(long order_id, int status) {
        Orders order = ordersDao.findByOrderId(order_id);

        if (status <= order.getStatus()) {
            return false;
        }
//        if (status != 4 && status - order.getStatus() > 1) {
//            return false;
//        }

        ordersDao.updateStatus(order_id, status);


        // 取消订单商品库存要恢复
        if (status == 4) {
            List<Order_Detail> orderItemsList = orderDetailDao.findByOrderId(order_id);
            for (Order_Detail orderDetail : orderItemsList) {
                bookInfoDao.increaseStoreAmount(orderDetail.getBook_id(), orderDetail.getAmount());
            }
        }

        return true;

    }

    @Override
    public boolean isOrderPaymentDelay(long order_id) {
        Orders order = ordersDao.findByOrderId(order_id);
        if (order.getStatus() == 0) {
            return Tools.isDelay(order.getPayment_ddl_time());
        } else {
            return false;
        }

    }

    @Override
    public void ship(long order_id, String shipping_name, String shipping_code) {
        ordersDao.updateShippingMsg(order_id, shipping_name, shipping_code);
    }

    @Override
    public boolean canShip(long order_id) {
        if (ordersDao.findByOrderId(order_id).getStatus() == 1) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public PageInfo<GeneratedOrderDto> converListToPage(List<Orders> ordersList, int pageNum, int pageSize) {
        List<GeneratedOrderDto> generatedOrderDtoList = new ArrayList<>();

        for (Orders order : ordersList) {
            List<Order_Detail> orderDetailList = orderDetailDao.findByOrderId(order.getOrder_id());

            List<OrderItemDto> orderItemDtoList = new ArrayList<>();
            for (Order_Detail orderDetail : orderDetailList) {
                //将订单商品信息添加到列表里
                Book_Info bookinfo = bookInfoDao.findBookInfo(orderDetail.getBook_id());
                OrderItemDto orderItem = new OrderItemDto(bookinfo);
                orderItem.setAmount(orderDetail.getAmount());
                orderItemDtoList.add(orderItem);
            }

            GeneratedOrderDto generatedOrderDto = new GeneratedOrderDto();
            generatedOrderDto.setInitOrder(order);
            generatedOrderDto.setOrderItemList(orderItemDtoList);

            generatedOrderDtoList.add(generatedOrderDto);
        }

//        PageHelper.startPage(pageNum, pageSize);
//        PageInfo<GeneratedOrderDto> pageInfo = new PageInfo<GeneratedOrderDto>(generatedOrderDtoList);


        //创建Page类
        Page page = new Page(pageNum, pageSize);
        //为Page类中的total属性赋值
        int total = generatedOrderDtoList.size();
        page.setTotal(total);
        //计算当前需要显示的数据下标起始值
        int startIndex = (pageNum - 1) * pageSize;
        int endIndex = Math.min(startIndex + pageSize, total);
        //从链表中截取需要显示的子链表，并加入到Page
        page.addAll(generatedOrderDtoList.subList(startIndex, endIndex));
        //以Page创建PageInfo
        PageInfo pageInfo = new PageInfo<>(page);

        return pageInfo;
    }

    @Override
    public GeneratedOrderDto getUsersOrder(long user_id, long order_id) {
        Orders order = ordersDao.findByOrderId(order_id);

        if(order == null){
            return null;
        }
        List<Order_Detail> orderDetailList = orderDetailDao.findByOrderId(order.getOrder_id());

        List<OrderItemDto> orderItemDtoList = new ArrayList<>();
        for (Order_Detail orderDetail : orderDetailList) {
            //将订单商品信息添加到列表里
            Book_Info bookinfo = bookInfoDao.findBookInfo(orderDetail.getBook_id());
            OrderItemDto orderItem = new OrderItemDto(bookinfo);
            orderItem.setAmount(orderDetail.getAmount());
            orderItemDtoList.add(orderItem);
        }

        GeneratedOrderDto generatedOrderDto = new GeneratedOrderDto();
        generatedOrderDto.setInitOrder(order);
        generatedOrderDto.setOrderItemList(orderItemDtoList);
        return generatedOrderDto;
    }
}
