package ra.security.model.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ra.security.model.domain.Coupon;
import ra.security.model.domain.Shipment;
import ra.security.model.domain.Users;

import java.util.Date;


@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class OrdersRequest {

    private Coupon coupon;

    private double total_price;

    private String eDelivered;

    private Shipment shipment;
    private Users users;
    private Date order_at;

    private boolean status;

}
