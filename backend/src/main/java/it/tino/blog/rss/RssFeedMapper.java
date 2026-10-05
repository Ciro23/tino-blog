package it.tino.blog.rss;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.jspecify.annotations.Nullable;
import org.springframework.stereotype.Component;
import org.springframework.web.util.HtmlUtils;

import com.rometools.rome.feed.synd.SyndContent;
import com.rometools.rome.feed.synd.SyndEntry;
import com.rometools.rome.feed.synd.SyndFeed;
import com.rometools.rome.feed.synd.SyndLink;

@Component
class RssMapper {

    public RssFeed feedToDto(String url, SyndFeed feed) {
        List<RssEntry> entryDtoList = entryToDtoList(url, feed.getEntries());
        return new RssFeed(url, entryDtoList);
    }

    private List<RssEntry> entryToDtoList(String feedUrl, Collection<SyndEntry> entries) {
        List<RssEntry> dtoList = new ArrayList<>();
        for (var syndEntry : entries) {
            var dto = entryToDto(feedUrl, syndEntry);
            dtoList.add(dto);
        }

        return dtoList;
    }

    private RssEntry entryToDto(String feedUrl, SyndEntry entry) {
        String content = parseEntryContent(entry);
        if (content == null) {
            content = "";
        }

        String description = parseEntryDescription(entry);
        if (description == null) {
            description = "";
        }

        String link = parseEntryLink(entry);

        // Relative URLs (e.g. images) must point to the original website.
        String baseUrl = !link.isBlank() ? link : feedUrl;
        content = RssContentCleaner.clean(content, baseUrl);

        Instant updatedDate = null;
        if (entry.getUpdatedDate() != null) {
            updatedDate = entry.getUpdatedDate()
                    .toInstant();
        }

        return new RssEntry(
            entry.getTitle(),
            description,
            content,
            link,
            entry.getPublishedDate()
                    .toInstant(),
            updatedDate
        );
    }

    /**
     * Some RSS feeds put the content inside the <content> tag, while others in
     * <description>.
     */
    private @Nullable String parseEntryContent(SyndEntry entry) {
        if (
            !entry.getContents()
                    .isEmpty()
        ) {
            return entry.getContents()
                    .getFirst()
                    .getValue();
        }

        if (
            entry.getContents()
                    .isEmpty() && entry.getDescription() != null
        ) {
            SyndContent description = entry.getDescription();
            if (isPlainText(description) && description.getValue() != null) {
                // Plain text must be wrapped to be styled like any other
                // paragraph.
                return "<p>" + HtmlUtils.htmlEscape(description.getValue()) + "</p>";
            }

            return description.getValue();
        }

        return null;
    }

    /**
     * Some feeds are "summary only", meaning they have no content, just a plain
     * text <summary>. In that case it's used as content and as description.
     */
    private @Nullable String parseEntryDescription(SyndEntry entry) {
        SyndContent description = entry.getDescription();
        if (description == null) {
            return null;
        }

        if (
            !entry.getContents()
                    .isEmpty() || isPlainText(description)
        ) {
            return description.getValue();
        }

        return null;
    }

    private boolean isPlainText(SyndContent content) {
        String type = content.getType();
        return "text".equals(type) || "text/plain".equals(type);
    }

    private String parseEntryLink(SyndEntry entry) {
        if (
            entry.getLink() != null && !entry.getLink()
                    .isBlank()
        ) {
            return entry.getLink();
        }

        return entry.getLinks()
                .stream()
                .filter(l -> l.getRel() == null || "alternate".equals(l.getRel()))
                .map(SyndLink::getHref)
                .filter(href -> href != null && !href.isBlank())
                .findFirst()
                .orElse("");
    }
}
