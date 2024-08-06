import {Component, OnInit} from '@angular/core';
import {NgForOf} from "@angular/common";
import {RouterLink} from "@angular/router";
import {ArticleService} from "../article-service";
import {Article} from "../article";
import {ArticleListComponent} from "../article-list/article-list.component";

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
export class AllArticlesComponent {
  articles: Article[] = [];

  constructor(private articleService: ArticleService) {}
}
