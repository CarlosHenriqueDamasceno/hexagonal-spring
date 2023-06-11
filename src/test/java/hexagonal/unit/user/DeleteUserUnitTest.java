package hexagonal.unit.user;

import hexagonal.shared.exceptions.BusinessException;
import hexagonal.shared.exceptions.RecordNotFoundException;
import hexagonal.user.domain.application.DeleteUserImpl;
import hexagonal.user.port.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class DeleteUserUnitTest {

    @Mock
    UserRepository mockUserRepository;

    @Test
    void shouldDeleteAUser() {

        when(mockUserRepository.find(1L))
                .thenReturn(UserTestUtils.existentUser);

        var deleteUser = new DeleteUserImpl(mockUserRepository);
        assertDoesNotThrow(() -> deleteUser.execute(1L));
    }

    @Test
    void shouldNotDeleteAUserBecauseInvalidId() {

        when(mockUserRepository.find(1L))
                .thenThrow(new RecordNotFoundException("record not found"));

        var deleteUser = new DeleteUserImpl(mockUserRepository);
        var exception = assertThrows(BusinessException.class, () -> deleteUser.execute(1L));
        assertEquals(UserTestUtils.invalidUserErrorMessage, exception.getMessage());
    }
}
