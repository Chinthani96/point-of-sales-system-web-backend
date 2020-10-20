package lk.ijse.dep.pos.web.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class SearchOrderDTO {
    private String orderId;
    private String date;
    private String customerId;
    private String customerName;
    private double total;
}
