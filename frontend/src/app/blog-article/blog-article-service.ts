import { HttpClient, HttpResponse } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { Observable } from "rxjs";
import { SaveArticle } from "./save-article";
import { BlogArticle } from "./blog-article";
import { BlogArticleSummary } from "./blog-article-summary";

@Injectable({
  providedIn: 'root'
})
export class BlogArticleService {

  apiUrl = "/api/articles"

  constructor(private http: HttpClient) { }

  fetchArticles(): Observable<BlogArticleSummary[]> {
    return this.http.get<BlogArticle[]>(this.apiUrl);
  }

  fetchLatestArticles(limit: number): Observable<BlogArticleSummary[]> {
    return this.http.get<BlogArticle[]>(`${this.apiUrl}?limit=${limit}`);
  }

  fetchArticleById(id: string): Observable<BlogArticle> {
    return this.http.get<BlogArticle>(`${this.apiUrl}/${id}`);
  }

  insertArticle(article: SaveArticle): Observable<HttpResponse<BlogArticle>> {
    return this.http.post<BlogArticle>(this.apiUrl, article, { observe: "response" });
  }

  updateArticle(id: string, article: SaveArticle): Observable<HttpResponse<BlogArticle>> {
    return this.http.put<BlogArticle>(`${this.apiUrl}/${id}`, article, { observe: "response" });
  }

  deleteArticle(id: string): Observable<HttpResponse<void>> {
    return this.http.delete<void>(`${this.apiUrl}/${id}`, { observe: "response" });
  }
}
