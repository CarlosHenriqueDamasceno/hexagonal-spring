package hexagonal.link.port.application;

import hexagonal.link.port.dto.LinkOutput;

public interface FindLinkById {
    LinkOutput execute(long id);
}
