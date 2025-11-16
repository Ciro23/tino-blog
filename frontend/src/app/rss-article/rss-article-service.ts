import { Injectable } from '@angular/core';
import { HttpClient } from "@angular/common/http";
import { Observable } from "rxjs";
import { RssArticle } from "./rss-article";

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
   */
  fetchRssArticles(): Observable<RssArticle[]> {
    return this.http.get<RssArticle[]>(`${this.apiUrl}`);
  }

  fetchRssArticleById(id: string): Observable<RssArticle> {
    return this.http.get<RssArticle>(`${this.apiUrl}/${id}`);
  }

  reloadRssArticles(): Observable<any> {
    return this.http.post(`${this.apiUrl}/reload`, {}, { observe: "response" })
  }
}
