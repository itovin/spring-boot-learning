package springbootlearning.ecommerce.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
@Entity
@Table(name = "cart")
public class Cart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @OneToOne
    @JoinColumn(name = "product_id")
    private Product product;

    @Column(name = "quantity")
    private int quantity;

    public Cart(User user, Product product, int quantity){
        this.user = user;
        this.product = product;
        this.quantity = quantity;
    }

    public String toString(){
        return product.getId() + " | " + product.getName() + " | Price: " + product.getPrice() + " | Quantiy: " + quantity;
    }
}
