package hexagonal.user.domain.application;

import hexagonal.shared.exceptions.BusinessException;
import hexagonal.shared.exceptions.RecordNotFoundException;
import hexagonal.user.UserTestUtils;
import hexagonal.user.port.UserRepository;
import hexagonal.user.port.dto.UpdateUserInput;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UpdateUserUnitTest {

    @Mock
    UserRepository mockUserRepository;

    @Test
    void shouldUpdateUser() {

        when(mockUserRepository.find(1L))
                .thenReturn(UserTestUtils.existentUser);

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

        when(mockUserRepository.find(1L))
                .thenThrow(new RecordNotFoundException("record not found"));

        var input = new UpdateUserInput(
                "Carlos editado",
                UserTestUtils.editedEmail
        );
        var updateUser = new UpdateUserImpl(mockUserRepository);
        var exception = assertThrows(BusinessException.class, () -> updateUser.execute(1L, input));
        assertEquals(UserTestUtils.invalidUserErrorMessage, exception.getMessage());
    }

    @Test
    void shouldNotUpdateUserBecauseInvalidEmail() {

        when(mockUserRepository.find(1L))
                .thenReturn(UserTestUtils.existentUser);

        when(mockUserRepository.findByEmail(UserTestUtils.validEmail))
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
