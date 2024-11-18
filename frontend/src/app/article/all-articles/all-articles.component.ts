import {Component, OnInit} from '@angular/core';
import {NgForOf} from "@angular/common";
import {Router, RouterLink} from "@angular/router";
import {Article} from "../article";
import {ArticleListComponent} from "../article-list/article-list.component";
import {ArticleService} from "../article-service";

@Component({
  selector: 'app-all-articles',
  standalone: true,
  imports: [
    NgForOf,
    RouterLink,
    ArticleListComponent
  ],
  templateUrl: './all-articles.component.html',
})
export class AllArticlesComponent implements OnInit {
  articles: Article[] = [];

  constructor(
    private articleService: ArticleService,
    private router: Router
  ) {}

  ngOnInit(): void {
    this.articleService.fetchArticles().subscribe(articles => {
      this.articles = articles;
    })
  }

  onViewArticle = (id: string) => {
    void this.router.navigate(["/articles", id]);
  }
}
