import {Component, OnInit, ViewEncapsulation} from '@angular/core';
import {NgIf} from "@angular/common";
import {getFormattedCreationDateTime} from "../../utilities/date-utilities";
import {DomSanitizer, SafeHtml, Title} from "@angular/platform-browser";
import {RssArticle} from "../rss-article";
import {ActivatedRoute, Router} from "@angular/router";
import {RssService} from "../rss.service";

@Component({
  selector: 'app-rss-article-details',
  standalone: true,
  imports: [
    NgIf
  ],
  templateUrl: './rss-article-details.component.html',
  styleUrl: './rss-article-details.component.css',
  encapsulation: ViewEncapsulation.None,
})
export class RssArticleDetailsComponent implements OnInit {
  articleId?: string;
  article?: RssArticle;

  /**
   * Sanitization must be bypassed because otherwise <iframe>, for example,
   * would not be displayed. This opens the doors for XSS vulnerabilities,
   * but I assume the RSS feeds are trusted.
   */
  articleContent: SafeHtml = "";

  constructor(
    private rssService: RssService,
    private domSanitizer: DomSanitizer,
    private title: Title,
    private route: ActivatedRoute,
    private router: Router,
  ) {}

  ngOnInit(): void {
    this.articleId = this.route.snapshot.paramMap.get('id')!;
    this.rssService.fetchRssArticleById(this.articleId).subscribe({
      next: article => {
        this.article = article;
        this.title.setTitle(article.title + " - Tino Blog");

        const cleanedHtml = this.stripInlineStyles(this.article.content);
        this.articleContent = this.domSanitizer.bypassSecurityTrustHtml(cleanedHtml);
      },
      error: () => {
        void this.router.navigate(['/404'], { skipLocationChange: true });
      }
    });
  }

  /**
  * RSS articles should not dare trying to override my style.
  */
  private stripInlineStyles(html: string): string {
    const parser = new DOMParser();
    const doc = parser.parseFromString(html, 'text/html');

    doc.querySelectorAll('[style]').forEach(el => el.removeAttribute('style'));
    doc.querySelectorAll('style').forEach(el => el.remove());

    return doc.body.innerHTML;
  }

  protected readonly getFormattedCreationDateTime = getFormattedCreationDateTime;
}
