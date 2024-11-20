import {Component, OnInit} from '@angular/core';
import {NgIf} from "@angular/common";
import {ActivatedRoute, Router, RouterLink} from "@angular/router";
import {Article} from "../article";
import {ArticleService} from "../article-service";
import {AuthService} from "../../authentication/auth.service";
import {getFormattedCreationDateTime} from "../../utilities/date-utilities";
import {MarkdownComponent} from "ngx-markdown";
import {ConfirmationModalComponent} from "../../confimation-modal/confirmation-modal.component";
import {NgbModal} from "@ng-bootstrap/ng-bootstrap";

@Component({
  selector: 'app-article',
  standalone: true,
    imports: [NgIf, RouterLink, MarkdownComponent],
  templateUrl: './article-details.component.html',
  styleUrls: ['./article-details.component.css']
})
export class ArticleDetailsComponent implements OnInit {
  private articleId: string;
  article?: Article;

  constructor(
    private articleService: ArticleService,
    protected authService: AuthService,
    private route: ActivatedRoute,
    private router: Router,
    private modalService: NgbModal,
  ) {
    this.articleId = this.route.snapshot.paramMap.get('id')!;
  }

  ngOnInit(): void {
    this.articleService.fetchArticleById(this.articleId).subscribe({
      next: article => {
        this.article = article;
      },
      error: () => {
        void this.router.navigate(['/404'], { skipLocationChange: true });
      }
    });
  }

  openDeleteConfirmationDialog() {
    const modalRef = this.modalService.open(ConfirmationModalComponent);
    modalRef.componentInstance.title = "Confirm deletion";
    modalRef.componentInstance.message = "Are you sure you want to delete this article?";
    modalRef.componentInstance.type = "danger";
    modalRef.componentInstance.confirmed.subscribe(() => this.deleteArticle());
  }

  private deleteArticle(): void {
    this.articleService.deleteArticle(this.articleId).subscribe({
      next: response => {
        if (response.status === 204) {
          window.history.back();
        }
      }
    });
  }

  protected readonly getFormattedCreationDateTime = getFormattedCreationDateTime;
}
