import {Component, OnInit} from '@angular/core';
import {ArticleSnippetComponent} from "../article-snippet/article-snippet.component";
import {NgForOf, NgIf} from "@angular/common";
import {Router, RouterLink} from "@angular/router";
import {Article} from "../article";
import {ArticleListComponent} from "../article-list/article-list.component";
import {ArticleService} from "../article-service";
import {finalize} from "rxjs";

@Component({
  selector: 'app-latest-articles',
  standalone: true,
  templateUrl: './latest-articles.component.html',
  imports: [
    ArticleSnippetComponent,
    NgForOf,
    RouterLink,
    ArticleListComponent,
    NgIf
  ],
})
export class LatestArticlesComponent implements OnInit {
  articles?: Article[] = [];
  loadingArticles: boolean = true;

  constructor(
    private articleService: ArticleService,
    private router: Router
  ) {}

  ngOnInit(): void {
    this.articleService.fetchLatestArticles(5)
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
        },
      });
  }

  onViewArticle = (id: string) => {
    void this.router.navigate(["/articles", id]);
  }
}
