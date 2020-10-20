package lk.ijse.dep.pos.web.repository.custom;

import lk.ijse.dep.pos.web.entity.Customer;
import lk.ijse.dep.pos.web.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.sql.SQLException;

public interface CustomerRepository extends JpaRepository<Customer,String> {
    Customer getFirstLastCustomerIdByOrderByIdDesc() throws SQLException;
}
