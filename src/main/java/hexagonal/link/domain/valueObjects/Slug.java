package hexagonal.link.domain.valueObjects;

import java.util.Objects;
import java.util.Random;

public final class Slug {

    private final String value;

    public Slug(String value) {
        this.value = value;
    }

    public Slug() {
        this.value = generateRandomSlug();
    }

    private static String generateRandomSlug() {
        Random random = new Random();
        int size = random.nextInt(9 - 7 + 1) + 7;
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < size; i++) {
            char randomChar = (char) (random.nextInt(26) + 'a');
            sb.append(randomChar);
        }
        return sb.toString();
    }

    public String value() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Slug slug = (Slug) o;
        return Objects.equals(value, slug.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }
}
