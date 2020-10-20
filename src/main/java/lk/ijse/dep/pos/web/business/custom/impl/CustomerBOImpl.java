package lk.ijse.dep.pos.web.business.custom.impl;

import lk.ijse.dep.pos.web.business.custom.CustomerBO;
import lk.ijse.dep.pos.web.repository.custom.CustomerRepository;
import lk.ijse.dep.pos.web.entity.Customer;
import lk.ijse.dep.pos.web.dto.CustomerDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Component
@Transactional
public class CustomerBOImpl implements CustomerBO {

    @Autowired
    private CustomerRepository customerRepository;

    @Transactional(readOnly = true)
    public List<CustomerDTO> getAllCustomers() throws Exception {
        List<Customer> allCustomers = null;
        List<CustomerDTO> customerTMS = new ArrayList<>();
            allCustomers = customerRepository.findAll();
        for (Customer customer : allCustomers) {
            customerTMS.add(new CustomerDTO(customer.getId(), customer.getName(), customer.getAddress()));
        }
        return customerTMS;
    }

    @Override
    public CustomerDTO getCustomer(String id) throws Exception {
        Customer customer = customerRepository.findById(id).get();
        return new CustomerDTO(customer.getId(),customer.getName(),customer.getAddress());
    }

    @Override
    public boolean customerExist(String id){
        try {
            customerRepository.findById(id).get();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public void saveCustomer(String id, String name, String address) throws Exception {
            customerRepository.save(new Customer(id, name, address));

    }

    public void updateCustomer(String id, String name, String address) throws Exception {
        customerRepository.save(new Customer(id, name, address));
    }

    public void deleteCustomer(String id) throws Exception {
            customerRepository.deleteById(id);
    }

//    @Transactional(readOnly = true)
//    public String generateNewCustomerId() throws SQLException {
//            String lastCustomerId = customerRepository.getFirstLastCustomerIdByOrderByIdDesc().getId();
//            int lastNumber = Integer.parseInt(lastCustomerId.substring(1, 4));
//            if (lastNumber == 0) {
//                return "C001";
//            } else if (lastNumber < 9) {
//                lastNumber++;
//                return "C00" + lastNumber;
//            } else if (lastNumber < 99) {
//                lastNumber++;
//                return "C0" + lastNumber;
//            } else {
//                lastNumber++;
//                return "C" + lastNumber;
//            }
//    }
}
