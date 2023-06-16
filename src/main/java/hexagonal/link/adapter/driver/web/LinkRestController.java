package hexagonal.link.adapter.driver.web;

import hexagonal.link.port.application.CreateLink;
import hexagonal.link.port.application.FindLinkById;
import hexagonal.link.port.application.GetAllLinks;
import hexagonal.link.port.dto.LinkInput;
import hexagonal.link.port.dto.LinkOutput;
import hexagonal.link.port.dto.PaginationInput;
import hexagonal.shared.port.dto.GetAllOutput;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.Optional;

@RestController
@RequestMapping("api/v1/links")
public class LinkRestController {
    private final CreateLink createLink;
    private final FindLinkById findLinkById;
    private final GetAllLinks getAllLinks;

    public LinkRestController(
            CreateLink createLink,
            FindLinkById findLinkById,
            GetAllLinks getAllLinks
    ) {
        this.createLink = createLink;
        this.findLinkById = findLinkById;
        this.getAllLinks = getAllLinks;
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody @Valid LinkInput data) {
        var result = createLink.execute(data);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(result).toUri();
        return ResponseEntity.created(location).build();
    }

    @GetMapping
    public GetAllOutput<LinkOutput> getAll(
            @RequestParam Optional<Integer> page,
            @RequestParam Optional<Integer> pageSize
    ) {
        return getAllLinks.execute(parsePagination(page, pageSize));
    }

    @GetMapping("{id}")
    public LinkOutput find(@PathVariable long id) {
        return findLinkById.execute(id);
    }

    protected PaginationInput parsePagination(Optional<Integer> page, Optional<Integer> pageSize) {
        int s = pageSize.orElse(10);
        int p = page.isPresent() && page.get() > 0 ? page.get() - 1 : 0;
        return new PaginationInput(p, s);
    }
}
