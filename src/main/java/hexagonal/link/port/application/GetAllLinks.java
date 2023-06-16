package hexagonal.link.port.application;

import hexagonal.link.port.dto.LinkOutput;
import hexagonal.link.port.dto.PaginationInput;
import hexagonal.shared.port.dto.GetAllOutput;

public interface GetAllLinks {
    GetAllOutput<LinkOutput> execute(PaginationInput paginationInput);
}
