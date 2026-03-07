package springbootlearning.ecommerce.entities;

import jakarta.persistence.*;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@NoArgsConstructor
@Entity(name = "addresses")
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "address1")
    private String address1;

    @Column(name = "address2")
    private String address2;

    @Column(name = "city")
    private String city;

    @Column(name = "province")
    private String province;

    @Column(name = "zip_code")
    private String zipCode;

    public Address(String address1, String address2, String city, String province, String zipCode){
        this.address1 = address1;
        this.address2 = address2;
        this.city = city;
        this.province = province;
        this.zipCode = zipCode;
    }
}
