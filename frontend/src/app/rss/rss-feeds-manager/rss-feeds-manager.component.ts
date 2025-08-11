import { Component, OnInit } from '@angular/core';
import { RssFeed } from "../rss-feed";
import { ConfirmationModalComponent } from "../../confimation-modal/confirmation-modal.component";
import { NgbModal } from "@ng-bootstrap/ng-bootstrap";
import { RouterLink } from "@angular/router";
import { finalize } from "rxjs";
import { RssFeedService } from '../rss-feed-service';
import { LoadingSpinnerComponent } from '../../loading-spinner/loading-spinner.component';

@Component({
  selector: 'app-rss-feeds-manager',
  standalone: true,
  imports: [
    RouterLink,
    LoadingSpinnerComponent,
  ],
  templateUrl: './rss-feeds-manager.component.html',
})
export class RssFeedsManagerComponent implements OnInit {
  rssFeeds?: RssFeed[] = [];
  loadingRssFeeds: boolean = true;

  constructor(
    private rssFeedService: RssFeedService,
    private modalService: NgbModal
  ) { }

  ngOnInit(): void {
    this.rssFeedService.fetchRssFeeds()
      .pipe(
        finalize(() => {
          this.loadingRssFeeds = false;
        })
      )
      .subscribe({
        next: rssFeeds => {
          this.rssFeeds = rssFeeds;
        },
        error: () => {
          this.rssFeeds = undefined;
        }
      });
  }

  openDeleteConfirmationDialog(id: string) {
    const modalRef = this.modalService.open(ConfirmationModalComponent);
    modalRef.componentInstance.title = "Confirm deletion";
    modalRef.componentInstance.message = "Are you sure you want to delete this RSS feed?";
    modalRef.componentInstance.type = "danger";
    modalRef.componentInstance.confirmed.subscribe(() => this.deleteRssFeed(id));
  }

  private deleteRssFeed(id: string): void {
    this.rssFeedService.deleteRssFeed(id).subscribe({
      next: success => {
        if (success) {
          this.rssFeeds = this.rssFeeds!.filter((rssFeed) => rssFeed.id != id);
        }
      }
    })
  }
}
