import { AfterViewInit, Component, OnInit, ViewEncapsulation } from '@angular/core';
import { getFormattedCreationDateTime } from "../../utilities/date-utilities";
import { DomSanitizer, SafeHtml, Title } from "@angular/platform-browser";
import { RssArticle } from "../rss-article";
import { ActivatedRoute, Router } from "@angular/router";
import { RssArticleService } from "../rss-article-service";
import { LoadingSpinnerComponent } from '../../loading-spinner/loading-spinner.component';

@Component({
  selector: 'app-rss-article-details',
  standalone: true,
  imports: [LoadingSpinnerComponent],
  templateUrl: './rss-article-details.component.html',
  styleUrl: './rss-article-details.component.css',
  encapsulation: ViewEncapsulation.None,
})
export class RssArticleDetailsComponent implements OnInit, AfterViewInit {
  articleId?: string;
  article?: RssArticle;

  /**
   * Sanitization must be bypassed because otherwise <iframe>, for example,
   * would not be displayed. This opens the doors for XSS vulnerabilities,
   * but I assume the RSS feeds are trusted.
   */
  articleContent: SafeHtml = "";

  constructor(
    private rssArticleService: RssArticleService,
    private domSanitizer: DomSanitizer,
    private title: Title,
    private route: ActivatedRoute,
    private router: Router,
  ) { }

  ngOnInit(): void {
    this.articleId = this.route.snapshot.paramMap.get('id')!;
    this.rssArticleService.fetchRssArticleById(this.articleId).subscribe({
      next: article => {
        this.article = article;
        this.title.setTitle(article.title + " - Tino Blog");

        this.articleContent = this.domSanitizer.bypassSecurityTrustHtml(article.content);
      },
      error: () => {
        void this.router.navigate(['/404'], { skipLocationChange: true });
      }
    });
  }

  /**
   * Some feeds only publish a short summary, so a link to the original article
   * is shown.
   */
  get articleHost(): string {
    if (!this.article?.link) {
      return "";
    }

    try {
      return new URL(this.article.link).hostname;
    } catch {
      return "the original website";
    }
  }

  ngAfterViewInit(): void {
    this.handleAnchors();
  }

  /**
   * A listener is required to make anchors work, otherwise they
   * would redirect the user to "https://website-root.com/#the-anchor",
   * instead of "https://website-root.com/rss/the-article#the-anchor".
   */
  private handleAnchors(): void {
    document.addEventListener('click', (e) => {
      const anchor = (e.target as HTMLElement).closest('a[href^="#"]');
      if (anchor) {
        e.preventDefault();
        const href = anchor.getAttribute('href');
        if (href) {
          window.location.hash = href;
        }
      }
    });
  }

  protected readonly getFormattedCreationDateTime = getFormattedCreationDateTime;
}
