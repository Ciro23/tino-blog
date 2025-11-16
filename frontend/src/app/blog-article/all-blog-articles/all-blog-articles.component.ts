import { Component, OnInit } from '@angular/core';
import { Router } from "@angular/router";
import { Article } from "../../article/article";
import { ArticleListComponent } from "../../article/article-list/article-list.component";
import { BlogArticleService } from "../blog-article-service";
import { finalize } from "rxjs";

@Component({
  selector: 'app-all-blog-articles',
  standalone: true,
  imports: [
    ArticleListComponent
  ],
  templateUrl: './all-blog-articles.component.html',
})
export class AllBlogArticlesComponent implements OnInit {
  articles?: Article[] = [];
  loadingArticles: boolean = true;

  constructor(
    private blogArticleService: BlogArticleService,
    private router: Router
  ) { }

  ngOnInit(): void {
    this.blogArticleService.fetchArticles()
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
