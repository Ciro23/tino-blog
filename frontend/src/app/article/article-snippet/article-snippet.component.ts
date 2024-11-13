import {Component, Input} from '@angular/core';
import {RouterLink} from "@angular/router";
import {NgClass, NgIf} from "@angular/common";
import {Article} from "../article";
import {AuthService} from "../../authentication/auth.service";
import {getFormattedCreationDateTime} from "../../utilities/date-utilities";
import {ConfirmationModalComponent} from "../../confimation-modal/confirmation-modal.component";
import {NgbModal} from "@ng-bootstrap/ng-bootstrap";

@Component({
  selector: 'app-article-snippet',
  standalone: true,
  templateUrl: './article-snippet.component.html',
  imports: [
    RouterLink,
    NgIf,
    NgClass
  ],
  styleUrls: ['./article-snippet.component.css']
})
export class ArticleSnippetComponent {
  @Input() article!: Article;
  @Input() onDelete!: (id: string) => void;

  /**
   * If the user is authenticated, allows the action buttons to
   * be shown for the article, to manage it.
   */
  @Input() allowArticleActions: boolean = false;

  constructor(
    protected authService: AuthService,
    private modalService: NgbModal,
  ) {}

  protected readonly getFormattedCreationDateTime = getFormattedCreationDateTime;

  openDeleteConfirmationDialog(id: string) {
    const modalRef = this.modalService.open(ConfirmationModalComponent);
    modalRef.componentInstance.title = "Confirm deletion";
    modalRef.componentInstance.message = "Are you sure you want to delete this article?";
    modalRef.componentInstance.type = "danger";
    modalRef.componentInstance.confirmed.subscribe(() => this.onDelete(id));
  }
}
