import { Component } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import {LatestArticlesComponent} from "./article/latest-articles/latest-articles.component";
import {ArticleDetailsComponent} from "./article/article-details/article-details.component";
import {ArticleSnippetComponent} from "./article/article-snippet/article-snippet.component";
import {NavbarComponent} from "./navbar/navbar.component";
import {FooterComponent} from "./footer/footer.component";
import {ArticleListComponent} from "./article/article-list/article-list.component";
import {LoginComponent} from "./authentication/login/login.component";
import {ArticlesManagerComponent} from "./article/articles-manager/articles-manager.component";
import {ArticleFormComponent} from "./article/article-form/article-form.component";
import {AllArticlesComponent} from "./article/all-articles/all-articles.component";
import {NotFoundComponent} from "./not-found/not-found.component";

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [
    RouterOutlet,
    AllArticlesComponent,
    LatestArticlesComponent,
    ArticleSnippetComponent,
    ArticleDetailsComponent,
    ArticleListComponent,
    ArticlesManagerComponent,
    ArticleFormComponent,
    NavbarComponent,
    FooterComponent,
    LoginComponent,
    NotFoundComponent,
  ],
  templateUrl: './app.component.html',
})
export class AppComponent {
  title = 'frontend';
}
