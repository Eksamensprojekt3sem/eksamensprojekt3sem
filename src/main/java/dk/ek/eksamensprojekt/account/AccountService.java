package dk.ek.eksamensprojekt.account;

import dk.ek.eksamensprojekt.common.ConflictException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
class AccountService {

    private final AccountRepository accountRepository;
    private final PasswordEncoder passwordEncoder;

    AccountService(AccountRepository accountRepository, PasswordEncoder passwordEncoder) {
        this.accountRepository = accountRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    void register(RegisterRequest request) {
        String email = Account.normalizeEmail(request.email());
        if (accountRepository.findByEmail(email).isPresent()) {
            throw new ConflictException("Emailen findes allerede");
        }
         accountRepository.save(Account.create(email,
                passwordEncoder.encode(request.password()),
                request.role()));
    }
}
