package hexagonal.category.domain;

public enum PostingType {
    EXPENSE(1), REVENUE(2);
    private final int value;

    PostingType(int value) {
        this.value = value;
    }

    int getValue() {
        return this.value;
    }
}
