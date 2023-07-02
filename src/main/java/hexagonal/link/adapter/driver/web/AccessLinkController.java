package hexagonal.link.adapter.driver.web;

import hexagonal.link.port.application.AccessLink;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
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
