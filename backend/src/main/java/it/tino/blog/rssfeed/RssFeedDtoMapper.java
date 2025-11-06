package it.tino.blog.rssfeed;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import org.springframework.stereotype.Component;

@Component
class RssFeedDtoMapper {

    public RssFeedDetailDto toDetailDto(RssFeed feed) {
        return new RssFeedDetailDto(
            feed.getId(),
            feed.getUrl(),
            feed.getDescription(),
            feed.isShowArticlesDescription()
        );
    }

    public List<RssFeedDetailDto> toListDto(Collection<RssFeed> feeds) {
        Set<RssFeed> sortedFeeds = new TreeSet<>(feeds);
        List<RssFeedDetailDto> dtoList = new ArrayList<>();

        for (RssFeed feed : sortedFeeds) {
            RssFeedDetailDto dto = toDetailDto(feed);
            dtoList.add(dto);
        }

        return dtoList;
    }

    public RssFeed toDomain(SaveRssFeedDto dto) {
        RssFeed feed = new RssFeed();
        feed.setUrl(dto.url());
        feed.setDescription(dto.description());
        feed.setShowArticlesDescription(dto.showArticlesDescription());

        return feed;
    }
}
