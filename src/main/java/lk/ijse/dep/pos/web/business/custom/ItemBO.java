package lk.ijse.dep.pos.web.business.custom;

import lk.ijse.dep.pos.web.business.SuperBO;
import lk.ijse.dep.pos.web.dto.ItemDTO;

import java.sql.SQLException;
import java.util.List;

public interface ItemBO extends SuperBO {
    String generateNewItemId() throws SQLException;
    List<ItemDTO> getAllItems() throws Exception;
    void saveItem(String code, String description, double unitPrice, int qtyOnHand) throws Exception;
    void updateItem(String code, String description, double unitPrice, int qtyOnHand) throws Exception;
    void deleteItem(String code) throws Exception;
    ItemDTO getItem(String id) throws Exception;
    boolean itemExist(String id);

}
