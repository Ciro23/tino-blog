package it.tino.blog.shared;

import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class PageResultTest {

    private final List<Integer> numbers = List.of(1, 2, 3, 4, 5);

    @Test
    public void shouldRoundTotalPagesUp() {
        PageResult<Integer> result = PageResult.of(List.of(1, 2), 0, 2, 5);
        Assertions.assertEquals(3, result.totalPages());
    }

    @Test
    public void testTotalPagesWithExactMultiple() {
        PageResult<Integer> result = PageResult.of(List.of(1, 2), 0, 2, 4);
        Assertions.assertEquals(2, result.totalPages());
    }

    @Test
    public void testTotalPagesWithoutElements() {
        PageResult<Integer> result = PageResult.of(List.of(), 0, 2, 0);
        Assertions.assertEquals(0, result.totalPages());
    }

    @Test
    public void shouldSliceFirstPage() {
        PageResult<Integer> result = PageResult.slice(numbers, PageRequest.of(0, 2));
        Assertions.assertEquals(List.of(1, 2), result.content());
        Assertions.assertEquals(5, result.totalElements());
        Assertions.assertEquals(3, result.totalPages());
    }

    @Test
    public void shouldSliceMiddlePage() {
        PageResult<Integer> result = PageResult.slice(numbers, PageRequest.of(1, 2));
        Assertions.assertEquals(List.of(3, 4), result.content());
    }

    @Test
    public void shouldSlicePartialLastPage() {
        PageResult<Integer> result = PageResult.slice(numbers, PageRequest.of(2, 2));
        Assertions.assertEquals(List.of(5), result.content());
    }

    @Test
    public void shouldReturnEmptyContentPastLastPage() {
        PageResult<Integer> result = PageResult.slice(numbers, PageRequest.of(10, 2));
        Assertions.assertEquals(List.of(), result.content());
        Assertions.assertEquals(10, result.page());
        Assertions.assertEquals(5, result.totalElements());
        Assertions.assertEquals(3, result.totalPages());
    }

    @Test
    public void testSliceEmptyList() {
        PageResult<Integer> result = PageResult.slice(List.of(), PageRequest.of(0, 2));
        Assertions.assertEquals(List.of(), result.content());
        Assertions.assertEquals(0, result.totalElements());
        Assertions.assertEquals(0, result.totalPages());
    }

    @Test
    public void shouldKeepPaginationDataWhenReplacingContent() {
        PageResult<Integer> numbersPage = PageResult.slice(numbers, PageRequest.of(1, 2));
        PageResult<String> result = numbersPage.withContent(List.of("3", "4"));

        Assertions.assertEquals(List.of("3", "4"), result.content());
        Assertions.assertEquals(1, result.page());
        Assertions.assertEquals(2, result.size());
        Assertions.assertEquals(5, result.totalElements());
        Assertions.assertEquals(3, result.totalPages());
    }
}
