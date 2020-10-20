package lk.ijse.dep.pos.web.business.custom;

import lk.ijse.dep.pos.web.business.SuperBO;
import lk.ijse.dep.pos.web.dto.CustomerDTO;

import java.sql.SQLException;
import java.util.List;

public interface CustomerBO extends SuperBO {
    List<CustomerDTO> getAllCustomers() throws Exception;
    void saveCustomer(String id, String name, String address) throws Exception;
    void updateCustomer(String id, String name, String address) throws Exception;
    void deleteCustomer(String id) throws Exception;
//    String generateNewCustomerId() throws SQLException;
    CustomerDTO getCustomer(String id) throws Exception;
    boolean customerExist(String id);
}
