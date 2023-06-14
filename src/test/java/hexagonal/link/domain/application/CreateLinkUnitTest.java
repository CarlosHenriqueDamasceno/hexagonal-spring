package hexagonal.link.domain.application;

import hexagonal.link.LinkUnitTestUtils;
import hexagonal.link.port.LinkRepository;
import hexagonal.link.port.application.CreateLink;
import hexagonal.shared.exceptions.BusinessException;
import hexagonal.shared.port.application.AuthenticationService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CreateLinkUnitTest {

    @Mock
    LinkRepository mockLinkRepository;

    @Mock
    AuthenticationService authenticationService;

    @InjectMocks
    CreateLinkImpl createLink;

    @Test
    void shouldCreateALink() {
        when(mockLinkRepository.findBySlug(LinkUnitTestUtils.validSlug))
                .thenReturn(Optional.empty());
        when(mockLinkRepository.create(LinkUnitTestUtils.nonExistentLink))
                .thenReturn(LinkUnitTestUtils.existentLink);
        when(authenticationService.getCurrentUserId()).thenReturn(1L);
        var input = new CreateLink.LinkInput(
                LinkUnitTestUtils.validUrl,
                LinkUnitTestUtils.validSlug
        );
        var newLink = createLink.execute(input);
        assertEquals(LinkUnitTestUtils.existentLink.id(), newLink.id());
    }

    @Test
    void shouldCreateALinkWithNonGivenSlug() {
        when(mockLinkRepository.findBySlug(anyString()))
                .thenReturn(Optional.empty());
        when(mockLinkRepository.create(argThat(link -> link.url().equals(LinkUnitTestUtils.validUrl))))
                .thenReturn(LinkUnitTestUtils.existentLink);
        when(authenticationService.getCurrentUserId()).thenReturn(1L);
        var input = new CreateLink.LinkInput(
                LinkUnitTestUtils.validUrl,
                null
        );
        var newLink = createLink.execute(input);
        assertEquals(LinkUnitTestUtils.existentLink.id(), newLink.id());
    }

    @Test
    void shouldCreateALinkWithNonGivenSlugTryingTwice() {
        when(mockLinkRepository.findBySlug(anyString()))
                .thenReturn(Optional.of(LinkUnitTestUtils.existentLink)).thenReturn(Optional.empty());
        when(mockLinkRepository.create(argThat(link -> link.url().equals(LinkUnitTestUtils.validUrl))))
                .thenReturn(LinkUnitTestUtils.existentLink);
        when(authenticationService.getCurrentUserId()).thenReturn(1L);
        var input = new CreateLink.LinkInput(
                LinkUnitTestUtils.validUrl,
                null
        );
        var newLink = createLink.execute(input);
        assertEquals(LinkUnitTestUtils.existentLink.id(), newLink.id());
        verify(mockLinkRepository, times(2)).findBySlug(anyString());
    }

    @Test
    void shouldNotCreateALinkWithAlreadyTakenSlug() {
        when(mockLinkRepository.findBySlug(anyString()))
                .thenReturn(Optional.of(LinkUnitTestUtils.existentLink)).thenReturn(Optional.empty());
        when(authenticationService.getCurrentUserId()).thenReturn(1L);
        var input = new CreateLink.LinkInput(
                LinkUnitTestUtils.validUrl,
                "asdas23"
        );
        var exception = assertThrows(BusinessException.class, () -> {
                    createLink.execute(input);
                }
        );
        assertEquals(
                LinkUnitTestUtils.invalidSlugErrorMessage,
                exception.getMessage()
        );
    }
}
