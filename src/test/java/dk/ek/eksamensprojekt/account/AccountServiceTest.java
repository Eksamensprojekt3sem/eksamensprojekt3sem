package dk.ek.eksamensprojekt.account;

import dk.ek.eksamensprojekt.common.ConflictException;
import dk.ek.eksamensprojekt.common.NotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AccountServiceTest {

    @Mock AccountRepository accountRepository;
    @Mock PasswordEncoder passwordEncoder;
    @InjectMocks AccountService accountService;

    // ---- getAccountId ----

    @Test
    void findTheAccountIdIgnoringCaseAndSpaces() {
        // Arrange: a fake account with a known id, since only the database sets real ids
        Account account = mock(Account.class);
        when(account.getId()).thenReturn(7L);
        when(accountRepository.findByEmail("instructor@example.dk")).thenReturn(Optional.of(account));

        // Act: capital letters and spaces on purpose
        Long id = accountService.getAccountId(" Instructor@example.Dk ");

        // Assert
        assertThat(id).isEqualTo(7L);
    }

    @Test
    void unknownEmailThrowsNotFoundException() {
        when(accountRepository.findByEmail("ukendt@example.dk")).thenReturn(Optional.empty());

        assertThatThrownBy(() -> accountService.getAccountId("ukendt@example.dk"))
                .isInstanceOf(NotFoundException.class);
    }

    // ---- register ----

    @Test
    void register_saveAccountWithHashedPassword() {
        when(accountRepository.findByEmail("flemming@example.com")).thenReturn(Optional.empty());

        when(passwordEncoder.encode("hemmelig123")).thenReturn("hashed");

        accountService.register(new RegisterRequest("flemming@example.com", "hemmelig123", Role.MEMBER));

        ArgumentCaptor<Account> captor = ArgumentCaptor.forClass(Account.class);

        verify(accountRepository).save(captor.capture());
        assertThat(captor.getValue().getEmail()).isEqualTo("flemming@example.com");
        assertThat(captor.getValue().getPasswordHash()).isEqualTo("hashed");
    }

    @Test
    void register_existingEmail_throwsConflict() {

        Account existingAccount = Account.create("anna@example.com", "hashet-kodeord", Role.PARENT);
        when(accountRepository.findByEmail("anna@example.com")).thenReturn(Optional.of(existingAccount));
        RegisterRequest request = new RegisterRequest("anna@example.com", "kodeord123", Role.MEMBER);

        assertThatThrownBy(() -> accountService.register(request))
                .isInstanceOf(ConflictException.class);

        verify(accountRepository, never()).save(any());
    }
}