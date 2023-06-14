package hexagonal.link.port.application;

import hexagonal.link.domain.Link;
import hexagonal.link.port.dto.GetAllOutput;
import hexagonal.link.port.dto.PaginationInput;

public interface GetAllLinks {
    GetAllOutput<Link> execute(PaginationInput paginationInput);
}
