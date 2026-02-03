package com.learn.demo.dto;

import java.math.BigDecimal;

public class OrderResponse {

    private Long id;
    private BigDecimal total;
    private int userId;
    private String userName;

    public OrderResponse(Long id, BigDecimal total, int userId, String userName) {
        this.id = id;
        this.total = total;
        this.userId = userId;
        this.userName = userName;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
