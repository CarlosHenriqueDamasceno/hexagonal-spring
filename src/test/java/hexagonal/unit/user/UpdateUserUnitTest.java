package hexagonal.unit.user;

import hexagonal.shared.exceptions.BusinessException;
import hexagonal.user.domain.application.UpdateUserImpl;
import hexagonal.user.port.UserRepository;
import hexagonal.user.port.dto.UpdateUserInput;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
public class UpdateUserUnitTest {

    @Mock
    UserRepository mockUserRepository;

    @Test
    void shouldUpdateUser() {

        Mockito.when(mockUserRepository.find(1L))
                .thenReturn(Optional.of(UserTestUtils.existentUser));

        var input = new UpdateUserInput(
                "Carlos editado",
                UserTestUtils.editedEmail
        );
        var updateUser = new UpdateUserImpl(mockUserRepository);
        var result = updateUser.execute(1L, input);
        assertEquals(result.name(), UserTestUtils.updatedUser.name());
        assertEquals(result.email(), UserTestUtils.updatedUser.email().value());
    }

    @Test
    void shouldNotUpdateUserBecauseInvalidId() {

        Mockito.when(mockUserRepository.find(2L))
                .thenReturn(Optional.empty());

        var input = new UpdateUserInput(
                "Carlos editado",
                UserTestUtils.editedEmail
        );
        var updateUser = new UpdateUserImpl(mockUserRepository);
        var exception = assertThrows(BusinessException.class, () -> updateUser.execute(2L, input));
        assertEquals(UserTestUtils.invalidUserErrorMessage, exception.getMessage());
    }

    @Test
    void shouldNotUpdateUserBecauseInvalidEmail() {

        Mockito.when(mockUserRepository.find(1L))
                .thenReturn(Optional.of(UserTestUtils.existentUser));

        Mockito.when(mockUserRepository.findByEmail(UserTestUtils.validEmail))
                .thenReturn(Optional.of(UserTestUtils.existentUser));

        var input = new UpdateUserInput(
                "Carlos editado",
                UserTestUtils.validEmail
        );
        var updateUser = new UpdateUserImpl(mockUserRepository);
        var exception = assertThrows(BusinessException.class, () -> updateUser.execute(1L, input));
        assertEquals(UserTestUtils.invalidEmailErrorMessage, exception.getMessage());
    }
}
