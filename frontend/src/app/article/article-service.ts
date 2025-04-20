import { HttpClient } from "@angular/common/http";
import { Article } from "./article";
import { Injectable } from "@angular/core";
import { Observable } from "rxjs";

@Injectable({
  providedIn: 'root'
})
export class ArticleService {

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

  insertArticle(article: Article): Observable<any> {
    return this.http.post<Article>(this.apiUrl, article, { observe: "response" });
  }

  updateArticle(article: Article): Observable<any> {
    return this.http.put<Article>(`${this.apiUrl}/${article.id}`, article, { observe: "response" });
  }

  deleteArticle(id: string): Observable<any> {
    return this.http.delete(`${this.apiUrl}/${id}`, { observe: "response" });
  }
}
