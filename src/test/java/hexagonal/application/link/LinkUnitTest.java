package hexagonal.application.link;

import hexagonal.link.domain.Link;
import hexagonal.link.domain.valueObjects.Slug;
import hexagonal.shared.exceptions.BusinessException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class LinkUnitTest {

    @Test
    void shouldInstantiateANewLinkWithGivenSlug() {
        Link link = Link.buildNonExistentLink("https://www.google.com.br", "45d3df0");
        assertEquals("45d3df0", link.slug().value());
    }

    @Test
    void shouldInstantiateANewLinkWithGeneratedSlug() {
        Link link = Link.buildNonExistentLink("https://www.google.com.br");
        assertNotNull(link.slug());
    }

    @Test
    void shouldNotInstantiateANewLinkWithNullGivenSlug() {

        var exception = assertThrows(BusinessException.class, () -> {
                    Link.buildNonExistentLink("https://www.google.com.br", null);
                }
        );
        assertEquals("The slug can not be null", exception.getMessage());
    }

    @Test
    void shouldNotInstantiateANewLinkWithNullInvalidSizeSlug() {

        var exception = assertThrows(BusinessException.class, () -> {
                    Link.buildNonExistentLink("https://www.google.com.br", "sdawsdaswd");
                }
        );
        assertEquals(
                "The slug length has to be between " + Slug.minlength + " and " + Slug.maxlength,
                exception.getMessage()
        );
    }
}
