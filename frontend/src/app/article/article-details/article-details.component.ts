import {Component, Input, OnInit} from '@angular/core';
import {NgIf} from "@angular/common";
import {ActivatedRoute, Router, RouterLink} from "@angular/router";
import {Article} from "../article";
import {ArticleService} from "../article-service";
import {AuthService} from "../../authentication/auth.service";
import {getFormattedCreationDateTime} from "../../utilities/date-utilities";
import {log} from "@angular-devkit/build-angular/src/builders/ssr-dev-server";
import {MarkdownComponent} from "ngx-markdown";

@Component({
  selector: 'app-article',
  standalone: true,
    imports: [NgIf, RouterLink, MarkdownComponent],
  templateUrl: './article-details.component.html',
  styleUrls: ['./article-details.component.css']
})
export class ArticleDetailsComponent implements OnInit {
  private articleId: string;
  article: Article = {
    id: "",
    title: "",
    shortDescription: "",
    content: "",
  };

  constructor(
    private articleService: ArticleService,
    protected authService: AuthService,
    private route: ActivatedRoute,
    private router: Router,
  ) {
    this.articleId = this.route.snapshot.paramMap.get('id')!;
  }

  ngOnInit(): void {
    this.articleService.fetchArticleById(this.articleId).subscribe(article => {
      this.article = article;
    }, error => {
      void this.router.navigate(['/404'], { skipLocationChange: true });
    });
  }

  deleteArticle(): void {
    this.articleService.deleteArticle(this.articleId).subscribe({
      next: response => {
        if (response.status === 204) {
          window.history.back();
        }
      }
    });
  }

  protected readonly getFormattedCreationDateTime = getFormattedCreationDateTime;
}
