package hexagonal.link.adapter.driver.web;

import hexagonal.link.port.application.AccessLink;
import hexagonal.link.port.application.CreateLink;
import hexagonal.link.port.application.FindLinkById;
import hexagonal.link.port.application.GetAllLinks;
import hexagonal.link.port.dto.LinkInput;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("api/v1/links")
public class LinkRestController {
    private final CreateLink createLink;
    private final FindLinkById findLinkById;
    private final GetAllLinks getAllLinks;
    private final AccessLink accessLink;

    public LinkRestController(
            CreateLink createLink,
            FindLinkById findLinkById,
            GetAllLinks getAllLinks,
            AccessLink accessLink
    ) {
        this.createLink = createLink;
        this.findLinkById = findLinkById;
        this.getAllLinks = getAllLinks;
        this.accessLink = accessLink;
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody @Valid LinkInput data) {
        var result = createLink.execute(data);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(result).toUri();
        return ResponseEntity.created(location).build();
    }
}
