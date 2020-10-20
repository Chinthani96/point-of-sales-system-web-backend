package lk.ijse.dep.pos.web.business.custom;

import lk.ijse.dep.pos.web.business.SuperBO;
import lk.ijse.dep.pos.web.dto.CustomerDTO;
import lk.ijse.dep.pos.web.dto.SearchOrderDTO;

import java.sql.Date;
import java.sql.SQLException;
import java.util.List;

public interface OrderBO extends SuperBO {
    void saveOrder(String id, Date date, CustomerDTO customer) throws Exception;
    void saveOrderDetail(String orderId, String itemCode, int qty, double unitPrice) throws Exception;
    boolean orderExist(String id);
    String generateNewOrderId() throws SQLException;
    List<SearchOrderDTO> getOrderDetails() throws Exception;
}
