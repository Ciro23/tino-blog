import {AfterViewInit, Component, Input, ViewEncapsulation} from '@angular/core';
import {MarkdownComponent} from "ngx-markdown";
import {NgIf} from "@angular/common";
import {getFormattedCreationDateTime} from "../../utilities/date-utilities";
import {Article} from "../../article/article";

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
export class RssArticleDetailsComponent implements AfterViewInit {
  @Input() article?: Article;

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
  }

  protected readonly getFormattedCreationDateTime = getFormattedCreationDateTime;
}
