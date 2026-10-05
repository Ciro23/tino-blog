import { HttpClient, HttpResponse } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { map, Observable } from "rxjs";
import { CreateRssFeed } from "./create-rss-feed";
import { RssFeed } from "./rss-feed";
import { MAX_PAGE_SIZE, PageResult } from "../utilities/page-result";

@Injectable({
  providedIn: 'root'
})
export class RssFeedService {

  apiUrl = "/api/rss/feeds"

  constructor(private http: HttpClient) { }

  /**
   * Only the first {@link MAX_PAGE_SIZE} feeds are loaded,
   * since there is no pagination UI yet.
   */
  fetchRssFeeds(): Observable<RssFeed[]> {
    return this.http.get<PageResult<RssFeed>>(`${this.apiUrl}?page=0&size=${MAX_PAGE_SIZE}`).pipe(
      map(page => page.content)
    );
  }

  fetchRssFeedById(id: string): Observable<RssFeed> {
    return this.http.get<RssFeed>(`${this.apiUrl}/${id}`);
  }

  insertRssFeed(rssFeed: CreateRssFeed): Observable<HttpResponse<RssFeed>> {
    return this.http.post<RssFeed>(this.apiUrl, rssFeed, { observe: "response" });
  }

  updateRssFeed(id: string, rssFeed: CreateRssFeed): Observable<HttpResponse<RssFeed>> {
    return this.http.put<RssFeed>(`${this.apiUrl}/${id}`, rssFeed, { observe: "response" });
  }

  deleteRssFeed(id: string): Observable<HttpResponse<void>> {
    return this.http.delete<void>(`${this.apiUrl}/${id}`, { observe: "response" });
  }
}
