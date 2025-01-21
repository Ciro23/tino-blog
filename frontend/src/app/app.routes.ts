import { Routes } from '@angular/router';
import { LatestArticlesComponent } from './article/latest-articles/latest-articles.component';
import {ArticleDetailsComponent} from "./article/article-details/article-details.component";
import {LoginComponent} from "./authentication/login/login.component";
import {ArticleFormComponent} from "./article/article-form/article-form.component";
import {AllArticlesComponent} from "./article/all-articles/all-articles.component";
import {AuthGuard} from "./authentication/auth-guard.guard";
import {NotFoundComponent} from "./not-found/not-found.component";
import {AllRssArticlesComponent} from "./rss/all-rss-articles-list/all-rss-articles.component";
import {ManagerComponent} from "./manager/manager.component";
import {RssFeedFormComponent} from "./rss/rss-feed-form/rss-feed-form.component";

export const routes: Routes = [
  { title: 'Home - Tino Blog', path: '', component: LatestArticlesComponent },
  { title: 'Login - Tino Blog', path: 'login', component: LoginComponent },
  { title: 'Manager - Tino Blog', path: 'manager', component: ManagerComponent, canActivate: [AuthGuard] },
  { title: 'Articles - Tino Blog', path: 'articles', component: AllArticlesComponent },
  { title: 'New article - Tino Blog', path: 'articles/new', component: ArticleFormComponent, canActivate: [AuthGuard] },
  { path: 'articles/:id', component: ArticleDetailsComponent }, // Page title is set dynamically inside the component.
  { title: 'Edit article - Tino Blog', path: 'articles/:id/edit', component: ArticleFormComponent, canActivate: [AuthGuard] },
  { title: 'RSS - Tino Blog', path: 'rss-aggregator', component: AllRssArticlesComponent },
  { path: 'rss-aggregator/:id', component: AllRssArticlesComponent }, // Page title is set dynamically inside the component.
  { title: 'Add RSS feed - Tino Blog', path: 'rss-feeds/new', component: RssFeedFormComponent, canActivate: [AuthGuard] },
  { title: 'Edit RSS feed - Tino Blog', path: 'rss-feeds/:id/edit', component: RssFeedFormComponent, canActivate: [AuthGuard] },
  { title: 'Page not found - Tino Blog', path: '404', component: NotFoundComponent },
  { title: 'Page not found - Tino Blog', path: '**', component: NotFoundComponent },
];
