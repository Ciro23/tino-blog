package it.tino.blog.rssfeed;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;

import it.tino.blog.shared.PageRequest;
import it.tino.blog.shared.PageResult;

@Repository
class RssFeedDataSource implements RssFeedRepository {

    private final RssFeedDao rssFeedDao;

    public RssFeedDataSource(RssFeedDao rssFeedDao) {
        this.rssFeedDao = rssFeedDao;
    }

    @Override
    public RssFeed save(RssFeed rssFeed) {
        SpringRssFeed entity = domainToDb(rssFeed);
        return dbToDomain(rssFeedDao.save(entity));
    }

    @Override
    public List<RssFeed> findAll() {
        Sort sorting = getSorting();
        return dbToDomain(rssFeedDao.findAll(sorting));
    }

    @Override
    public PageResult<RssFeed> findPage(PageRequest pageRequest) {
        Sort sorting = getSorting();
        var pageable = org.springframework.data.domain.PageRequest
                .of(pageRequest.page(), pageRequest.size(), sorting);

        Page<SpringRssFeed> page = rssFeedDao.findAll(pageable);
        return PageResult.of(
            dbToDomain(page.getContent()),
            pageRequest.page(),
            pageRequest.size(),
            page.getTotalElements()
        );
    }

    @Override
    public Optional<RssFeed> findById(UUID id) {
        return rssFeedDao.findById(id)
                .map(this::dbToDomain);
    }

    @Override
    public List<RssFeed> findByIdIn(Collection<UUID> ids) {
        if (ids.isEmpty()) {
            return Collections.emptyList();
        }

        Sort sorting = getSorting();
        return dbToDomain(rssFeedDao.findAllByIdIn(ids, sorting));
    }

    @Override
    public boolean deleteById(UUID id) {
        if (rssFeedDao.existsById(id)) {
            rssFeedDao.deleteById(id);
            return true;
        }
        return false;
    }

    private Sort getSorting() {
        // The id makes the order deterministic, so pages never overlap.
        return Sort.by("title")
                .and(Sort.by("id"));
    }

    private SpringRssFeed domainToDb(RssFeed domain) {
        SpringRssFeed db = new SpringRssFeed();
        db.setId(domain.getId());
        db.setUrl(domain.getUrl());
        db.setTitle(domain.getTitle());
        db.setShowArticlesDescription(domain.isShowArticlesDescription());

        return db;
    }

    private RssFeed dbToDomain(SpringRssFeed db) {
        RssFeed domain = new RssFeed();
        domain.setId(db.getId());
        domain.setUrl(db.getUrl());
        domain.setTitle(db.getTitle());
        domain.setShowArticlesDescription(db.isShowArticlesDescription());

        return domain;
    }

    private List<RssFeed> dbToDomain(Collection<SpringRssFeed> rssFeeds) {
        return rssFeeds.stream()
                .map(this::dbToDomain)
                .collect(Collectors.toList());
    }
}
