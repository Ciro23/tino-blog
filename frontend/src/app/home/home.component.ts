import { Component } from '@angular/core';
import { LatestArticlesComponent } from "../article/latest-articles/latest-articles.component";

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
