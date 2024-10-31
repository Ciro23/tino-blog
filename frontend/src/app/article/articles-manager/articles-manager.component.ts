import { Component } from '@angular/core';
import {Router, RouterLink} from "@angular/router";
import {ArticleListComponent} from "../article-list/article-list.component";
import {AuthService} from "../../authentication/auth.service";

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
  ) {}

  logout(): void {
    this.authService.logout();
    void this.router.navigate(["/"]);
  }
}
