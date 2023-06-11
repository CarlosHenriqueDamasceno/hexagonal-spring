package hexagonal.unit.link;

import hexagonal.link.domain.application.FindLinkBySlugImpl;
import hexagonal.link.port.LinkRepository;
import hexagonal.shared.exceptions.BusinessException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class FindLinkBySlugUnitTest {

    @Mock
    LinkRepository mockLinkRepository;

    @Test
    void shouldFindLinkBySlug() {
        when(mockLinkRepository.findBySlug(LinkUnitTestUtils.existentLink.slug().value()))
                .thenReturn(Optional.of(LinkUnitTestUtils.existentLink));
        var findLink = new FindLinkBySlugImpl(mockLinkRepository);
        var foundLink = findLink.execute(LinkUnitTestUtils.existentLink.slug().value());
        assertEquals(LinkUnitTestUtils.existentLink.id(), foundLink.id());
    }

    @Test
    void shouldNotFindALinkBySlug() {
        when(mockLinkRepository.findBySlug(LinkUnitTestUtils.existentLink.slug().value()))
                .thenReturn(Optional.empty());
        var findLink = new FindLinkBySlugImpl(mockLinkRepository);
        var exception = assertThrows(BusinessException.class, () -> {
                    findLink.execute(LinkUnitTestUtils.existentLink.slug().value());
                }
        );
        assertEquals(
                LinkUnitTestUtils.linkNotFoundWithSlugErrorMessage,
                exception.getMessage()
        );
    }
}
