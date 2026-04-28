package springbootlearning.ecommerce.entities;

import jakarta.persistence.*;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@NoArgsConstructor
@Setter
@Entity
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "order_uuid")
    private UUID orderUuid;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    private User user;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    @Column(name = "created_at")
    private Instant createdAt;

    @OneToMany(mappedBy = "order", cascade = CascadeType.PERSIST)
    private List<OrderItem> orderItemList = new ArrayList<>();

    public Order(UUID orderUuid, User user){
        this.orderUuid = orderUuid;
        this.user = user;
        status = OrderStatus.PENDING;
    }

    public BigDecimal getGrandTotal(){
        return orderItemList.stream()
                .map(OrderItem::getTotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

}
