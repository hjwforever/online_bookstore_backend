package com.springboot.online_bookstore_backend.controller.dto;

import java.util.ArrayList;
import java.util.List;

public class OrderCommentRateDto {
    private long order_id;
    List<CommentAndRateDto> commentAndRatesList;

    public long getOrder_id() {
        return order_id;
    }

    public void setOrder_id(long order_id) {
        this.order_id = order_id;
    }

    public List<CommentAndRateDto> getCommentAndRatesList() {
        return commentAndRatesList;
    }

    public void setCommentAndRatesList(List<CommentAndRateDto> commentAndRatesList) {
        this.commentAndRatesList = commentAndRatesList;
    }
}
