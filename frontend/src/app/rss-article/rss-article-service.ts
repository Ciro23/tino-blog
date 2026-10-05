import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from "@angular/common/http";
import { map, Observable } from "rxjs";
import { RssArticle } from "./rss-article";
import { RssArticleSummary } from './rss-article-summary';
import { PageResult } from '../utilities/page-result';

@Injectable({
  providedIn: 'root'
})
export class RssArticleService {

  apiUrl = "/api/rss/articles"

  constructor(private http: HttpClient) { }

  /**
   * The RSS articles are cached to improve user experience and
   * reduce the server's workload!<br>
   * Use {@link reloadRssArticles} to evict the cache.
   * @param page The 0-based index of the page to load.
   */
  fetchRssArticles(page: number, size: number): Observable<PageResult<RssArticleSummary>> {
    return this.http.get<PageResult<RssArticleSummary>>(`${this.apiUrl}?page=${page}&size=${size}`).pipe(
      map(result => ({
        ...result,
        content: result.content.map(a => ({
          ...a,
          id: a.slug
        }))
      }))
    );
  }

  fetchRssArticleById(id: string): Observable<RssArticle> {
    return this.http.get<RssArticle>(`${this.apiUrl}/${id}`).pipe(
      map(a => ({
        ...a,
        id: a.slug
      }))
    );
  }

  reloadRssArticles(): Observable<HttpResponse<void>> {
    return this.http.post<void>(`${this.apiUrl}/reload`, {}, { observe: "response" })
  }
}
