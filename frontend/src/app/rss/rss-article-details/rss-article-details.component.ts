import {AfterViewInit, Component, Input, OnChanges, ViewEncapsulation} from '@angular/core';
import {MarkdownComponent} from "ngx-markdown";
import {NgForOf, NgIf} from "@angular/common";
import {getFormattedCreationDateTime} from "../../utilities/date-utilities";
import {DomSanitizer, SafeHtml} from "@angular/platform-browser";
import {RssArticle} from "../rss-article";

@Component({
  selector: 'app-rss-article-details',
  standalone: true,
  imports: [
    MarkdownComponent,
    NgIf,
    NgForOf
  ],
  templateUrl: './rss-article-details.component.html',
  styleUrl: './rss-article-details.component.css',
  encapsulation: ViewEncapsulation.None,
})
export class RssArticleDetailsComponent implements OnChanges {
  @Input() article?: RssArticle;
  articleContent: SafeHtml = "";

  constructor(private domSanitizer: DomSanitizer) {}

  ngOnChanges(): void {
    if (this.article) {
      // Sanitization must be bypassed because otherwise <iframe>, for example,
      // would not be displayed. This opens the doors for XSS vulnerabilities,
      // but I assume the RSS feeds are trusted.
      this.articleContent = this.domSanitizer.bypassSecurityTrustHtml(this.article.content);
    }
  }

  protected readonly getFormattedCreationDateTime = getFormattedCreationDateTime;
}
