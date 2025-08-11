import { AfterViewInit, Component, OnInit, ViewEncapsulation } from '@angular/core';
import { getFormattedCreationDateTime } from "../../utilities/date-utilities";
import { DomSanitizer, SafeHtml, Title } from "@angular/platform-browser";
import { RssArticle } from "../rss-article";
import { ActivatedRoute, Router } from "@angular/router";
import { RssService } from "../rss.service";

@Component({
  selector: 'app-rss-article-details',
  standalone: true,
  imports: [],
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
    private rssService: RssService,
    private domSanitizer: DomSanitizer,
    private title: Title,
    private route: ActivatedRoute,
    private router: Router,
  ) { }

  ngOnInit(): void {
    this.articleId = this.route.snapshot.paramMap.get('id')!;
    this.rssService.fetchRssArticleById(this.articleId).subscribe({
      next: article => {
        this.article = article;
        this.title.setTitle(article.title + " - Tino Blog");

        const htmlWithNoStyle = this.stripInlineStyles(this.article.content);
        const htmlWithNoVideoSizeAttributs = this.stripVideoSizeAttributes(htmlWithNoStyle);
        const cleanedUpHtml = this.fixVideoFullscreenOnMobile(htmlWithNoVideoSizeAttributs);
        this.articleContent = this.domSanitizer.bypassSecurityTrustHtml(cleanedUpHtml);
      },
      error: () => {
        void this.router.navigate(['/404'], { skipLocationChange: true });
      }
    });
  }

  ngAfterViewInit(): void {
    this.handleAnchors();
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

  private stripVideoSizeAttributes(html: string): string {
    const parser = new DOMParser();
    const doc = parser.parseFromString(html, 'text/html');

    doc.querySelectorAll('video').forEach(video => {
      video.removeAttribute('width');
      video.removeAttribute('height');
    });

    return doc.body.innerHTML;
  }

  private fixVideoFullscreenOnMobile(html: string): string {
    const parser = new DOMParser();
    const doc = parser.parseFromString(html, 'text/html');

    doc.querySelectorAll('video').forEach(video => {
      video.setAttribute('playsinline', '');
      video.setAttribute('webkit-playsinline', '');
    });

    return doc.body.innerHTML;
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
