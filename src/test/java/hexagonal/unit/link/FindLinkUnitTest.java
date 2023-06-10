package hexagonal.unit.link;

import hexagonal.link.domain.application.FindLinkByIdImpl;
import hexagonal.link.port.LinkRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class FindLinkUnitTest {
    @Mock
    LinkRepository mockLinkRepository;

    @Test
    void shouldFindALinkById() {
        Mockito.when(mockLinkRepository.findById(LinkUnitTestUtils.existentLink.id()))
                .thenReturn(LinkUnitTestUtils.existentLink);
        var findLink = new FindLinkByIdImpl(mockLinkRepository);
        var foundLink = findLink.execute(LinkUnitTestUtils.existentLink.id());
        assertEquals(LinkUnitTestUtils.existentLink.id(), foundLink.id());
    }
}
