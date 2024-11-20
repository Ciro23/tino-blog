import {Component, OnInit} from '@angular/core';
import {FormsModule, NgForm} from "@angular/forms";
import {Article} from "../article";
import {ArticleService} from "../article-service";
import {ActivatedRoute} from "@angular/router";
import {NgIf} from "@angular/common";
import {AutoResizeDirective} from "../../auto-resize.directive";
import {MarkdownComponent} from "ngx-markdown";

@Component({
  selector: 'app-article-form',
  standalone: true,
  imports: [
    FormsModule,
    NgIf,
    AutoResizeDirective,
    MarkdownComponent
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

  article: Article = {
    id: '',
    creationDateTime: new Date(),
    title: '',
    shortDescription: '',
    content: ''
  };

  constructor(private articleService: ArticleService, private route: ActivatedRoute) {
    this.articleId = this.route.snapshot.paramMap.get('id')!;
  }

  ngOnInit(): void {
    if (!this.articleId) {
      return;
    }

    this.loadingArticle = true;
    this.articleService.fetchArticleById(this.articleId).subscribe(article => {
      this.article = article;
      this.loadingArticle = false;
    })
  }

  onSubmit(form: NgForm): void {
    let callable = this.articleService.updateArticle(this.article);
    if (!this.articleId) {
      callable = this.articleService.insertArticle(this.article);
    }

    callable.subscribe({
      next: response => {
        if (response.status === 200) {
          window.history.back();
        }
      }
    });
  }
}
