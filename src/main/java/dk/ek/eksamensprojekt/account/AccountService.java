package dk.ek.eksamensprojekt.account;

import dk.ek.eksamensprojekt.common.ConflictException;
import dk.ek.eksamensprojekt.common.InvalidInputException;
import dk.ek.eksamensprojekt.common.NotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

@Service
class AccountService implements AccountApi {

    private static final Set<Role> SELF_REGISTRABLE_ROLES = Set.of(Role.MEMBER, Role.PARENT);
    private final AccountRepository accountRepository;
    private final PasswordEncoder passwordEncoder;

    AccountService(AccountRepository accountRepository, PasswordEncoder passwordEncoder) {
        this.accountRepository = accountRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    @Transactional(readOnly = true)
    public Long getAccountId(String email) {
        return accountRepository.findByEmail(Account.normalizeEmail(email))
                .orElseThrow(() -> new NotFoundException("Kontoen findes ikke"))
                .getId();
    }

    @Transactional
    void register(RegisterRequest request) {
        if (!VALID_ROLES.contains(request.role())) {
            throw new InvalidInputException("Du kan kun oprette dig som medlem eller som forælder");
        }

        String email = Account.normalizeEmail(request.email());
        if (accountRepository.findByEmail(email).isPresent()) {
            throw new ConflictException("Emailen findes allerede");
        }

        accountRepository.save(Account.create(email,
                passwordEncoder.encode(request.password()),
                request.role()));
    }
}