import { Component } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import { NavbarComponent } from "./navbar/navbar.component";
import { FooterComponent } from "./footer/footer.component";
import { AppUpdateService } from "./app-update/app-update-service";

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [
    RouterOutlet,
    NavbarComponent,
    FooterComponent,
  ],
  templateUrl: './app.component.html',
})
export class AppComponent {
  title = 'frontend';

  constructor(appUpdateService: AppUpdateService) {
    appUpdateService.init();
  }
}
