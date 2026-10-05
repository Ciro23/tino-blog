import { Component, OnInit } from '@angular/core';
import { RssArticleService } from "../rss-article-service";
import { ArticleListComponent } from "../../article/article-list/article-list.component";
import { catchError, combineLatest, finalize, map, of, startWith, Subject, switchMap } from "rxjs";
import { ActivatedRoute, Router } from "@angular/router";
import { AuthService } from "../../authentication/auth.service";
import { RssArticleSummary } from '../rss-article-summary';
import { ARTICLES_PAGE_SIZE, PageResult, pageFromQueryParam } from '../../utilities/page-result';

@Component({
  selector: 'app-all-rss-articles',
  standalone: true,
  imports: [
    ArticleListComponent,
  ],
  templateUrl: './all-rss-articles.component.html'
})
export class AllRssArticlesComponent implements OnInit {
  articles?: RssArticleSummary[] = [];
  loadingArticles: boolean = true;

  currentPage: number = 0;
  totalElements: number = 0;
  readonly pageSize: number = ARTICLES_PAGE_SIZE;

  /**
   * Emits to load the current page again, e.g. after the cache is reloaded.
   */
  private readonly refresh$ = new Subject<void>();

  get categoryNamesByArticleId(): Map<string, string> | undefined {
    if (this.articles === undefined) {
      return undefined;
    }
    return new Map(
      this.articles.map(article => [article.id, article.feedTitle])
    );
  }

  constructor(
    protected authService: AuthService,
    private rssArticleService: RssArticleService,
    private router: Router,
    private route: ActivatedRoute,
  ) { }

  ngOnInit(): void {
    // The page is stored in the URL, so that it's kept when going back
    // and forth between the list and an article.
    const page$ = this.route.queryParamMap
      .pipe(
        map(params => pageFromQueryParam(params.get("page")))
      );

    combineLatest([page$, this.refresh$.pipe(startWith(undefined))])
      .pipe(
        // Cancels the request of the previous page when changing it quickly.
        switchMap(([page]) => {
          this.loadingArticles = true;
          return this.rssArticleService.fetchRssArticles(page, this.pageSize)
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

  reloadRssArticles() {
    this.rssArticleService.reloadRssArticles()
      .subscribe({
        next: response => {
          if (response.status !== 204) {
            return;
          }
          this.refresh$.next();
        },
        error: () => {
          this.articles = undefined;
        }
      });
  }

  onViewRssArticle = (slug: string) => {
    void this.router.navigate(["/rss/articles", slug]);
  }

  onPageChange = (page: number, replaceUrl: boolean = false) => {
    void this.router.navigate([], {
      relativeTo: this.route,
      // The first page has no parameter, so that its URL is always "/rss".
      queryParams: { page: page > 0 ? page + 1 : null },
      queryParamsHandling: "merge",
      replaceUrl: replaceUrl,
    });
  }

  /**
   * @param result Undefined only if an error occurred.
   */
  private showPage(result?: PageResult<RssArticleSummary>) {
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
