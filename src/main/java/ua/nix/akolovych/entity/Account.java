package ua.nix.akolovych.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "accounts")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Account extends BaseEntity {
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    @Column(name = "balance")
    private Long balance;
    @OneToMany(mappedBy = "account", cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    private Set<Transaction> transactions = new HashSet<>();

}
