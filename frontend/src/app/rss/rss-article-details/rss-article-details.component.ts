import {AfterViewInit, Component, Input, OnChanges, SimpleChanges, ViewEncapsulation} from '@angular/core';
import {MarkdownComponent} from "ngx-markdown";
import {NgIf} from "@angular/common";
import {getFormattedCreationDateTime} from "../../utilities/date-utilities";
import {Article} from "../../article/article";
import {DomSanitizer, SafeHtml} from "@angular/platform-browser";

@Component({
  selector: 'app-rss-article-details',
  standalone: true,
    imports: [
        MarkdownComponent,
        NgIf
    ],
  templateUrl: './rss-article-details.component.html',
  styleUrl: './rss-article-details.component.css'
})
export class RssArticleDetailsComponent implements AfterViewInit, OnChanges {
  @Input() article?: Article;
  articleContent: SafeHtml = "";

  constructor(private domSanitizer: DomSanitizer) {}

  ngAfterViewInit(): void {
    const figures = document.querySelectorAll('.content figure');
    figures.forEach((img) => {
      (img as HTMLImageElement).style.alignSelf = 'center';
    });

    const images = document.querySelectorAll('.content img');
    images.forEach((img) => {
      (img as HTMLImageElement).style.maxWidth = '100%';
      (img as HTMLImageElement).style.height = 'auto';
    });

    // Syntax highlighting is not available for the RSS articles, but at least
    // this makes the code blocks a bit more readable.
    const codeBlocks = document.querySelectorAll('.content pre');
    codeBlocks.forEach((codeBlock) => {
      (codeBlock as HTMLPreElement).style.background = '#272822';
      (codeBlock as HTMLPreElement).style.padding = '1rem';
      (codeBlock as HTMLPreElement).style.borderRadius = '0.3rem';
    });
  }

  ngOnChanges(changes: SimpleChanges): void {
    if (this.article) {
      // Sanitization must be bypassed because otherwise <iframe>, for example,
      // would not be displayed. This opens the doors for XSS vulnerabilities,
      // but I assume the RSS feeds are trusted.
      this.articleContent = this.domSanitizer.bypassSecurityTrustHtml(this.article.content);
    }
  }

  protected readonly getFormattedCreationDateTime = getFormattedCreationDateTime;
}
