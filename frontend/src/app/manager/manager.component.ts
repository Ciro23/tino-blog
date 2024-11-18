import { Component } from '@angular/core';
import {RssFeedsManagerComponent} from "../rss/rss-feeds-manager/rss-feeds-manager.component";
import {ArticlesManagerComponent} from "../article/articles-manager/articles-manager.component";
import {ConfirmationModalComponent} from "../confimation-modal/confirmation-modal.component";
import {AuthService} from "../authentication/auth.service";
import {NgbModal} from "@ng-bootstrap/ng-bootstrap";
import {Router} from "@angular/router";

@Component({
  selector: 'app-manager',
  standalone: true,
  imports: [
    RssFeedsManagerComponent,
    ArticlesManagerComponent
  ],
  templateUrl: './manager.component.html',
})
export class ManagerComponent {

  constructor(
    private authService: AuthService,
    private modalService: NgbModal,
    private router: Router
  ) {}

  openLogoutConfirmationDialog() {
    const modalRef = this.modalService.open(ConfirmationModalComponent);
    modalRef.componentInstance.title = "Confirm logout";
    modalRef.componentInstance.message = "Are you sure you want to logout?";
    modalRef.componentInstance.type = "danger";
    modalRef.componentInstance.confirmed.subscribe(() => this.logout());
  }

  private logout(): void {
    this.authService.logout();
    void this.router.navigate(["/"]);
  }
}
