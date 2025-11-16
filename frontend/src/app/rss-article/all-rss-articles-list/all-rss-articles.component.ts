import { Component, OnInit } from '@angular/core';
import { RssArticleService } from "../rss-article-service";
import { ArticleListComponent } from "../../article/article-list/article-list.component";
import { finalize } from "rxjs";
import { Router } from "@angular/router";
import { AuthService } from "../../authentication/auth.service";
import { RssArticle } from "../rss-article";

@Component({
  selector: 'app-all-rss-articles',
  standalone: true,
  imports: [
    ArticleListComponent,
  ],
  templateUrl: './all-rss-articles.component.html'
})
export class AllRssArticlesComponent implements OnInit {
  articles?: RssArticle[] = [];
  loadingArticles: boolean = true;

  constructor(
    protected authService: AuthService,
    private rssArticleService: RssArticleService,
    private router: Router,
  ) { }

  ngOnInit(): void {
    this.fetchRssArticles();
  }

  reloadRssArticles() {
    this.rssArticleService.reloadRssArticles()
      .subscribe({
        next: response => {
          if (response.status !== 204) {
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
    this.loadingArticles = true;
    this.rssArticleService.fetchRssArticles()
      .pipe(
        finalize(() => {
          this.loadingArticles = false;
        })
      )
      .subscribe({
        next: articles => {
          this.articles = articles;
        },
        error: () => {
          this.articles = undefined;
        }
      });
  }

  onViewRssArticle = (slug: string) => {
    void this.router.navigate(["/rss/articles", slug]);
  }
}
