package hexagonal.unit.link;

import hexagonal.link.domain.Link;
import hexagonal.link.domain.valueObjects.Slug;

import java.util.List;

public class LinkUnitTestUtils {

    public static final String validUrl = "https://www.google.com.br";
    public static final String validSlug = "45d3df0";
    public static final String invalidSlug = "sdawsdaswd";

    public static Link existentLink = Link.buildExistentLink(1L, validUrl, validSlug, 1L);
    public static Link nonExistentLink = Link.buildNonExistentLink(validUrl, validSlug, 1L);

    public static final String nullSlugErrorMessage = "O slug não pode ser nulo.";
    public static final String invalidLengthErrorMessage = "O slug deve ter entre" + Slug.minlength + " e " + Slug.maxlength + " caracteres.";
    public static final String linkNotFoundErrorMessage = "Link not found with id: " + existentLink.id() + ".";
    public static final String invalidSlugErrorMessage = "O slug informado já está em uso.";
    public static final String linkNotFoundWithSlugErrorMessage = "Não foi encontrado um link com o slug fornecido.";
    public static List<Link> listOfLinks = List.of(
            Link.buildExistentLink(1L, validUrl, validSlug, 1L),
            Link.buildExistentLink(2L, validUrl, "236s343", 2L),
            Link.buildExistentLink(3L, validUrl, "a1da3sw", 1L),
            Link.buildExistentLink(4L, validUrl, "23ss343", 1L)
    );
}
