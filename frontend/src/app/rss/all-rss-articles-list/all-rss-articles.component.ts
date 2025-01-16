import {Component, OnInit} from '@angular/core';
import {RssService} from "../rss.service";
import {Article} from "../../article/article";
import {ArticleListComponent} from "../../article/article-list/article-list.component";
import {RssArticleDetailsComponent} from "../rss-article-details/rss-article-details.component";
import {NgIf} from "@angular/common";
import {filter, finalize, Subscription} from "rxjs";
import {ActivatedRoute, NavigationEnd, Router} from "@angular/router";
import {AuthService} from "../../authentication/auth.service";
import {RssArticle} from "../rss-article";

@Component({
  selector: 'app-rss-aggregator',
  standalone: true,
  imports: [
    ArticleListComponent,
    RssArticleDetailsComponent,
    NgIf
  ],
  templateUrl: './all-rss-articles.component.html'
})
export class AllRssArticlesComponent implements OnInit {
  articles?: RssArticle[] = [];
  loadingArticles: boolean = true;

  selectedArticle?: RssArticle;
  selectedArticleId?: string;

  private routerSubscription!: Subscription;

  constructor(
    protected authService: AuthService,
    private rssService: RssService,
    private router: Router,
    private route: ActivatedRoute
  ) {
    this.selectedArticleId = this.route.snapshot.paramMap.get('id') ?? undefined;
  }

  ngOnInit(): void {
    this.fetchRssArticles();

    this.routerSubscription = this.router.events.pipe(
      filter(event =>
        (event instanceof NavigationEnd) ||
        (this.router.getCurrentNavigation()?.trigger === 'popstate')
      )
    ).subscribe(() => {
      this.closeArticle();
    });
  }

  openArticle = (id: string): void => {
    window.scrollTo({ top: 0, left: 0, behavior: 'instant' });

    if (window.history.state?.url !== `/rss-aggregator/${id}`) {
      window.history.pushState({ url: `/rss-aggregator/${id}` }, "", `/rss-aggregator/${id}`);
    }

    this.selectedArticle = this.articles!.filter((a) => a.id == id)[0];
  }

  reloadRssArticles() {
    this.articles = [];
    this.loadingArticles = true;

    this.rssService.reloadRssArticles()
      .subscribe({
        next: success => {
          if (!success) {
            return;
          }
          this.fetchRssArticles();
        },
        error: () => {
          this.articles = undefined;
        }
      });
  }

  fetchRssArticles() {
    this.rssService.fetchRssArticles()
      .pipe(
        finalize(() => {
          this.loadingArticles = false;
        })
      )
      .subscribe({
        next: articles => {
          this.articles = articles;

          if (this.selectedArticleId != undefined) {
            this.openArticle(this.selectedArticleId!);
          }
        },
        error: () => {
          this.articles = undefined;
        }
      });
  }

  private closeArticle() {
    window.scrollTo({ top: 0, left: 0, behavior: 'instant' });
    this.selectedArticle = undefined;
  }
}
