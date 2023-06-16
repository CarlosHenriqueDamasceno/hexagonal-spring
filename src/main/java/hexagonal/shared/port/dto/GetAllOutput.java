package hexagonal.shared.port.dto;

import java.util.List;

public class GetAllOutput<T> {

    private final List<T> data;
    private final int pageSize;
    private final int page;
    private final Long totalRecords;

    public GetAllOutput(List<T> data, int pageSize, int page, Long totalRecords) {
        this.data = data;
        this.pageSize = pageSize;
        this.page = page;
        this.totalRecords = totalRecords;
    }

    public List<T> data() {
        return data;
    }

    public Pagination pagination() {
        return new Pagination(pageSize, page, totalRecords);
    }

    public record Pagination(int pageSize,
                             int page,
                             Long totalRecords) {
    }

    public List<T> getData() {
        return data;
    }

    public int getPageSize() {
        return pageSize;
    }

    public int getPage() {
        return page;
    }

    public Long getTotalRecords() {
        return totalRecords;
    }
}
