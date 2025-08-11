import { Component, OnInit } from '@angular/core';
import { FormsModule, NgForm, ReactiveFormsModule } from "@angular/forms";
import { NgClass } from "@angular/common";
import { RssFeed } from "../rss-feed";
import { ActivatedRoute } from "@angular/router";
import { finalize } from "rxjs";
import { RssFeedService } from '../rss-feed-service';
import { LoadingSpinnerComponent } from '../../loading-spinner/loading-spinner.component';

@Component({
  selector: 'app-rss-feed-form',
  standalone: true,
  imports: [
    FormsModule,
    ReactiveFormsModule,
    NgClass,
    LoadingSpinnerComponent,
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
    description: "",
    showArticlesDescription: true
  }

  errorMessage: string = "";

  constructor(
    private rssFeedService: RssFeedService,
    private route: ActivatedRoute
  ) {
    this.rssFeedId = this.route.snapshot.paramMap.get('id')!;
  }

  ngOnInit(): void {
    if (!this.rssFeedId) {
      return;
    }

    this.loadingRssFeed = true;
    this.rssFeedService.fetchRssFeedById(this.rssFeedId)
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

    let callable = this.rssFeedService.updateRssFeed(this.rssFeed!);
    if (!this.rssFeedId) {
      callable = this.rssFeedService.insertRssFeed(this.rssFeed!);
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
