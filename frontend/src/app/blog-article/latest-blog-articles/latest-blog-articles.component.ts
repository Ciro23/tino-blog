import { Component, OnInit } from '@angular/core';
import { Router, RouterLink } from "@angular/router";
import { ArticleListComponent } from "../../article/article-list/article-list.component";
import { BlogArticleService } from "../blog-article-service";
import { finalize } from "rxjs";
import { BlogArticleSummary } from '../blog-article-summary';

@Component({
  selector: 'app-latest-blog-articles',
  standalone: true,
  templateUrl: './latest-blog-articles.component.html',
  imports: [
    RouterLink,
    ArticleListComponent,
  ],
})
export class LatestArticlesComponent implements OnInit {
  articles?: BlogArticleSummary[] = [];
  loadingArticles: boolean = true;

  constructor(
    private articleService: BlogArticleService,
    private router: Router
  ) { }

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
