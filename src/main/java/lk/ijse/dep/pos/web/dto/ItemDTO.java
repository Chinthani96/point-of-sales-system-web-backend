package lk.ijse.dep.pos.web.dto;

import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ItemDTO {
    private String code;
    private String description;
    private BigDecimal unitPrice;
    private int qtyOnHand;
}
