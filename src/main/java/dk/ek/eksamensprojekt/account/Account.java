package dk.ek.eksamensprojekt.account;

import dk.ek.eksamensprojekt.common.InvalidInputException;
import jakarta.persistence.*;

import java.util.Locale;

@Entity
@Table(name = "accounts")
class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(name = "password_hash", nullable = false)
    private String passwordHash;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;

    protected Account() {
        // required by JPA
    }

    private Account(String email, String passwordHash, Role role) {
        this.email = normalizeEmail(email);
        this.passwordHash = requireNotBlank(passwordHash, "En konto skal have en adgangskode.");
        if (role == null) {
            throw new InvalidInputException("En konto skal have en rolle.");
        }
        this.role = role;
    }

    static Account create(String email, String passwordHash, Role role) {
        return new Account(email, passwordHash, role);
    }

    static String normalizeEmail(String email) {
        return requireNotBlank(email, "En konto skal have en e-mail.")
                .trim()
                .toLowerCase(Locale.ROOT);
    }

    private static String requireNotBlank(String value, String message) {
        if (value == null || value.isBlank()) {
            throw new InvalidInputException(message);
        }
        return value;
    }

    Long getId() {
        return id;
    }

    String getEmail() {
        return email;
    }

    String getPasswordHash() {
        return passwordHash;
    }

    Role getRole() {
        return role;
    }
}