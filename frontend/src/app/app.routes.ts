import { Routes } from '@angular/router';
import { BlogArticleDetailsComponent } from "./blog-article/blog-article-details/blog-article-details.component";
import { LoginComponent } from "./authentication/login/login.component";
import { BlogArticleFormComponent } from "./blog-article/blog-article-form/blog-article-form.component";
import { AllBlogArticlesComponent } from "./blog-article/all-blog-articles/all-blog-articles.component";
import { AuthGuard } from "./authentication/auth-guard.guard";
import { NotFoundComponent } from "./not-found/not-found.component";
import { AllRssArticlesComponent } from "./rss-article/all-rss-articles-list/all-rss-articles.component";
import { ManagerComponent } from "./manager/manager.component";
import { RssFeedFormComponent } from "./rss-feed/rss-feed-form/rss-feed-form.component";
import { LoginGuard } from "./authentication/login-guard.guard";
import { RssArticleDetailsComponent } from "./rss-article/rss-article-details/rss-article-details.component";
import { HomeComponent } from "./home/home.component";

export const routes: Routes = [
  { title: 'Tino Blog', path: '', component: HomeComponent },
  { title: 'Login - Tino Blog', path: 'login', component: LoginComponent, canActivate: [LoginGuard] },
  { title: 'Manager - Tino Blog', path: 'manager', component: ManagerComponent, canActivate: [AuthGuard] },
  { title: 'Articles - Tino Blog', path: 'articles', component: AllBlogArticlesComponent },
  { title: 'New article - Tino Blog', path: 'articles/new', component: BlogArticleFormComponent, canActivate: [AuthGuard] },
  { path: 'articles/:id', component: BlogArticleDetailsComponent }, // Page title is set dynamically inside the component.
  { title: 'Edit article - Tino Blog', path: 'articles/:id/edit', component: BlogArticleFormComponent, canActivate: [AuthGuard] },
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
