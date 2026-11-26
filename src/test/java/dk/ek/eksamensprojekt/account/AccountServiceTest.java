package dk.ek.eksamensprojekt.account;

import dk.ek.eksamensprojekt.common.NotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AccountServiceTest {

    @Mock
    private AccountRepository repository;

    @InjectMocks
    private AccountService service;

    @Test
    void findTheAccountIdIgnoringCaseAndSpaces() {
        // Arrange: a fake account with a known id, since only yhe database sets real id's
        Account account = mock(Account.class);
        when(account.getId()).thenReturn(7L);
        when(repository.findByEmail("instructor@example.dk")).thenReturn(Optional.of(account));


        // Act: capital letters and spaces on purpose
        Long id = service.getAccountId(" Instructor@example.Dk ");

        // Assert
        assertEquals(7L, id);
    }

    @Test
    void unknownEmailThrowsNotFoundException() {
        when(repository.findByEmail("ukendt@example.dk")).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class,
                () -> service.getAccountId("ukendt@example.dk"));
    }
}
