import { Component, OnInit } from '@angular/core';
import { Router } from "@angular/router";
import { Article } from "../article";
import { ArticleListComponent } from "../article-list/article-list.component";
import { ArticleService } from "../article-service";
import { finalize } from "rxjs";

@Component({
  selector: 'app-all-articles',
  standalone: true,
  imports: [
    ArticleListComponent
  ],
  templateUrl: './all-articles.component.html',
})
export class AllArticlesComponent implements OnInit {
  articles?: Article[] = [];
  loadingArticles: boolean = true;

  constructor(
    private articleService: ArticleService,
    private router: Router
  ) { }

  ngOnInit(): void {
    this.articleService.fetchArticles()
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

  onViewArticle = (slug: string) => {
    void this.router.navigate(["/articles", slug]);
  }
}
