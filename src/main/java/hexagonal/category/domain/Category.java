package hexagonal.category.domain;

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
}
