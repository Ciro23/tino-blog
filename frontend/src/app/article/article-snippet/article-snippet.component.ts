import {Component, Input, OnChanges, OnInit} from '@angular/core';
import {RouterLink} from "@angular/router";
import {NgClass, NgIf} from "@angular/common";
import {Article} from "../article";
import {AuthService} from "../../authentication/auth.service";
import {getFormattedCreationDateTime} from "../../utilities/date-utilities";
import {ArticleService} from "../article-service";

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

  constructor(protected authService: AuthService) {}

  protected readonly getFormattedCreationDateTime = getFormattedCreationDateTime;
}
