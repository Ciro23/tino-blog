package it.tino.blog.rss;

public class RssParsingException extends RuntimeException {

    public RssParsingException(String message, Exception e) {
        super(message, e);
    }
}
