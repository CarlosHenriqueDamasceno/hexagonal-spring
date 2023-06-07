package hexagonal.application.link;

import hexagonal.link.domain.Link;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

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
        System.out.println(link.slug().value());
    }
}
