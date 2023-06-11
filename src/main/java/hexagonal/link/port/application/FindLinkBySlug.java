package hexagonal.link.port.application;

import hexagonal.link.port.dto.LinkOutput;

public interface FindLinkBySlug {
    LinkOutput execute(String slug);
}
