package com.springboot.online_bookstore_backend.repository;

import com.springboot.online_bookstore_backend.domain.Order_Detail;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository("order_detailDao")
public interface Order_DetailDao {

    List<Long> findOrderIdByBookId(long book_id);
    void insert(Order_Detail orderDetail);
    Order_Detail findById(long order_detail_id);
    List<Order_Detail> findByOrderId(long order_id);
}
