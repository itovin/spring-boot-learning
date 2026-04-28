package springbootlearning.ecommerce.services;

import lombok.AllArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import springbootlearning.ecommerce.dtos.CartDto;
import springbootlearning.ecommerce.entities.Cart;
import springbootlearning.ecommerce.entities.Order;
import springbootlearning.ecommerce.entities.User;
import springbootlearning.ecommerce.exceptions.UserNotFoundException;
import springbootlearning.ecommerce.mappers.ProductMapper;
import springbootlearning.ecommerce.repositories.OrderRepository;

import java.util.List;
import java.util.UUID;

@AllArgsConstructor
@Service
public class OrderService {
    private final OrderRepository orderRepository;
    private final OrderItemService orderItemService;
    private final UserService userService;
    private ProductMapper productMapper;
    private CartService cartService;

    public void createOrder(String usernameOrEmail){
        User user = userService.getUser(usernameOrEmail).orElseThrow(() -> new UserNotFoundException("User not found."));
        Long userId = user.getId();
        List<Cart> cartItems = cartService.getAllCartItemsOfUser(userId);
        Order order = new Order(UUID.randomUUID(), user);
        order.setOrderItemList(cartItems.stream()
                .map(item -> productMapper.cartItemToOrderItem(order, item))
                .toList());
        save(order);
        System.out.println(order.getGrandTotal());

    }

    public void save(Order order){
        orderRepository.save(order);
    }

}
