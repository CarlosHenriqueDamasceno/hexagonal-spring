package hexagonal.link.domain.application;

import hexagonal.link.LinkUnitTestUtils;
import hexagonal.link.port.LinkRepository;
import hexagonal.shared.exceptions.BusinessException;
import hexagonal.shared.exceptions.RecordNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class FindLinkByIdUnitTest {
    @Mock
    LinkRepository mockLinkRepository;

    @Test
    void shouldFindALinkById() {
        when(mockLinkRepository.findById(LinkUnitTestUtils.existentLink.id()))
                .thenReturn(LinkUnitTestUtils.existentLink);
        var findLink = new FindLinkByIdImpl(mockLinkRepository);
        var foundLink = findLink.execute(LinkUnitTestUtils.existentLink.id());
        assertEquals(LinkUnitTestUtils.existentLink.id(), foundLink.id());
    }

    @Test
    void shouldNotFindALinkById() {
        when(mockLinkRepository.findById(LinkUnitTestUtils.existentLink.id()))
                .thenThrow(new RecordNotFoundException("record not found"));
        var findLink = new FindLinkByIdImpl(mockLinkRepository);
        var exception = assertThrows(BusinessException.class, () -> {
                    findLink.execute(LinkUnitTestUtils.existentLink.id());
                }
        );
        assertEquals(
                LinkUnitTestUtils.linkNotFoundErrorMessage,
                exception.getMessage()
        );
    }
}
