package dk.ek.eksamensprojekt.account;

import dk.ek.eksamensprojekt.common.NotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
class AccountService implements AccountApi {

    private final AccountRepository accountRepository;

    AccountService(AccountRepository repository) {
        this.accountRepository = repository;
    }

    // ---- AccountApi: Used by other features ----

    @Override
    @Transactional(readOnly = true)
    public Long getAccountId(String email) {
        return accountRepository.findByEmail(Account.normalizeEmail(email))
                .orElseThrow(() -> new NotFoundException("Kontoen findes ikke"))
                .getId();
    }
}