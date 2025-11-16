import { Component, Input } from '@angular/core';
import { ArticleSnippetComponent } from "../article-snippet/article-snippet.component";
import { LoadingSpinnerComponent } from '../../loading-spinner/loading-spinner.component';
import { ArticleSummary } from '../article-summary';

@Component({
  selector: 'app-article-list',
  standalone: true,
  imports: [
    ArticleSnippetComponent,
    LoadingSpinnerComponent,
  ],
  templateUrl: './article-list.component.html',
})
export class ArticleListComponent {
  /**
   * Must be undefined only if an error occurred.
   */
  @Input() articles?: ArticleSummary[] = [];
  @Input() categoryNamesByArticleId?: Map<string, string>;

  @Input() loadingArticles: boolean = false;

  @Input() onViewArticle!: (slug: string) => void;
  @Input() onEditArticle?: (id: string) => void;
  @Input() onDeleteArticle?: (id: string) => void;
}
