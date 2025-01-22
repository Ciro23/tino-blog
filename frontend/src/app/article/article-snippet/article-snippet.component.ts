import {Component, Input} from '@angular/core';
import {RouterLink} from "@angular/router";
import {NgClass, NgIf} from "@angular/common";
import {Article} from "../article";
import {AuthService} from "../../authentication/auth.service";
import {getFormattedCreationDateTime} from "../../utilities/date-utilities";

@Component({
  selector: 'app-article-snippet',
  standalone: true,
  templateUrl: './article-snippet.component.html',
  imports: [
    RouterLink,
    NgIf,
    NgClass
  ]
})
export class ArticleSnippetComponent {
  @Input() article!: Article;

  @Input() onView!: (slug: string) => void;
  @Input() onEdit?: (id: string) => void;
  @Input() onDelete?: (id: string) => void;

  constructor(protected authService: AuthService) {}

  protected readonly getFormattedCreationDateTime = getFormattedCreationDateTime;
}
