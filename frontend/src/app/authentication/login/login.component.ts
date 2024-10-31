import { Component } from '@angular/core';
import {FormBuilder, FormGroup, ReactiveFormsModule, Validators} from "@angular/forms";
import {AuthService} from "../auth.service";
import {Router} from "@angular/router";
import {NgIf} from "@angular/common";

@Component({
  selector: 'app-authentication',
  standalone: true,
  imports: [
    ReactiveFormsModule,
    NgIf
  ],
  templateUrl: './login.component.html',
})
export class LoginComponent {
  loginForm: FormGroup;
  wrongCredentials: boolean = false;

  constructor(
    private formBuilder: FormBuilder,
    private authService: AuthService,
    private router: Router,
  ) {
    this.loginForm = this.formBuilder.group({
      username: ['', [Validators.required, Validators.email]],
      password: ['', Validators.required]
    });
  }

  login(): void {
    if (!this.loginForm.valid) {
      return;
    }

    let credentials = this.loginForm.value;
    let result = this.authService.login(credentials['username'], credentials['password']);
    result.subscribe({
      next: success => {
        if (success) {
          void window.history.back();
        } else {
          this.wrongCredentials = true;
        }
      }
    });
  }
}
