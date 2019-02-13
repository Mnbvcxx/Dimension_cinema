package com.bw.movie.movie.fragment.cinemaActivity.bean;

/**
 * @author: pengbo
 * @date:2019/1/30
 * desc:
 */
public class MoveTicketBean {

    private String orderId;
    private String message;
    private String status;

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
