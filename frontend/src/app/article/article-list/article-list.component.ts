import { Component, Input } from '@angular/core';
import {
  NgbPagination,
  NgbPaginationFirst,
  NgbPaginationLast,
  NgbPaginationNext,
  NgbPaginationPrevious,
} from '@ng-bootstrap/ng-bootstrap';
import { ArticleSnippetComponent } from "../article-snippet/article-snippet.component";
import { LoadingSpinnerComponent } from '../../loading-spinner/loading-spinner.component';
import { ArticleSummary } from '../article-summary';

@Component({
  selector: 'app-article-list',
  standalone: true,
  imports: [
    ArticleSnippetComponent,
    LoadingSpinnerComponent,
    NgbPagination,
    NgbPaginationFirst,
    NgbPaginationPrevious,
    NgbPaginationNext,
    NgbPaginationLast,
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

  /**
   * The 0-based index of the shown page.
   */
  @Input() currentPage: number = 0;
  @Input() pageSize: number = 0;
  @Input() totalElements: number = 0;

  /**
   * The pagination controls are shown only when this is set.
   * Receives the 0-based index of the page to show.
   */
  @Input() onPageChange?: (page: number) => void;

  protected changePage(oneBasedPage: number) {
    const page = oneBasedPage - 1;

    // The pagination component also emits when it corrects its own page,
    // which must not trigger a new request.
    if (page !== this.currentPage) {
      this.onPageChange?.(page);
    }
  }
}
