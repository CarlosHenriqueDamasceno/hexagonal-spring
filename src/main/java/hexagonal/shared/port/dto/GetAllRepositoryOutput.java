package hexagonal.shared.port.dto;

import java.util.List;

public record GetAllRepositoryOutput<T>(List<T> records, Long totalRecords) {
}
