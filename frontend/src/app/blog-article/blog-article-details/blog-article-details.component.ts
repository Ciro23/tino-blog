import { Component, OnInit, ViewEncapsulation } from '@angular/core';
import { ActivatedRoute, Router, RouterLink } from "@angular/router";
import { Article } from "../../article/article";
import { BlogArticleService } from "../blog-article-service";
import { AuthService } from "../../authentication/auth.service";
import { getFormattedCreationDateTime } from "../../utilities/date-utilities";
import { MarkdownComponent, MarkdownService } from "ngx-markdown";
import { ConfirmationModalComponent } from "../../confimation-modal/confirmation-modal.component";
import { NgbModal } from "@ng-bootstrap/ng-bootstrap";
import { Title } from "@angular/platform-browser";
import { Tokens } from "marked";
import { LoadingSpinnerComponent } from '../../loading-spinner/loading-spinner.component';

@Component({
  selector: 'app-blog-article',
  standalone: true,
  imports: [
    RouterLink,
    MarkdownComponent,
    LoadingSpinnerComponent,
  ],
  templateUrl: './blog-article-details.component.html',
  styleUrls: ['blog-article-details.component.css'],
  encapsulation: ViewEncapsulation.None,
})
export class BlogArticleDetailsComponent implements OnInit {
  articleId?: string;
  article?: Article;

  constructor(
    private blogArticleService: BlogArticleService,
    protected authService: AuthService,
    private route: ActivatedRoute,
    private router: Router,
    private modalService: NgbModal,
    private title: Title,
    private markdownService: MarkdownService,
  ) { }

  ngOnInit(): void {
    this.articleId = this.route.snapshot.paramMap.get('id')!;
    this.blogArticleService.fetchArticleById(this.articleId).subscribe({
      next: article => {
        this.article = article;
        this.title.setTitle(article.title + " - Tino Blog");
      },
      error: () => {
        void this.router.navigate(['/404'], { skipLocationChange: true });
      }
    });

    this.openLinksInNewTab();
  }

  openDeleteConfirmationDialog() {
    const modalRef = this.modalService.open(ConfirmationModalComponent);
    modalRef.componentInstance.title = "Confirm deletion";
    modalRef.componentInstance.message = "Are you sure you want to delete this article?";
    modalRef.componentInstance.type = "danger";
    modalRef.componentInstance.confirmed.subscribe(() => this.deleteArticle());
  }

  private deleteArticle(): void {
    this.blogArticleService.deleteArticle(this.article!.id).subscribe({
      next: response => {
        if (response.status === 204) {
          window.history.back();
        }
      }
    });
  }

  private openLinksInNewTab() {
    this.markdownService.renderer.link = ({ href, title, text }: Tokens.Link) => {
      const titleAttr = title ? ` title="${title}"` : '';
      return `<a href="${href}"${titleAttr} target="_blank" rel="noopener noreferrer">${text}</a>`;
    };
  }

  protected readonly getFormattedCreationDateTime = getFormattedCreationDateTime;
}
