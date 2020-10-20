package lk.ijse.dep.pos.web.dto;

public class Order {
    private OrderDetailsDTO orderId;
    private CustomerDTO customerId;
    private double total;

    public Order() {
    }

    public Order(OrderDetailsDTO orderId, CustomerDTO customerId, double total) {
        this.orderId = orderId;
        this.customerId = customerId;
        this.total = total;
    }

    public OrderDetailsDTO getOrderId() {
        return orderId;
    }

    public void setOrderId(OrderDetailsDTO orderId) {
        this.orderId = orderId;
    }

    public CustomerDTO getCustomerId() {
        return customerId;
    }

    public void setCustomerId(CustomerDTO customerId) {
        this.customerId = customerId;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }
}
