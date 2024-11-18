import { Routes } from '@angular/router';
import { LatestArticlesComponent } from './article/latest-articles/latest-articles.component';
import {ArticleDetailsComponent} from "./article/article-details/article-details.component";
import {LoginComponent} from "./authentication/login/login.component";
import {ArticleFormComponent} from "./article/article-form/article-form.component";
import {AllArticlesComponent} from "./article/all-articles/all-articles.component";
import {AuthGuard} from "./authentication/auth-guard.guard";
import {NotFoundComponent} from "./not-found/not-found.component";
import {AllRssArticlesComponent} from "./rss/all-rss-list/all-rss-articles.component";
import {ManagerComponent} from "./manager/manager.component";
import {RssFeedFormComponent} from "./rss/rss-feed-form/rss-feed-form.component";

export const routes: Routes = [
  { path: '', component: LatestArticlesComponent },
  { path: 'login', component: LoginComponent },
  { path: 'logout', component: LoginComponent },
  { path: 'manager', component: ManagerComponent, canActivate: [AuthGuard] },
  { path: 'articles', component: AllArticlesComponent },
  { path: 'articles/new', component: ArticleFormComponent, canActivate: [AuthGuard] },
  { path: 'articles/:id', component: ArticleDetailsComponent },
  { path: 'articles/:id/edit', component: ArticleFormComponent, canActivate: [AuthGuard] },
  { path: 'rss-aggregator', component: AllRssArticlesComponent },
  { path: 'rss-aggregator/:id', component: AllRssArticlesComponent },
  { path: 'rss-feeds/new', component: RssFeedFormComponent, canActivate: [AuthGuard] },
  { path: 'rss-feeds/:id/edit', component: RssFeedFormComponent, canActivate: [AuthGuard] },
  { path: '404', component: NotFoundComponent },
  { path: '**', component: NotFoundComponent },
];
