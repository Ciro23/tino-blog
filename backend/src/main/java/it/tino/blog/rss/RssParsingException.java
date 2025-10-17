package it.tino.blog.rss;

class RssParsingException extends RuntimeException {

    public RssParsingException(String message, Exception e) {
        super(message, e);
    }
}
