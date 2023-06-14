package hexagonal.unit.link;

import hexagonal.link.domain.Link;
import hexagonal.link.domain.application.GetAllLinksImpl;
import hexagonal.link.port.LinkRepository;
import hexagonal.link.port.dto.GetAllOutput;
import hexagonal.link.port.dto.PaginationInput;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class GetPagedLinksListUnitTest {

    @Mock
    LinkRepository mockLinkRepository;

    @InjectMocks
    GetAllLinksImpl getAllLinks;

    @Test
    void shouldGetTheFirstPageOf2Links() {
        int page = 1;
        int pageSize = 2;
        PaginationInput paginationInput = new PaginationInput(page, pageSize);
        when(mockLinkRepository.getAll(paginationInput)).thenAnswer(invocation -> {
            PaginationInput input = invocation.getArgument(0);
            return new GetAllOutput<>(
                    LinkUnitTestUtils.listOfLinks.stream().limit(input.pageSize()).toList(),
                    input.pageSize(),
                    input.page(),
                    LinkUnitTestUtils.listOfLinks.size()
            );
        });
        GetAllOutput<Link> result = getAllLinks.execute(paginationInput);
        assertEquals(page, result.pagination().page());
        assertEquals(pageSize, result.data().size());
    }
}
