import {Component, OnInit} from '@angular/core';
import {RssService} from "../rss.service";
import {ArticleListComponent} from "../../article/article-list/article-list.component";
import {NgIf} from "@angular/common";
import {finalize} from "rxjs";
import {Router} from "@angular/router";
import {AuthService} from "../../authentication/auth.service";
import {RssArticle} from "../rss-article";

@Component({
  selector: 'app-rss-aggregator',
  standalone: true,
  imports: [
    ArticleListComponent,
    NgIf
  ],
  templateUrl: './all-rss-articles.component.html'
})
export class AllRssArticlesComponent implements OnInit {
  articles?: RssArticle[] = [];
  loadingArticles: boolean = true;

  constructor(
    protected authService: AuthService,
    private rssService: RssService,
    private router: Router,
  ) {}

  ngOnInit(): void {
    this.fetchRssArticles();
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
