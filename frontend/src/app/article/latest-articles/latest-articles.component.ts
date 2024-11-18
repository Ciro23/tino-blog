import {Component, OnInit} from '@angular/core';
import {ArticleSnippetComponent} from "../article-snippet/article-snippet.component";
import {NgForOf} from "@angular/common";
import {Router, RouterLink} from "@angular/router";
import {Article} from "../article";
import {ArticleListComponent} from "../article-list/article-list.component";
import {ArticleService} from "../article-service";

@Component({
  selector: 'app-latest-articles',
  standalone: true,
  templateUrl: './latest-articles.component.html',
  imports: [
    ArticleSnippetComponent,
    NgForOf,
    RouterLink,
    ArticleListComponent
  ],
})
export class LatestArticlesComponent implements OnInit {
  articles: Article[] = [];

  constructor(
    private articleService: ArticleService,
    private router: Router
  ) {}

  ngOnInit(): void {
    this.articleService.fetchLatestArticles(5).subscribe(articles => {
      this.articles = articles;
    })
  }

  onViewArticle = (id: string) => {
    void this.router.navigate(["/articles", id]);
  }
}
