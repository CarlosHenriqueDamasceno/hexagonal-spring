package hexagonal.link.application.useCases.ports;

public interface CreateLink {
    LinkOutput execute(LinkInput input);

    record LinkInput(String url, String slug) {
    }
}
