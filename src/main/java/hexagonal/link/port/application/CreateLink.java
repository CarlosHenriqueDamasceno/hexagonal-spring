package hexagonal.link.port.application;

import hexagonal.link.port.dto.LinkOutput;

public interface CreateLink {
    LinkOutput execute(LinkInput input);

    record LinkInput(String url, String slug) {
    }
}
