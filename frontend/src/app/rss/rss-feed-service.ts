import { HttpClient } from "@angular/common/http";
import { Observable } from "rxjs";
import { RssFeed } from "./rss-feed";
import { Injectable } from "@angular/core";

@Injectable({
  providedIn: 'root'
})
export class RssFeedService {

  apiUrl = "/api/rss/feeds"

  constructor(private http: HttpClient) { }

  fetchRssFeeds(): Observable<RssFeed[]> {
    return this.http.get<RssFeed[]>(`${this.apiUrl}`);
  }

  fetchRssFeedById(id: string): Observable<RssFeed> {
    return this.http.get<RssFeed>(`${this.apiUrl}/${id}`);
  }

  insertRssFeed(rssFeed: RssFeed): Observable<any> {
    return this.http.post<RssFeed>(this.apiUrl, rssFeed, { observe: "response" });
  }

  updateRssFeed(rssFeed: RssFeed): Observable<any> {
    return this.http.put<RssFeed>(`${this.apiUrl}/${rssFeed.id}`, rssFeed, { observe: "response" });
  }

  deleteRssFeed(id: string): Observable<any> {
    return this.http.delete(`${this.apiUrl}/${id}`, { observe: "response" });
  }
}
