package hexagonal.unit.link;

import hexagonal.link.domain.application.CreateLinkImpl;
import hexagonal.link.port.LinkRepository;
import hexagonal.link.port.application.CreateLink;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CreateLinkUnitTest {

    @Mock
    LinkRepository mockLinkRepository;

    @Test
    void shouldCreateALink() {
        Mockito.when(mockLinkRepository.findBySlug(LinkUnitTestUtils.validSlug))
                .thenReturn(Optional.empty());
        Mockito.when(mockLinkRepository.create(LinkUnitTestUtils.nonExistentLink))
                .thenReturn(LinkUnitTestUtils.existentLink);
        var input = new CreateLink.LinkInput(
                LinkUnitTestUtils.validUrl,
                LinkUnitTestUtils.validSlug
        );
        var createLink = new CreateLinkImpl(mockLinkRepository);
        var newLink = createLink.execute(input);
        assertEquals(LinkUnitTestUtils.existentLink.id(), newLink.id());
    }

    @Test
    void shouldCreateALinkWithNonGivenSlug() {
        Mockito.when(mockLinkRepository.findBySlug(anyString()))
                .thenReturn(Optional.empty());
        Mockito.when(mockLinkRepository.create(argThat(link -> link.url().equals(LinkUnitTestUtils.validUrl))))
                .thenReturn(LinkUnitTestUtils.existentLink);
        var input = new CreateLink.LinkInput(
                LinkUnitTestUtils.validUrl,
                null
        );
        var createLink = new CreateLinkImpl(mockLinkRepository);
        var newLink = createLink.execute(input);
        assertEquals(LinkUnitTestUtils.existentLink.id(), newLink.id());
    }

    @Test
    void shouldCreateALinkWithNonGivenSlugTryingTwoTimes() {
        Mockito.when(mockLinkRepository.findBySlug(anyString()))
                .thenReturn(Optional.of(LinkUnitTestUtils.existentLink)).thenReturn(Optional.empty());
        Mockito.when(mockLinkRepository.create(argThat(link -> link.url().equals(LinkUnitTestUtils.validUrl))))
                .thenReturn(LinkUnitTestUtils.existentLink);
        var input = new CreateLink.LinkInput(
                LinkUnitTestUtils.validUrl,
                null
        );
        var createLink = new CreateLinkImpl(mockLinkRepository);
        var newLink = createLink.execute(input);
        assertEquals(LinkUnitTestUtils.existentLink.id(), newLink.id());
        verify(mockLinkRepository, times(2)).findBySlug(anyString());
    }
}
