import { Component } from '@angular/core';
import {Router, RouterLink} from "@angular/router";
import {ArticleListComponent} from "../article-list/article-list.component";
import {AuthService} from "../../authentication/auth.service";
import {ConfirmationModalComponent} from "../../confimation-modal/confirmation-modal.component";
import {NgbModal} from "@ng-bootstrap/ng-bootstrap";

@Component({
  selector: 'app-articles-manager',
  standalone: true,
  imports: [
    RouterLink,
    ArticleListComponent
  ],
  templateUrl: './articles-manager.component.html',
})
export class ArticlesManagerComponent {

  constructor(
    private authService: AuthService,
    private router: Router,
    private modalService: NgbModal,
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
