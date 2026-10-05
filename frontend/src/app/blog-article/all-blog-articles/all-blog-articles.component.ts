import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from "@angular/router";
import { ArticleListComponent } from "../../article/article-list/article-list.component";
import { BlogArticleService } from "../blog-article-service";
import { catchError, finalize, map, of, switchMap } from "rxjs";
import { BlogArticleSummary } from '../blog-article-summary';
import { ARTICLES_PAGE_SIZE, PageResult, pageFromQueryParam } from '../../utilities/page-result';

@Component({
  selector: 'app-all-blog-articles',
  standalone: true,
  imports: [
    ArticleListComponent
  ],
  templateUrl: './all-blog-articles.component.html',
})
export class AllBlogArticlesComponent implements OnInit {
  articles?: BlogArticleSummary[] = [];
  loadingArticles: boolean = true;

  currentPage: number = 0;
  totalElements: number = 0;
  readonly pageSize: number = ARTICLES_PAGE_SIZE;

  constructor(
    private blogArticleService: BlogArticleService,
    private router: Router,
    private route: ActivatedRoute,
  ) { }

  ngOnInit(): void {
    // The page is stored in the URL, so that it's kept when going back
    // and forth between the list and an article.
    this.route.queryParamMap
      .pipe(
        map(params => pageFromQueryParam(params.get("page"))),
        // Cancels the request of the previous page when changing it quickly.
        switchMap(page => {
          this.loadingArticles = true;
          return this.blogArticleService.fetchArticles(page, this.pageSize)
            .pipe(
              catchError(() => of(undefined)),
              finalize(() => {
                this.loadingArticles = false;
              })
            );
        })
      )
      .subscribe(result => this.showPage(result));
  }

  onViewArticle = (slug: string) => {
    void this.router.navigate(["/articles", slug]);
  }

  onPageChange = (page: number, replaceUrl: boolean = false) => {
    void this.router.navigate([], {
      relativeTo: this.route,
      // The first page has no parameter, so that its URL is always "/articles".
      queryParams: { page: page > 0 ? page + 1 : null },
      queryParamsHandling: "merge",
      replaceUrl: replaceUrl,
    });
  }

  /**
   * @param result Undefined only if an error occurred.
   */
  private showPage(result?: PageResult<BlogArticleSummary>) {
    if (result === undefined) {
      this.articles = undefined;
      return;
    }

    // The requested page doesn't exist anymore, e.g. from an old link.
    if (result.content.length === 0 && result.totalPages > 0) {
      this.onPageChange(result.totalPages - 1, true);
      return;
    }

    this.articles = result.content;
    this.currentPage = result.page;
    this.totalElements = result.totalElements;
  }
}
