package hexagonal.application.link;

import hexagonal.link.domain.Link;
import hexagonal.shared.exceptions.BusinessException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class LinkUnitTest {

    @Test
    void shouldInstantiateANewLinkWithGivenSlug() {
        Link link = Link.buildNonExistentLink(LinkUnitTestUtils.validUrl, LinkUnitTestUtils.validSlug);
        assertEquals(LinkUnitTestUtils.validSlug, link.slug().value());
    }

    @Test
    void shouldInstantiateANewLinkWithGeneratedSlug() {
        Link link = Link.buildNonExistentLink(LinkUnitTestUtils.validUrl);
        assertNotNull(link.slug());
    }

    @Test
    void shouldNotInstantiateANewLinkWithNullGivenSlug() {

        var exception = assertThrows(BusinessException.class, () -> {
                    Link.buildNonExistentLink(LinkUnitTestUtils.validUrl, null);
                }
        );
        assertEquals(LinkUnitTestUtils.nullSlugErrorMessage, exception.getMessage());
    }

    @Test
    void shouldNotInstantiateANewLinkWithNullInvalidSizeSlug() {

        var exception = assertThrows(BusinessException.class, () -> {
                    Link.buildNonExistentLink(LinkUnitTestUtils.validUrl, LinkUnitTestUtils.invalidSlug);
                }
        );
        assertEquals(
                LinkUnitTestUtils.invalidLengthErrorMessage,
                exception.getMessage()
        );
    }
}
