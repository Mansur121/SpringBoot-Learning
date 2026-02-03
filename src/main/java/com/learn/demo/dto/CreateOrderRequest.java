package com.learn.demo.dto;

import java.math.BigDecimal;

public class CreateOrderRequest {

    private int userId;
    private BigDecimal total;

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }
}
