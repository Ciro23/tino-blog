import {Component, OnInit} from '@angular/core';
import {FormsModule, NgForm} from "@angular/forms";
import {Article} from "../article";
import {ArticleService} from "../article-service";
import {ActivatedRoute} from "@angular/router";
import {NgClass, NgIf} from "@angular/common";
import {AutoResizeDirective} from "../../directives/auto-resize.directive";
import {MarkdownComponent} from "ngx-markdown";
import {finalize} from "rxjs";

@Component({
  selector: 'app-article-form',
  standalone: true,
  imports: [
    FormsModule,
    NgIf,
    AutoResizeDirective,
    MarkdownComponent,
    NgClass
  ],
  templateUrl: './article-form.component.html',
})
export class ArticleFormComponent implements OnInit{
  /**
   * If undefined, the forms represents the creation of a new article,
   * otherwise an existing article is fetched from the backend to be
   * edited, given its id.
   */
  readonly articleId?: string;
  loadingArticle: boolean = false;

  /**
   * Must be undefined only if an error occurred.
   */
  article?: Article = {
    id: '',
    creationDateTime: new Date(),
    title: '',
    minutesToRead: 0,
    shortDescription: '',
    content: ''
  };

  errorMessage: string = "";

  constructor(private articleService: ArticleService, private route: ActivatedRoute) {
    this.articleId = this.route.snapshot.paramMap.get('id')!;
  }

  ngOnInit(): void {
    if (!this.articleId) {
      return;
    }

    this.loadingArticle = true;
    this.articleService.fetchArticleById(this.articleId)
      .pipe(
        finalize(() => {
          this.loadingArticle = false;
        })
      )
      .subscribe({
        next: article => {
          this.article = article;
        },
        error: () => {
          this.article = undefined;
        }
      })
  }

  onSubmit(form: NgForm): void {
    if (!form.valid) {
      return;
    }

    let callable = this.articleService.updateArticle(this.article!);
    if (!this.articleId) {
      callable = this.articleService.insertArticle(this.article!);
    }

    callable.subscribe({
      next: response => {
        if (response.status === 200) {
          window.history.back();
        }
      },
      error: response => {
        if (response.status === 400) {
          this.errorMessage = "A client error has occurred. Verify the data written in the form";
        } else if (response.status === 500) {
          this.errorMessage = "A server error has occurred.";
        }
      }
    });
  }
}
