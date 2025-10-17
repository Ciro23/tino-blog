package it.tino.blog.rssarticle;

class RssParsingException extends RuntimeException {

    public RssParsingException(String message, Exception e) {
        super(message, e);
    }
}
