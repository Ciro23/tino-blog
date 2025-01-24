import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {RssFeed} from "./rss-feed";
import {RssArticle} from "./rss-article";

@Injectable({
  providedIn: 'root'
})
export class RssService {

  apiUrl = "/api/rss"

  constructor(private http: HttpClient) {}

  fetchRssFeeds(): Observable<RssFeed[]> {
    return this.http.get<RssFeed[]>(`${this.apiUrl}/feeds`);
  }

  fetchRssFeedById(id: string): Observable<RssFeed> {
    return this.http.get<RssFeed>(`${this.apiUrl}/feeds/${id}`);
  }

  insertRssFeed(rssFeed: RssFeed): Observable<any> {
    return this.http.post<RssFeed>(this.apiUrl, rssFeed, { observe: "response" });
  }

  updateRssFeed(rssFeed: RssFeed): Observable<any> {
    return this.http.put<RssFeed>(`${this.apiUrl}/${rssFeed.id}`, rssFeed, { observe: "response" });
  }

  deleteRssFeed(id: string): Observable<any> {
    return this.http.delete(`${this.apiUrl}/feeds/${id}`, { observe: "response" });
  }

  /**
   * The RSS articles are cached to improve user experience and
   * reduce the server's workload!<br>
   * Use {@link reloadRssArticles} to evict the cache.
   */
  fetchRssArticles(): Observable<RssArticle[]> {
    return this.http.get<RssArticle[]>(`${this.apiUrl}/articles`);
  }

  fetchRssArticleById(id: string): Observable<RssArticle> {
    return this.http.get<RssArticle>(`${this.apiUrl}/articles/${id}`);
  }

  reloadRssArticles(): Observable<any> {
    return this.http.get(`${this.apiUrl}/articles/reload`, { observe: "response" })
  }
}
