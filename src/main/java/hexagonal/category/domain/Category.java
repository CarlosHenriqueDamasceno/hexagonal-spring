package hexagonal.category.domain;

import java.util.Objects;

public class Category {
    private final Long id;
    private final String name;
    private final PostingType type;

    private Category(Long id, String name, PostingType type) {
        this.id = id;
        this.name = name;
        this.type = type;
    }

    public static Category buildExistentCategory(Long id, String name, PostingType type) {
        return new Category(id, name, type);
    }

    public static Category buildNonExistentCategory(String name, PostingType type) {
        return new Category(null, name, type);
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public PostingType getType() {
        return type;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Category category = (Category) o;
        return Objects.equals(id, category.id) && Objects.equals(
                name,
                category.name
        ) && type == category.type;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, type);
    }
}
