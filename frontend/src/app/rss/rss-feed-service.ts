import { HttpClient } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { Observable } from "rxjs";
import { CreateRssFeed } from "./create-rss-feed";
import { RssFeed } from "./rss-feed";

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

  insertRssFeed(rssFeed: CreateRssFeed): Observable<any> {
    return this.http.post<RssFeed>(this.apiUrl, rssFeed, { observe: "response" });
  }

  updateRssFeed(id: string, rssFeed: CreateRssFeed): Observable<any> {
    return this.http.put<RssFeed>(`${this.apiUrl}/${id}`, rssFeed, { observe: "response" });
  }

  deleteRssFeed(id: string): Observable<any> {
    return this.http.delete(`${this.apiUrl}/${id}`, { observe: "response" });
  }
}
