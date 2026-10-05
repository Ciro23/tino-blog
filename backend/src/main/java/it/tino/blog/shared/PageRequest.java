package it.tino.blog.shared;

public final class PageRequest {

    public static final int DEFAULT_SIZE = 20;
    public static final int MAX_SIZE = 100;

    private final int page;
    private final int size;

    private PageRequest(int page, int size) {
        if (!isValid(page, size)) {
            throw new InvalidPageRequestException(page, size);
        }

        this.page = page;
        this.size = size;
    }

    public static PageRequest of(int page, int size) {
        return new PageRequest(page, size);
    }

    /**
     * @return {@code true} if the page is not negative and the size is
     *         between 1 and {@link #MAX_SIZE}, both inclusive.
     */
    public static boolean isValid(int page, int size) {
        return page >= 0 && size > 0 && size <= MAX_SIZE;
    }

    public int page() {
        return page;
    }

    public int size() {
        return size;
    }

    public long offset() {
        return (long) page * size;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + page;
        result = prime * result + size;
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        PageRequest other = (PageRequest) obj;
        if (page != other.page)
            return false;
        if (size != other.size)
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "PageRequest [page=" + page + ", size=" + size + ", page()=" + page() + ", size()="
                + size() + ", offset()=" + offset() + "]";
    }
}
