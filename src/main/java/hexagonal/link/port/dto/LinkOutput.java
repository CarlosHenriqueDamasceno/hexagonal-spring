package hexagonal.link.port.dto;

import hexagonal.link.domain.Link;

public record LinkOutput(Long id, String url, String slug, Long accesses) {
    public static LinkOutput fromEntity(Link entity) {
        return new LinkOutput(entity.id(), entity.url(), entity.slug().value(), entity.accesses());
    }
}
