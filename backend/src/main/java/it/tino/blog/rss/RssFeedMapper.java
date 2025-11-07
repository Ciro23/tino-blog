package it.tino.blog.rss;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.jspecify.annotations.Nullable;
import org.springframework.stereotype.Component;

import com.rometools.rome.feed.synd.SyndEntry;
import com.rometools.rome.feed.synd.SyndFeed;

@Component
class RssMapper {

    public RssFeed feedToDto(String url, SyndFeed feed) {
        List<RssEntry> entryDtoList = entryToDtoList(feed.getEntries());
        return new RssFeed(url, entryDtoList);
    }

    private List<RssEntry> entryToDtoList(Collection<SyndEntry> entries) {
        List<RssEntry> dtoList = new ArrayList<>();
        for (var syndEntry : entries) {
            var dto = entryToDto(syndEntry);
            dtoList.add(dto);
        }

        return dtoList;
    }

    private RssEntry entryToDto(SyndEntry entry) {
        String content = parseEntryContent(entry);
        if (content == null) {
            content = "";
        }

        String description = parseEntryDescription(entry);
        if (description == null) {
            description = "";
        }

        Instant updatedDate = null;
        if (entry.getUpdatedDate() != null) {
            updatedDate = entry.getUpdatedDate()
                    .toInstant();
        }

        return new RssEntry(
            entry.getTitle(),
            description,
            content,
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
            return entry.getDescription()
                    .getValue();
        }

        return null;
    }

    private @Nullable String parseEntryDescription(SyndEntry entry) {
        if (
            !entry.getContents()
                    .isEmpty() && entry.getDescription() != null
        ) {
            return entry.getDescription()
                    .getValue();
        }

        return null;
    }
}
