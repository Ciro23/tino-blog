import { Routes } from '@angular/router';
import { LatestArticlesComponent } from './article/latest-articles/latest-articles.component';
import { ArticleDetailsComponent } from "./article/article-details/article-details.component";
import { LoginComponent } from "./authentication/login/login.component";
import { ArticleFormComponent } from "./article/article-form/article-form.component";
import { AllArticlesComponent } from "./article/all-articles/all-articles.component";
import { AuthGuard } from "./authentication/auth-guard.guard";
import { NotFoundComponent } from "./not-found/not-found.component";
import { AllRssArticlesComponent } from "./rss/all-rss-articles-list/all-rss-articles.component";
import { ManagerComponent } from "./manager/manager.component";
import { RssFeedFormComponent } from "./rss/rss-feed-form/rss-feed-form.component";
import { LoginGuard } from "./authentication/login-guard.guard";
import { RssArticleDetailsComponent } from "./rss/rss-article-details/rss-article-details.component";
import { HomeComponent } from "./home/home.component";

export const routes: Routes = [
  { title: 'Tino Blog', path: '', component: HomeComponent },
  { title: 'Login - Tino Blog', path: 'login', component: LoginComponent, canActivate: [LoginGuard] },
  { title: 'Manager - Tino Blog', path: 'manager', component: ManagerComponent, canActivate: [AuthGuard] },
  { title: 'Articles - Tino Blog', path: 'articles', component: AllArticlesComponent },
  { title: 'New article - Tino Blog', path: 'articles/new', component: ArticleFormComponent, canActivate: [AuthGuard] },
  { path: 'articles/:id', component: ArticleDetailsComponent }, // Page title is set dynamically inside the component.
  { title: 'Edit article - Tino Blog', path: 'articles/:id/edit', component: ArticleFormComponent, canActivate: [AuthGuard] },
  { title: 'RSS - Tino Blog', path: 'rss', component: AllRssArticlesComponent },
  { path: 'rss/articles/:id', component: RssArticleDetailsComponent }, // Page title is set dynamically inside the component.
  { title: 'Add RSS feed - Tino Blog', path: 'rss/feeds/new', component: RssFeedFormComponent, canActivate: [AuthGuard] },
  { title: 'Edit RSS feed - Tino Blog', path: 'rss/feeds/:id/edit', component: RssFeedFormComponent, canActivate: [AuthGuard] },
  { title: 'Page not found - Tino Blog', path: '404', component: NotFoundComponent },

  // Redirects for old routes to keep compatibility with old versions.
  // It's very important not to break existing public URLs for SEO and UX.
  // Old private URLs, like the ones accessible only by admins, do not need to be here.
  { path: 'rss-aggregator', redirectTo: `rss`, pathMatch: 'full' },
  { path: 'rss-aggregator/:id', redirectTo: `/rss/articles/:id`, pathMatch: 'full' },

  { title: 'Page not found - Tino Blog', path: '**', component: NotFoundComponent },
];
