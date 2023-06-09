package hexagonal.unit.user;

import hexagonal.shared.exceptions.BusinessException;
import hexagonal.user.domain.application.DeleteUserImpl;
import hexagonal.user.port.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class DeleteUserUnitTest {

    @Mock
    UserRepository mockUserRepository;

    @Test
    void shouldDeleteAUser() {

        Mockito.when(mockUserRepository.find(1L))
                .thenReturn(Optional.of(UserTestUtils.existentUser));

        var deleteUser = new DeleteUserImpl(mockUserRepository);
        assertDoesNotThrow(() -> deleteUser.execute(1L));
    }

    @Test
    void shouldNotDeleteAUserBecauseInvalidId() {

        Mockito.when(mockUserRepository.find(2L))
                .thenReturn(Optional.empty());

        var deleteUser = new DeleteUserImpl(mockUserRepository);
        var exception = assertThrows(BusinessException.class, () -> deleteUser.execute(2L));
        assertEquals(UserTestUtils.invalidUserErrorMessage, exception.getMessage());
    }
}
