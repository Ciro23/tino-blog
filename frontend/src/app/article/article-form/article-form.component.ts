import { Component, OnInit } from '@angular/core';
import { FormsModule, NgForm, ReactiveFormsModule } from "@angular/forms";
import { Article } from "../article";
import { ArticleService } from "../article-service";
import { ActivatedRoute } from "@angular/router";
import { NgClass, NgIf } from "@angular/common";
import { AutoResizeDirective } from "../../directives/auto-resize.directive";
import { finalize } from "rxjs";
import { makeStringUrlCompatible } from "../../utilities/url-utilities";

@Component({
  selector: 'app-article-form',
  standalone: true,
  imports: [
    FormsModule,
    NgIf,
    AutoResizeDirective,
    NgClass,
    ReactiveFormsModule
  ],
  templateUrl: './article-form.component.html',
})
export class ArticleFormComponent implements OnInit {
  /**
   * If undefined, the forms represents the creation of a new article,
   * otherwise an existing article is fetched from the backend to be
   * edited, given its id.
   */
  articleId?: string;
  loadingArticle: boolean = false;

  /**
   * Must be undefined only if an error occurred.
   */
  article?: Article = {
    id: '',
    creationDateTime: new Date(),
    title: '',
    slug: '',
    minutesToRead: 0,
    shortDescription: '',
    content: ''
  };

  errorMessage: string = "";

  /**
   * True to display the button to generate the URL slug, starting
   * from the title, false otherwise.
   */
  showGenerateSlugButton: boolean = false;

  /**
   * True when {@link article.slug} is directly changed by
   * the user, false otherwise.
   */
  slugManuallyChanged: boolean = false;

  constructor(
    private articleService: ArticleService,
    private route: ActivatedRoute,
  ) { }

  ngOnInit(): void {
    this.articleId = this.route.snapshot.paramMap.get('id')!;
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

  onTitleChanged(title: string): void {
    if (this.slugManuallyChanged) {
      this.showGenerateSlugButton = true;
    } else {
      this.generateSlug(title);
    }
  }

  generateSlug(title: string): void {
    this.article!.slug = makeStringUrlCompatible(title);
    this.slugManuallyChanged = false;
    this.showGenerateSlugButton = false;
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
