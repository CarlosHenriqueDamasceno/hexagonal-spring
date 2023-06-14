package hexagonal.link.domain.application;

import hexagonal.link.LinkUnitTestUtils;
import hexagonal.link.port.LinkRepository;
import hexagonal.shared.exceptions.BusinessException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class AccessLinkUnitTest {

    @Mock
    LinkRepository mockLinkRepository;

    @InjectMocks
    AccessLinkImpl findLinkBySlug;

    @Test
    void shouldFindLinkBySlugAndIncreaseTheAccessCount() {
        when(mockLinkRepository.findBySlug(LinkUnitTestUtils.existentLink.slug().value()))
                .thenReturn(Optional.of(LinkUnitTestUtils.existentLink));
        var foundLink = findLinkBySlug.execute(LinkUnitTestUtils.existentLink.slug().value());
        assertEquals(LinkUnitTestUtils.existentLink.id(), foundLink.id());
        assertEquals(1, foundLink.accesses());
    }

    @Test
    void shouldNotFindALinkBySlug() {
        when(mockLinkRepository.findBySlug(LinkUnitTestUtils.existentLink.slug().value()))
                .thenReturn(Optional.empty());
        var exception = assertThrows(BusinessException.class, () -> {
                    findLinkBySlug.execute(LinkUnitTestUtils.existentLink.slug().value());
                }
        );
        assertEquals(
                LinkUnitTestUtils.linkNotFoundWithSlugErrorMessage,
                exception.getMessage()
        );
    }
}
