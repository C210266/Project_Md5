package ra.security.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ra.security.exception.*;
import ra.security.model.domain.EDelivered;

import ra.security.model.dto.response.OrdersResponse;

import ra.security.service.impl.CartService;
import ra.security.service.impl.OrderService;

import javax.servlet.http.HttpSession;
import java.util.List;


@RestController
@RequestMapping("/api/v4/auth/order")
@CrossOrigin("*")
public class OrderController {
    @Autowired
    private OrderService orderService;
    @Autowired
    private CartService cartService;

    @PostMapping("/buy/shipment/{shipmentId}")
    public ResponseEntity<OrdersResponse> order(HttpSession session, @PathVariable Long shipmentId)
            throws Exception {
        return new ResponseEntity<>(orderService.order(session.getAttribute("CurrentUser"), shipmentId), HttpStatus.OK);
    }

    @PutMapping("/changeDelivery/{orderId}")
    public ResponseEntity<OrdersResponse> changeDelivery(@PathVariable Long orderId,
                                                         @RequestBody EDelivered status) throws CustomException {
        return new ResponseEntity<>(orderService.changeDelivery(status, orderId), HttpStatus.OK);
    }

    @GetMapping("/getAll")
    public ResponseEntity<List<OrdersResponse>> getOrder(HttpSession session) {
        return new ResponseEntity<>(orderService.showOrders(session.getAttribute("CurrentUser")), HttpStatus.OK);
    }
}
