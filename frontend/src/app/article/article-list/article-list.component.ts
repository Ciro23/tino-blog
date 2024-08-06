import {Component, Input, OnInit} from '@angular/core';
import {ArticleSnippetComponent} from "../article-snippet/article-snippet.component";
import {NgForOf} from "@angular/common";
import {ArticleService} from "../article-service";
import {Article} from "../article";
import {ActivatedRoute} from "@angular/router";

@Component({
  selector: 'app-article-list',
  standalone: true,
  imports: [
    ArticleSnippetComponent,
    NgForOf
  ],
  templateUrl: './article-list.component.html',
  styleUrl: './article-list.component.css'
})
export class ArticleListComponent implements OnInit {
  /**
   * The number of articles to fetch from the backend, or null
   * to fetch them all.
   */
  @Input() numberToFetch?: number | null;

  /**
   * If the user is authenticated, allows the action buttons to
   * be shown for each article, to manage them.
   */
  @Input() allowArticleActions: boolean = false;

  articles: Article[] = [];

  constructor(private articleService: ArticleService) {}

  ngOnInit(): void {
    if (this.numberToFetch == null) {
      this.articleService.fetchArticles().subscribe(articles => {
        this.articles = articles;
      })
    } else {
      this.articleService.fetchLatestArticles(this.numberToFetch).subscribe(articles => {
        this.articles = articles;
      })
    }
  }

  onDeleteArticle = (id: string) => {
    this.articleService.deleteArticle(id).subscribe({
      next: success => {
        if (success) {
          this.articles = this.articles.filter((article) => article.id != id);
        }
      }
    })
  }
}
