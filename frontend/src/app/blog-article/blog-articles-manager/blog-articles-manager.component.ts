import { Component, ElementRef, OnInit } from '@angular/core';
import { Router, RouterLink } from "@angular/router";
import { ArticleListComponent } from "../../article/article-list/article-list.component";
import { ConfirmationModalComponent } from "../../confimation-modal/confirmation-modal.component";
import { NgbModal } from "@ng-bootstrap/ng-bootstrap";
import { BlogArticleService } from "../blog-article-service";
import { finalize, Subscription } from "rxjs";
import { BlogArticleSummary } from '../blog-article-summary';
import { ARTICLES_PAGE_SIZE } from '../../utilities/page-result';

@Component({
  selector: 'app-blog-articles-manager',
  standalone: true,
  imports: [
    RouterLink,
    ArticleListComponent,
  ],
  templateUrl: './blog-articles-manager.component.html',
})
export class ArticlesManagerComponent implements OnInit {
  articles?: BlogArticleSummary[] = [];
  loadingArticles: boolean = true;

  currentPage: number = 0;
  totalElements: number = 0;
  readonly pageSize: number = ARTICLES_PAGE_SIZE;

  private pageSubscription?: Subscription;

  constructor(
    private articleService: BlogArticleService,
    private router: Router,
    private modalService: NgbModal,
    private elementRef: ElementRef<HTMLElement>,
  ) { }

  ngOnInit(): void {
    this.fetchPage(0);
  }

  onViewArticle = (id: string) => {
    void this.router.navigate(["/articles", id]);
  }

  onEditArticle = (id: string) => {
    void this.router.navigate(["/articles", id, "edit"]);
  }

  /**
   * The page isn't stored in the URL, because the manager page also
   * contains other lists.
   */
  onPageChange = (page: number) => {
    this.fetchPage(page);
    this.elementRef.nativeElement.scrollIntoView();
  }

  openDeleteConfirmationDialog = (id: string) => {
    const modalRef = this.modalService.open(ConfirmationModalComponent);
    modalRef.componentInstance.title = "Confirm deletion";
    modalRef.componentInstance.message = "Are you sure you want to delete this article?";
    modalRef.componentInstance.type = "danger";
    modalRef.componentInstance.confirmed.subscribe(() => this.deleteArticle(id));
  }

  private fetchPage(page: number): void {
    // Cancels the request of the previous page when changing it quickly.
    this.pageSubscription?.unsubscribe();

    this.loadingArticles = true;
    this.pageSubscription = this.articleService.fetchArticles(page, this.pageSize)
      .pipe(
        finalize(() => {
          this.loadingArticles = false;
        })
      )
      .subscribe({
        next: result => {
          // The page doesn't exist anymore, e.g. after deleting its last article.
          if (result.content.length === 0 && result.totalPages > 0) {
            this.fetchPage(result.totalPages - 1);
            return;
          }

          this.articles = result.content;
          this.currentPage = result.page;
          this.totalElements = result.totalElements;
        },
        error: () => {
          this.articles = undefined;
        }
      });
  }

  private deleteArticle(id: string): void {
    this.articleService.deleteArticle(id).subscribe({
      next: success => {
        if (success) {
          // Reloads the page so that it's filled with the next article.
          this.fetchPage(this.currentPage);
        }
      }
    })
  }
}
