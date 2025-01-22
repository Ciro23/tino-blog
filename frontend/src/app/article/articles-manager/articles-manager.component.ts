import {Component, OnInit} from '@angular/core';
import {Router, RouterLink} from "@angular/router";
import {ArticleListComponent} from "../article-list/article-list.component";
import {ConfirmationModalComponent} from "../../confimation-modal/confirmation-modal.component";
import {NgbModal} from "@ng-bootstrap/ng-bootstrap";
import {ArticleService} from "../article-service";
import {NgIf} from "@angular/common";
import {finalize} from "rxjs";
import {Article} from "../article";

@Component({
  selector: 'app-articles-manager',
  standalone: true,
  imports: [
    RouterLink,
    ArticleListComponent,
    NgIf
  ],
  templateUrl: './articles-manager.component.html',
})
export class ArticlesManagerComponent implements OnInit {
  articles?: Article[] = [];
  loadingArticles: boolean = true;

  constructor(
    private articleService: ArticleService,
    private router: Router,
    private modalService: NgbModal,
  ) {}

  ngOnInit(): void {
    this.articleService.fetchArticles()
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
        }
      })
  }

  onViewArticle = (id: string) => {
    void this.router.navigate(["/articles", id]);
  }

  onEditArticle = (id: string) => {
    void this.router.navigate(["/articles", id, "edit"]);
  }

  openDeleteConfirmationDialog = (id: string) => {
    const modalRef = this.modalService.open(ConfirmationModalComponent);
    modalRef.componentInstance.title = "Confirm deletion";
    modalRef.componentInstance.message = "Are you sure you want to delete this article?";
    modalRef.componentInstance.type = "danger";
    modalRef.componentInstance.confirmed.subscribe(() => this.deleteArticle(id));
  }

  private deleteArticle(id: string): void {
    this.articleService.deleteArticle(id).subscribe({
      next: success => {
        if (success) {
          this.articles = this.articles!.filter((article) => article.id != id);
        }
      }
    })
  }
}
