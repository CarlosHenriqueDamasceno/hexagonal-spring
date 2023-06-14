package hexagonal.link.port.application;

import hexagonal.link.port.dto.LinkOutput;

public interface AccessLink {
    LinkOutput execute(String slug);
}
