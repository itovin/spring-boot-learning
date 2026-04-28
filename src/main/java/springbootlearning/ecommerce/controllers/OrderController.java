package springbootlearning.ecommerce.controllers;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;
import springbootlearning.ecommerce.dtos.CartDto;
import springbootlearning.ecommerce.services.OrderItemService;
import springbootlearning.ecommerce.services.OrderService;

@AllArgsConstructor
@RestController
public class OrderController {
    private final OrderService orderService;
    private final OrderItemService orderItemService;

    @PostMapping("/orders")
    public ResponseEntity<?> createOrder(@AuthenticationPrincipal String usernameOrEmail){
        orderService.createOrder(usernameOrEmail);
        return null;
    }
}
