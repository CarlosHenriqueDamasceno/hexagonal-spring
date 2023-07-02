package hexagonal.user.domain.application;

import hexagonal.shared.exceptions.BusinessException;
import hexagonal.user.UserTestUtils;
import hexagonal.user.port.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doThrow;

@ExtendWith(MockitoExtension.class)
public class DeleteUserUnitTest {

    @Mock
    UserRepository mockUserRepository;

    @Test
    void shouldDeleteAUser() {
        var deleteUser = new DeleteUserImpl(mockUserRepository);
        deleteUser.execute(1L);
    }

    @Test
    void shouldNotDeleteAUserBecauseInvalidId() {

        var deleteUser = new DeleteUserImpl(mockUserRepository);
        var exception = assertThrows(BusinessException.class, () -> {
            doThrow(new BusinessException("Usuário não encontrado com o id: 1.")).when(mockUserRepository).delete(1L);
            deleteUser.execute(1L);
        });
        assertEquals(UserTestUtils.invalidUserErrorMessage, exception.getMessage());
    }
}
