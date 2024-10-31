import { Component } from '@angular/core';
import {RouterLink} from "@angular/router";
import {AuthService} from "../authentication/auth.service";
import {NgIf} from "@angular/common";
import {asyncScheduler} from "rxjs";

@Component({
  selector: 'app-navbar',
  standalone: true,
  imports: [
    RouterLink,
    NgIf
  ],
  templateUrl: './navbar.component.html',
})
export class NavbarComponent {

  constructor(protected authService: AuthService) {}
}
