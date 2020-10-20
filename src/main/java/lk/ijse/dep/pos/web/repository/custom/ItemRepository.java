package lk.ijse.dep.pos.web.repository.custom;

import lk.ijse.dep.pos.web.entity.Item;
import org.springframework.data.jpa.repository.JpaRepository;

import java.sql.SQLException;

public interface ItemRepository extends JpaRepository<Item,String> {
    Item getFirstLastItemCodeByOrderByCodeDesc() throws SQLException;
}
