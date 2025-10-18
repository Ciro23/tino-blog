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
        RssFeedDetailDto dto = new RssFeedDetailDto();
        dto.setId(feed.getId());
        dto.setUrl(feed.getUrl());
        dto.setDescription(feed.getDescription());
        dto.setShowArticlesDescription(feed.isShowArticlesDescription());

        return dto;
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
        feed.setUrl(dto.getUrl());
        feed.setDescription(dto.getDescription());
        feed.setShowArticlesDescription(dto.isShowArticlesDescription());

        return feed;
    }
}
