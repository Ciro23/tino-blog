import { Component, Input } from '@angular/core';
import { AuthService } from "../../authentication/auth.service";
import { getFormattedCreationDateTime } from "../../utilities/date-utilities";
import { ArticleSummary } from '../article-summary';

@Component({
  selector: 'app-article-snippet',
  standalone: true,
  templateUrl: './article-snippet.component.html',
  imports: []
})
export class ArticleSnippetComponent {
  @Input() article!: ArticleSummary;
  @Input() categoryName?: string;

  @Input() onView!: (slug: string) => void;
  @Input() onEdit?: (id: string) => void;
  @Input() onDelete?: (id: string) => void;

  constructor(protected authService: AuthService) { }

  protected readonly getFormattedCreationDateTime = getFormattedCreationDateTime;
}
