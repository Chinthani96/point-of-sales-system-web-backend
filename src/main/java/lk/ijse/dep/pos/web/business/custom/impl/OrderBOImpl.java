package lk.ijse.dep.pos.web.business.custom.impl;

import lk.ijse.dep.pos.web.business.custom.OrderBO;
import lk.ijse.dep.pos.web.repository.custom.ItemRepository;
import lk.ijse.dep.pos.web.repository.custom.OrderRepository;
import lk.ijse.dep.pos.web.repository.custom.OrderDetailRepository;
import lk.ijse.dep.pos.web.entity.*;
import lk.ijse.dep.pos.web.dto.CustomerDTO;
import lk.ijse.dep.pos.web.dto.SearchOrderDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Component
@Transactional
public class OrderBOImpl implements OrderBO {
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private OrderDetailRepository orderDetailRepository;
    @Autowired
    private ItemRepository itemRepository;

    public void saveOrder(String id, Date date, CustomerDTO customer) throws Exception {
        orderRepository.save(new Order(id, date, new Customer(customer.getId(), customer.getName(), customer.getAddress())));
    }

    public void saveOrderDetail(String orderId, String itemCode, int qty, double unitPrice) throws Exception {
        orderDetailRepository.save(new OrderDetail(orderId, itemCode, qty, BigDecimal.valueOf(unitPrice)));
        Item item = itemRepository.findById(itemCode).get();
        item.setQtyOnHand(item.getQtyOnHand() - qty);
    }

    @Override
    public boolean orderExist(String id) {
        try {
            orderRepository.findById(id).get();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public String generateNewOrderId() throws SQLException {
        String lastOrderId = orderRepository.getFirstLastOrderIdByOrderByIdDesc().getId();

        int lastNumber = Integer.parseInt(lastOrderId.substring(2, 5));
        if (lastNumber <= 0) {
            lastNumber++;
            return "OD001";
        } else if (lastNumber < 9) {
            lastNumber++;
            return "OD00" + lastNumber;
        } else if (lastNumber < 99) {
            lastNumber++;
            return "OD0" + lastNumber;
        } else {
            lastNumber++;
            return "OD" + lastNumber;
        }
    }

    @Transactional(readOnly = true)
    public List<SearchOrderDTO> getOrderDetails() throws Exception {
        List<CustomEntity> orderDetails = null;
        List<SearchOrderDTO> searchOrderTMS = new ArrayList<>();

//        orderDetails = queryDAO.getOrderDetails();
//        for (CustomEntity orderDetail : orderDetails) {
//            searchOrderTMS.add(new SearchOrderTM(orderDetail.getOrderId(), orderDetail.getOrderDate().toString(), orderDetail.getCustomerId(), orderDetail.getCustomerName(), orderDetail.getTotal().doubleValue()));
//            return searchOrderTMS;
//        }
        return null;
    }
}
