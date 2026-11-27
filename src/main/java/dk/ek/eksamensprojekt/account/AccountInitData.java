package dk.ek.eksamensprojekt.account;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@Order(1)
class AccountInitData implements CommandLineRunner {

    private final AccountRepository accountRepository;
    private final PasswordEncoder passwordEncoder;
    private final String adminPassword;
    private final String instructorPassword;
    private final String memberParentPassword;

    AccountInitData(AccountRepository accountRepository,
                    PasswordEncoder passwordEncoder,
                    @Value("${SEED_ADMIN_PASSWORD}") String adminPassword,
                    @Value("${SEED_INSTRUCTOR_PASSWORD}") String instructorPassword,
                    @Value("${SEED_MEMBER_PASSWORD}") String memberParentPassword) {
        this.accountRepository = accountRepository;
        this.passwordEncoder = passwordEncoder;
        this.adminPassword = adminPassword;
        this.instructorPassword = instructorPassword;
        this.memberParentPassword=memberParentPassword;
    }

    @Override
    public void run(String... args) {
        seed("admin@example.dk", adminPassword, Role.ADMIN);
        seed("instructor@example.dk", instructorPassword, Role.INSTRUCTOR);
        seed("member1@example.dk", memberParentPassword, Role.MEMBER);
        seed("member2@example.dk", memberParentPassword, Role.MEMBER);
        seed("parent1@example.dk", memberParentPassword, Role.PARENT);
        seed("parent2@example.dk", memberParentPassword, Role.PARENT);
    }

    private void seed(String email, String rawPassword, Role role) {
        if (accountRepository.findByEmail(Account.normalizeEmail(email)).isPresent()) {
            return;
        }
        if (rawPassword == null || rawPassword.isBlank()) {
            throw new IllegalStateException("Adgangskode til " + email + " mangler i .env");
        }
        accountRepository.save(Account.create(email, passwordEncoder.encode(rawPassword), role));
    }
}