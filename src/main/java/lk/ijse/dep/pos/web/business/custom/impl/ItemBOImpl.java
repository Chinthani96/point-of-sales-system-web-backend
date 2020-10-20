package lk.ijse.dep.pos.web.business.custom.impl;

import lk.ijse.dep.pos.web.business.custom.ItemBO;
import lk.ijse.dep.pos.web.repository.custom.ItemRepository;
import lk.ijse.dep.pos.web.entity.Item;
import lk.ijse.dep.pos.web.dto.ItemDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Component
@Transactional
public class ItemBOImpl implements ItemBO {
    @Autowired
    private ItemRepository itemRepository;

    @Transactional(readOnly = true)
    public List<ItemDTO> getAllItems() throws Exception {

        List<Item> allItems = null;
        List<ItemDTO> items = new ArrayList<>();
        allItems = itemRepository.findAll();
        for (Item item : allItems) {
            items.add(new ItemDTO(item.getCode(), item.getDescription(), item.getUnitPrice(), item.getQtyOnHand()));
        }
        return items;
    }

    public void saveItem(String code, String description, double unitPrice, int qtyOnHand) throws Exception {
        itemRepository.save(new Item(code, description, BigDecimal.valueOf(unitPrice), qtyOnHand));
    }

    public void updateItem(String code, String description, double unitPrice, int qtyOnHand) throws Exception {
        itemRepository.save(new Item(code, description, BigDecimal.valueOf(unitPrice), qtyOnHand));
    }

    public void deleteItem(String code) throws Exception {
        itemRepository.deleteById(code);
    }

    @Override
    public ItemDTO getItem(String id) throws Exception {
        Item item = itemRepository.findById(id).get();
        return new ItemDTO(item.getCode(),item.getDescription(),item.getUnitPrice(),item.getQtyOnHand());
    }

    @Override
    public boolean itemExist(String id) {
        try {
            itemRepository.findById(id).get();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Transactional(readOnly = true)
    public String generateNewItemId() throws SQLException {
        String lastItemId = itemRepository.getFirstLastItemCodeByOrderByCodeDesc().getCode();
        int lastNumber = Integer.parseInt(lastItemId.substring(1, 4));
        if (lastNumber == 0) {
            lastNumber++;
            return "I001";
        } else if (lastNumber < 9) {
            lastNumber++;
            return "I00" + lastNumber;
        } else if (lastNumber < 99) {
            lastNumber++;
            return "I0" + lastNumber;
        } else {
            lastNumber++;
            return "I" + lastNumber;
        }
    }
}
