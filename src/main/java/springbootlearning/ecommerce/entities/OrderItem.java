package springbootlearning.ecommerce.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@NoArgsConstructor
@Getter
@Entity
@Table(name = "order_items")
public class OrderItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "order_id")
    private Order order;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "price_at_purchase")
    private BigDecimal price;

    @Column(name = "quantity")
    private int quantity;

    public BigDecimal getTotal(){
        return price.multiply(BigDecimal.valueOf(quantity));
    }

    public OrderItem(Order order, Product product, String name, String description, BigDecimal price, int quantity){
        this.order = order;
        this.product = product;
        this.name = name;
        this.description = description;
        this.price = price;
        this.quantity = quantity;
    }
}
