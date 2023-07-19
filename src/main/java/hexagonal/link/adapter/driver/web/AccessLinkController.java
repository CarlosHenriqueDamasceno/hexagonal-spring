package hexagonal.link.adapter.driver.web;

import hexagonal.link.port.application.AccessLink;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Tag(name = "Access link")
public class AccessLinkController {
    private final AccessLink accessLink;

    public AccessLinkController(AccessLink accessLink) {
        this.accessLink = accessLink;
    }

    @GetMapping("/link/{slug}")
    public void access(@PathVariable String slug, HttpServletResponse httpServletResponse) {
        var link = accessLink.execute(slug);
        httpServletResponse.setHeader("Location", link.url());
        httpServletResponse.setStatus(302);
    }
}
