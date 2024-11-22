import {Component, OnInit} from '@angular/core';
import {RssFeed} from "../rss-feed";
import {RssService} from "../rss.service";
import {NgForOf, NgIf} from "@angular/common";
import {ConfirmationModalComponent} from "../../confimation-modal/confirmation-modal.component";
import {NgbModal} from "@ng-bootstrap/ng-bootstrap";
import {RouterLink} from "@angular/router";
import {finalize} from "rxjs";

@Component({
  selector: 'app-rss-feeds-manager',
  standalone: true,
  imports: [
    NgForOf,
    RouterLink,
    NgIf
  ],
  templateUrl: './rss-feeds-manager.component.html',
})
export class RssFeedsManagerComponent implements OnInit {
  rssFeeds?: RssFeed[] = [];
  loadingRssFeeds: boolean = true;

  constructor(
    private rssService: RssService,
    private modalService: NgbModal
  ) {}

  ngOnInit(): void {
    this.rssService.fetchRssFeeds()
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
    this.rssService.deleteRssFeed(id).subscribe({
      next: success => {
        if (success) {
          this.rssFeeds = this.rssFeeds!.filter((rssFeed) => rssFeed.id != id);
        }
      }
    })
  }
}
