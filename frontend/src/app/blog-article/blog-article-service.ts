import { HttpClient, HttpResponse } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { map, Observable } from "rxjs";
import { SaveArticle } from "./save-article";
import { BlogArticle } from "./blog-article";
import { BlogArticleSummary } from "./blog-article-summary";
import { PageResult } from "../utilities/page-result";

@Injectable({
  providedIn: 'root'
})
export class BlogArticleService {

  apiUrl = "/api/articles"

  constructor(private http: HttpClient) { }

  /**
   * @param page The 0-based index of the page to load.
   */
  fetchArticles(page: number, size: number): Observable<PageResult<BlogArticleSummary>> {
    return this.http.get<PageResult<BlogArticleSummary>>(`${this.apiUrl}?page=${page}&size=${size}`);
  }

  fetchLatestArticles(limit: number): Observable<BlogArticleSummary[]> {
    return this.http.get<PageResult<BlogArticleSummary>>(`${this.apiUrl}?page=0&size=${limit}`).pipe(
      map(page => page.content)
    );
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
