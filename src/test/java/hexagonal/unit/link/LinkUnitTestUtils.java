package hexagonal.unit.link;

import hexagonal.link.domain.Link;
import hexagonal.link.domain.valueObjects.Slug;

public class LinkUnitTestUtils {

    public static final String validUrl = "https://www.google.com.br";
    public static final String validSlug = "45d3df0";
    public static final String invalidSlug = "sdawsdaswd";

    public static final String nullSlugErrorMessage = "The slug can not be null";
    public static final String invalidLengthErrorMessage = "The slug length has to be between " + Slug.minlength + " and " + Slug.maxlength;

    public static Link existentLink = Link.buildExistentLink(1L, validUrl, validSlug);
    public static Link nonExistentLink = Link.buildNonExistentLink(validUrl, validSlug);
}
