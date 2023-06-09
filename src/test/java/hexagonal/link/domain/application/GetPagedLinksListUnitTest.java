package hexagonal.link.domain.application;

import hexagonal.auth.port.driven.AuthenticationService;
import hexagonal.link.LinkUnitTestUtils;
import hexagonal.link.port.LinkRepository;
import hexagonal.link.port.dto.LinkOutput;
import hexagonal.link.port.dto.PaginationInput;
import hexagonal.shared.port.dto.GetAllOutput;
import hexagonal.shared.port.dto.GetAllRepositoryOutput;
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

    @Mock
    AuthenticationService authenticationService;

    @InjectMocks
    GetAllLinksImpl getAllLinks;

    @Test
    void shouldGetTheFirstPageOf2Links() {
        int page = 1;
        int pageSize = 2;
        PaginationInput paginationInput = new PaginationInput(page, pageSize);
        when(authenticationService.getCurrentUserId()).thenReturn(1L);
        when(mockLinkRepository.getAll(paginationInput, 1L)).thenAnswer(invocation -> {
            PaginationInput input = invocation.getArgument(0);
            return new GetAllRepositoryOutput<>(
                    LinkUnitTestUtils.listOfLinks.stream().limit(input.pageSize()).toList(),
                    (long) LinkUnitTestUtils.listOfLinks.size()
            );
        });
        GetAllOutput<LinkOutput> result = getAllLinks.execute(paginationInput);
        assertEquals(page, result.pagination().page());
        assertEquals(pageSize, result.data().size());
    }

    @Test
    void shouldUserGetOnlyYourOwnLinks() {
        int page = 1;
        int pageSize = 2;
        PaginationInput paginationInput = new PaginationInput(page, pageSize);
        when(authenticationService.getCurrentUserId()).thenReturn(1L);
        when(mockLinkRepository.getAll(paginationInput, 1L)).thenAnswer(invocation -> {
            PaginationInput input = invocation.getArgument(0);
            Long userId = invocation.getArgument(1);
            return new GetAllRepositoryOutput<>(
                    LinkUnitTestUtils.listOfLinks.stream()
                            .filter(e -> e.userId().equals(userId))
                            .limit(input.pageSize())
                            .toList(),
                    LinkUnitTestUtils.listOfLinks.stream()
                            .filter(e -> e.userId().equals(userId)).count()
            );
        });
        GetAllOutput<LinkOutput> result = getAllLinks.execute(paginationInput);
        assertEquals(page, result.pagination().page());
        assertEquals(pageSize, result.data().size());
        assertEquals(LinkUnitTestUtils.firstUserLinksCount, result.getTotalRecords());
    }
}
