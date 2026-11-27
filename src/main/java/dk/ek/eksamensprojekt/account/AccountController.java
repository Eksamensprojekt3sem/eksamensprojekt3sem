package dk.ek.eksamensprojekt.account;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/register")
class AccountController {

    private final AccountService accountService;

    AccountController(AccountService accountService) {
        this.accountService=accountService;
    }

    @PostMapping
    ResponseEntity<Void> register(@Valid @RequestBody RegisterRequest request) {
         accountService.register(request);
         return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
