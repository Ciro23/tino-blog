import {Component, OnInit} from '@angular/core';
import {AutoResizeDirective} from "../../auto-resize.directive";
import {FormsModule, NgForm, ReactiveFormsModule} from "@angular/forms";
import {NgIf} from "@angular/common";
import {RssFeed} from "../rss-feed";
import {ArticleService} from "../../article/article-service";
import {ActivatedRoute} from "@angular/router";
import {RssService} from "../rss.service";

@Component({
  selector: 'app-rss-feed-form',
  standalone: true,
  imports: [
    AutoResizeDirective,
    FormsModule,
    NgIf,
    ReactiveFormsModule
  ],
  templateUrl: './rss-feed-form.component.html',
})
export class RssFeedFormComponent implements OnInit {
  /**
   * If undefined, the forms represents the addition of a new RSS feed,
   * otherwise an existing RSS feed is fetched from the backend to be
   * edited, given its id.
   */
  readonly rssFeedId?: string;

  rssFeed: RssFeed = {
    id: "",
    url: "",
    description: ""
  }

  constructor(private rssService: RssService, private route: ActivatedRoute) {
    this.rssFeedId = this.route.snapshot.paramMap.get('id')!;
  }

  ngOnInit(): void {
    if (!this.rssFeedId) {
      return;
    }

    this.rssService.fetchRssFeedById(this.rssFeedId).subscribe(article => {
      this.rssFeed = article;
    })
  }

  onSubmit(form: NgForm): void {
    let callable = this.rssService.updateRssFeed(this.rssFeed);
    if (!this.rssFeedId) {
      callable = this.rssService.insertRssFeed(this.rssFeed);
    }

    callable.subscribe({
      next: response => {
        if (response.status === 200) {
          window.history.back();
        }
      }
    });
  }
}
