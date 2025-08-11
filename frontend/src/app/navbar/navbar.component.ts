import { Component } from '@angular/core';
import { RouterLink } from "@angular/router";
import { AuthService } from "../authentication/auth.service";

@Component({
  selector: 'app-navbar',
  standalone: true,
  imports: [
    RouterLink,
  ],
  templateUrl: './navbar.component.html',
  styleUrls: ['./navbar.component.css']
})
export class NavbarComponent {

  constructor(protected authService: AuthService) { }
}
