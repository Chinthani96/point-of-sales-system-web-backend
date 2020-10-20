package lk.ijse.dep.pos.web.repository.custom;

import lk.ijse.dep.pos.web.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.sql.SQLException;

public interface OrderRepository extends JpaRepository<Order,String> {
    Order getFirstLastOrderIdByOrderByIdDesc() throws SQLException;
}
