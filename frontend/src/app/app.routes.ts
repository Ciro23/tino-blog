import { Routes } from '@angular/router';
import { LatestArticlesComponent } from './article/latest-articles/latest-articles.component';
import {ArticleDetailsComponent} from "./article/article-details/article-details.component";
import {ArticleListComponent} from "./article/article-list/article-list.component";
import {LoginComponent} from "./authentication/login/login.component";
import {ArticlesManagerComponent} from "./article/articles-manager/articles-manager.component";
import {ArticleFormComponent} from "./article/article-form/article-form.component";
import {AllArticlesComponent} from "./article/all-articles/all-articles.component";
import {AuthGuard} from "./authentication/auth-guard.guard";
import {NotFoundComponent} from "./not-found/not-found.component";

export const routes: Routes = [
  { path: '', component: LatestArticlesComponent },
  { path: 'login', component: LoginComponent },
  { path: 'logout', component: LoginComponent },
  { path: 'articles', component: AllArticlesComponent },
  { path: 'articles/manager', component: ArticlesManagerComponent, canActivate: [AuthGuard] },
  { path: 'articles/new', component: ArticleFormComponent, canActivate: [AuthGuard] },
  { path: 'articles/:id', component: ArticleDetailsComponent },
  { path: 'articles/:id/edit', component: ArticleFormComponent, canActivate: [AuthGuard] },
  { path: '404', component: NotFoundComponent },
  { path: '**', component: NotFoundComponent },
];
