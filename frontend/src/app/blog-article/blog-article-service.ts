import { HttpClient } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { Observable } from "rxjs";
import { Article } from "../article/article";
import { SaveArticle } from "./save-article";

@Injectable({
  providedIn: 'root'
})
export class BlogArticleService {

  apiUrl = "/api/articles"

  constructor(private http: HttpClient) { }

  fetchArticles(): Observable<any> {
    return this.http.get(this.apiUrl);
  }

  fetchLatestArticles(limit: number): Observable<any> {
    return this.http.get(`${this.apiUrl}?limit=${limit}`);
  }

  fetchArticleById(id: string): Observable<any> {
    return this.http.get(`${this.apiUrl}/${id}`);
  }

  insertArticle(article: SaveArticle): Observable<any> {
    return this.http.post<Article>(this.apiUrl, article, { observe: "response" });
  }

  updateArticle(id: string, article: SaveArticle): Observable<any> {
    return this.http.put<Article>(`${this.apiUrl}/${id}`, article, { observe: "response" });
  }

  deleteArticle(id: string): Observable<any> {
    return this.http.delete(`${this.apiUrl}/${id}`, { observe: "response" });
  }
}
