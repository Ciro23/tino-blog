import {Component, OnInit} from '@angular/core';
import {AutoResizeDirective} from "../../auto-resize.directive";
import {FormsModule, NgForm, ReactiveFormsModule} from "@angular/forms";
import {NgClass, NgIf} from "@angular/common";
import {RssFeed} from "../rss-feed";
import {ActivatedRoute} from "@angular/router";
import {RssService} from "../rss.service";
import {finalize} from "rxjs";

@Component({
  selector: 'app-rss-feed-form',
  standalone: true,
  imports: [
    AutoResizeDirective,
    FormsModule,
    NgIf,
    ReactiveFormsModule,
    NgClass
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
  loadingRssFeed: boolean = false;

  rssFeed?: RssFeed = {
    id: "",
    url: "",
    description: ""
  }

  errorMessage: string = "";

  constructor(private rssService: RssService, private route: ActivatedRoute) {
    this.rssFeedId = this.route.snapshot.paramMap.get('id')!;
  }

  ngOnInit(): void {
    if (!this.rssFeedId) {
      return;
    }

    this.loadingRssFeed = true;
    this.rssService.fetchRssFeedById(this.rssFeedId)
      .pipe(
        finalize(() => {
          this.loadingRssFeed = false;
        })
      )
      .subscribe({
        next: rssFeed => {
          this.rssFeed = rssFeed;
        },
        error: () => {
          this.rssFeed = undefined;
        }
      })
  }

  onSubmit(form: NgForm): void {
    if (!form.valid) {
      return;
    }

    let callable = this.rssService.updateRssFeed(this.rssFeed!);
    if (!this.rssFeedId) {
      callable = this.rssService.insertRssFeed(this.rssFeed!);
    }

    callable.subscribe({
      next: response => {
        if (response.status === 200) {
          window.history.back();
        }
      },
      error: response => {
        if (response.status === 400) {
          this.errorMessage = "An client error has occurred. Verify the data written in the form";
        } else if (response.status === 500) {
          this.errorMessage = "A server error has occurred.";
        }
      }
    });
  }
}
