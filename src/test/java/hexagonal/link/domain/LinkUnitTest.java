package hexagonal.link.domain;

import hexagonal.link.LinkUnitTestUtils;
import hexagonal.shared.exceptions.BusinessException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class LinkUnitTest {

    @Test
    void shouldInstantiateANewLinkWithGivenSlug() {
        Link link = Link.buildNonExistentLink(LinkUnitTestUtils.validUrl, LinkUnitTestUtils.validSlug, 1L);
        assertEquals(LinkUnitTestUtils.validSlug, link.slug().value());
    }

    @Test
    void shouldInstantiateANewLinkWithGeneratedSlug() {
        Link link = Link.buildNonExistentLink(LinkUnitTestUtils.validUrl, 1L);
        assertNotNull(link.slug());
    }

    @Test
    void shouldNotInstantiateANewLinkWithNullGivenSlug() {
        var exception = assertThrows(BusinessException.class, () -> {
                    Link.buildNonExistentLink(LinkUnitTestUtils.validUrl, null, 1L);
                }
        );
        assertEquals(LinkUnitTestUtils.nullSlugErrorMessage, exception.getMessage());
    }

    @Test
    void shouldNotInstantiateANewLinkWithNullInvalidSizeSlug() {
        var exception = assertThrows(BusinessException.class, () -> {
                    Link.buildNonExistentLink(LinkUnitTestUtils.validUrl, LinkUnitTestUtils.invalidSlug, 1L);
                }
        );
        assertEquals(
                LinkUnitTestUtils.invalidLengthErrorMessage,
                exception.getMessage()
        );
    }
}
