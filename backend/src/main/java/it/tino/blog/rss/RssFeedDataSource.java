package it.tino.blog.rss;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class RssFeedDataSource implements RssFeedRepository {

    private final RssFeedDao rssFeedDao;

    @Override
    public RssFeed save(RssFeed rssFeed) {
        SpringRssFeed entity = domainToDb(rssFeed);
        return dbToDomain(rssFeedDao.save(entity));
    }

    @Override
    public List<RssFeed> findAll() {
        return dbToDomain(rssFeedDao.findAll());
    }

    @Override
    public Optional<RssFeed> findById(UUID id) {
        return rssFeedDao.findById(id)
                .map(this::dbToDomain);
    }

    @Override
    public boolean deleteById(UUID id) {
        if (rssFeedDao.existsById(id)) {
            rssFeedDao.deleteById(id);
            return true;
        }
        return false;
    }

    private SpringRssFeed domainToDb(RssFeed domain) {
        SpringRssFeed db = new SpringRssFeed();
        db.setId(domain.getId());
        db.setUrl(domain.getUrl());
        db.setDescription(domain.getDescription());
        db.setShowArticlesDescription(domain.isShowArticlesDescription());

        return db;
    }

    private RssFeed dbToDomain(SpringRssFeed db) {
        RssFeed domain = new RssFeed();
        domain.setId(db.getId());
        domain.setUrl(db.getUrl());
        domain.setDescription(db.getDescription());
        domain.setShowArticlesDescription(db.isShowArticlesDescription());

        return domain;
    }

    private List<RssFeed> dbToDomain(Collection<SpringRssFeed> rssFeeds) {
        return rssFeeds.stream()
                .map(this::dbToDomain)
                .collect(Collectors.toList());
    }
}
