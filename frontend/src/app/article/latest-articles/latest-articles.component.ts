import { Component } from '@angular/core';
import {ArticleSnippetComponent} from "../article-snippet/article-snippet.component";
import {NgForOf} from "@angular/common";
import {RouterLink} from "@angular/router";
import {Article} from "../article";
import {ArticleListComponent} from "../article-list/article-list.component";

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
export class LatestArticlesComponent {
  articles: Article[] = [];
}
