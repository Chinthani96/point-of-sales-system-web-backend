package lk.ijse.dep.pos.web.dto;

import java.util.ArrayList;

public class OrderDetail {
    private String orderId;
//    private Date orderDate;
    private ArrayList<OrderDetailsDTO> orderDetails;

    public OrderDetail() {
    }

    public OrderDetail(String orderId, ArrayList<OrderDetailsDTO> orderDetails) {
        this.orderId = orderId;
//        this.orderDate = orderDate;
        this.orderDetails = orderDetails;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }
//
//    public Date getOrderDate() {
//        return orderDate;
//    }
//
//    public void setOrderDate(Date orderDate) {
//        this.orderDate = orderDate;
//    }

    public ArrayList<OrderDetailsDTO> getOrderDetails() {
        return orderDetails;
    }

    public void setOrderDetails(ArrayList<OrderDetailsDTO> orderDetails) {
        this.orderDetails = orderDetails;
    }
}
