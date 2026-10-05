package it.tino.blog.shared;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class PageRequestTest {

    @Test
    public void shouldRejectNegativePage() {
        Assertions.assertFalse(PageRequest.isValid(-1, 10));
    }

    @Test
    public void shouldAcceptFirstPage() {
        Assertions.assertTrue(PageRequest.isValid(0, 10));
    }

    @Test
    public void shouldRejectZeroSize() {
        Assertions.assertFalse(PageRequest.isValid(0, 0));
    }

    @Test
    public void shouldAcceptSizeBetweenOneAndMax() {
        Assertions.assertTrue(PageRequest.isValid(0, 1));
        Assertions.assertTrue(PageRequest.isValid(0, PageRequest.MAX_SIZE));
    }

    @Test
    public void shouldRejectSizeAboveMax() {
        Assertions.assertFalse(PageRequest.isValid(0, PageRequest.MAX_SIZE + 1));
    }

    @Test
    public void shouldNotCreateInvalidRequest() {
        Assertions.assertThrows(
            InvalidPageRequestException.class,
            () -> PageRequest.of(-1, 10)
        );
    }

    @Test
    public void testOffset() {
        Assertions.assertEquals(0, PageRequest.of(0, 20).offset());
        Assertions.assertEquals(60, PageRequest.of(3, 20).offset());
    }
}
