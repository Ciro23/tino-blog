package it.tino.blog.shared;

import java.util.List;

public record PageResult<T>(
    List<T> content,
    int page,
    int size,
    long totalElements,
    int totalPages
) {

    public static <T> PageResult<T> of(List<T> content, int page, int size, long totalElements) {
        if (size <= 0) {
            throw new IllegalArgumentException("size must be positive");
        }

        int totalPages = (int) ((totalElements + size - 1) / size);
        return new PageResult<>(content, page, size, totalElements, totalPages);
    }

    /**
     * Extracts the requested page from a list which is already fully loaded
     * and sorted.
     */
    public static <T> PageResult<T> slice(List<T> all, PageRequest pageRequest) {
        int totalElements = all.size();
        int fromIndex = (int) Math.min(pageRequest.offset(), totalElements);
        int toIndex = (int) Math.min(pageRequest.offset() + pageRequest.size(), totalElements);

        List<T> content = List.copyOf(all.subList(fromIndex, toIndex));
        return of(content, pageRequest.page(), pageRequest.size(), totalElements);
    }

    /**
     * @return A page with the same pagination data but different content,
     *         e.g. to convert domain objects into DTOs.
     */
    public <R> PageResult<R> withContent(List<R> content) {
        return new PageResult<>(content, page, size, totalElements, totalPages);
    }
}
