package lk.ijse.dep.pos.web.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class OrderDetailsDTO {
    private String itemId;
    private String description;
    private int qty;
    private double unitPrice;
    private double total;
}
