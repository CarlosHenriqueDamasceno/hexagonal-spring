package hexagonal.unit.user;

import hexagonal.shared.exceptions.BusinessException;
import hexagonal.shared.exceptions.RecordNotFoundException;
import hexagonal.user.domain.application.FindUserImpl;
import hexagonal.user.port.UserRepository;
import hexagonal.user.port.dto.UserOutput;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class FindUserUnitTest {

    @Mock
    UserRepository mockUserRepository;

    @Test
    void shouldFindAUser() {

        var expected = UserOutput.fromUser(UserTestUtils.existentUser);

        when(mockUserRepository.find(1L))
                .thenReturn(UserTestUtils.existentUser);

        var findUser = new FindUserImpl(mockUserRepository);
        var user = findUser.execute(1L);
        assertEquals(user, expected);
    }

    @Test
    void shouldNotFindUser() {

        when(mockUserRepository.find(1L))
                .thenThrow(new RecordNotFoundException("record not found"));

        var findUser = new FindUserImpl(mockUserRepository);
        var exception = assertThrows(BusinessException.class, () -> findUser.execute(1L));
        assertEquals(UserTestUtils.invalidUserErrorMessage, exception.getMessage());
    }

}
