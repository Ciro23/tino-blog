import {Component, Input} from '@angular/core';
import {ArticleSnippetComponent} from "../article-snippet/article-snippet.component";
import {NgForOf, NgIf} from "@angular/common";
import {Article} from "../article";

@Component({
  selector: 'app-article-list',
  standalone: true,
  imports: [
    ArticleSnippetComponent,
    NgForOf,
    NgIf
  ],
  templateUrl: './article-list.component.html',
})
export class ArticleListComponent {
  @Input() articles: Article[] = [];
  @Input() loadingArticles: boolean = false;

  @Input() onViewArticle!: (id: string) => void;
  @Input() onEditArticle?: (id: string) => void;
  @Input() onDeleteArticle?: (id: string) => void;
}
