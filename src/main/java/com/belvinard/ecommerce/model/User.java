package com.belvinard.ecommerce.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@NoArgsConstructor
@Table(name = "users",
        uniqueConstraints = {
        @UniqueConstraint(columnNames = "username"),
                @UniqueConstraint(columnNames = "email")
        })
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long userId;

    @NotBlank
    @Size(max = 20)
    @Column(name = "username")
    private String userName;

    @NotBlank
    @Size(max = 50)
    @Email
    @Column(name = "email")
    private String email;

    @NotBlank
    @Size(max = 120)
    @Column(name = "password")
    private String password;

    public User(String userName, String email, String password) {
        this.userName = userName;
        this.email = email;
        this.password = password;
    }

    // Lombok annotations to automatically generate getter and setter methods for the 'roles' field.
    @Setter
    @Getter

// Specifies a many-to-many relationship between the User entity and the Role entity.
// Each user can have multiple roles, and each role can be associated with multiple users.
    @ManyToMany(
            // Configures cascading behavior: PERSIST ensures that when a User is saved,
            // its associated Roles are also saved; MERGE updates roles when a User is updated.
            cascade = {CascadeType.PERSIST, CascadeType.MERGE},

            // Defines how associated roles are fetched. EAGER means roles will be loaded immediately
            // along with the User entity, which may affect performance with large datasets.
            fetch = FetchType.EAGER
    )

// Configures the join table to manage the many-to-many relationship in the database.
// The 'user_role' table serves as the intermediary table between 'users' and 'roles'.
    @JoinTable(
            name = "user_role", // Name of the join table in the database.

            // Configures the foreign key column in the join table that references the User entity.
            joinColumns = @JoinColumn(name = "user_id"),

            // Configures the foreign key column in the join table that references the Role entity.
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private Set<Role> roles = new HashSet<>();
// A set is used to store unique roles for a user. The initialization ensures that
// the collection is ready to use without explicitly initializing it elsewhere.

    @Getter
    @Setter
    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(name = "user_address",
                joinColumns = @JoinColumn(name = "user_id"),
                inverseJoinColumns = @JoinColumn(name = "address_id"))
    private List<Address> addresses
            = new ArrayList<>();

    @ToString.Exclude
    @OneToMany(mappedBy = "user",
            cascade = {CascadeType.PERSIST, CascadeType.MERGE},
            orphanRemoval = true)
    private Set<Product> products;

}
