package com.stefan.customer;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "customer",
        uniqueConstraints = {
                @UniqueConstraint(name = "customer_email_unique",
                        columnNames = "email"
                )
        }
)
public class Customer {

    @Id
    @SequenceGenerator(name = "customer_id_seq", sequenceName = "customer_id_seq",
            allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "customer_id_seq")
    private Integer id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private Integer age;
}
