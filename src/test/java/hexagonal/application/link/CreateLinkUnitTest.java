package hexagonal.application.link;

import hexagonal.link.application.LinkRepository;
import hexagonal.link.application.useCases.CreateLinkImpl;
import hexagonal.link.application.useCases.ports.CreateLink;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

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
        Mockito.when(mockLinkRepository.findBySlug(LinkUnitTestUtils.validSlug))
                .thenReturn(Optional.empty());
        Mockito.when(mockLinkRepository.create(LinkUnitTestUtils.nonExistentLink))
                .thenReturn(LinkUnitTestUtils.existentLink);
        var input = new CreateLink.LinkInput(
                LinkUnitTestUtils.validUrl,
                null
        );
        var createLink = new CreateLinkImpl(mockLinkRepository);
        var newLink = createLink.execute(input);
        assertEquals(LinkUnitTestUtils.existentLink.id(), newLink.id());
    }
}
