package springbootlearning.ecommerce.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;
import java.util.UUID;

@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "refresh_token")
public class RefreshToken {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id")
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "revoked")
    private boolean isRevoked;

    @Column(name = "expires_at")
    private Instant expiresAt;

    public RefreshToken(User user, Instant expiresAt){
        this.user = user;
        this.expiresAt = expiresAt;
        this.isRevoked = false;
    }

}
