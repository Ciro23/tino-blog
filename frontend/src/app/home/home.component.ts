import { Component } from '@angular/core';
import { LatestArticlesComponent } from "../blog-article/latest-blog-articles/latest-blog-articles.component";

@Component({
  standalone: true,
  selector: 'app-home',
  imports: [
    LatestArticlesComponent
  ],
  templateUrl: './home.component.html'
})
export class HomeComponent {

}
