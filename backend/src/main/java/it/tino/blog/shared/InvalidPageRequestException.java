package it.tino.blog.shared;

public class InvalidPageRequestException extends RuntimeException {

    private final int page;
    private final int size;

    public InvalidPageRequestException(int page, int size) {
        super("Invalid page request: page=" + page + ", size=" + size);
        this.page = page;
        this.size = size;
    }

    public int page() {
        return page;
    }

    public int size() {
        return size;
    }
}
