import { Injectable } from '@angular/core';
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {BehaviorSubject, catchError, map, Observable, of, tap} from "rxjs";

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  private tokenKey = 'jwt_token';
  private loggedIn = new BehaviorSubject<boolean>(this.isTokenValid());

  constructor(private http: HttpClient) {}

  login(username: string, password: string): Observable<boolean> {
    let headers = new HttpHeaders({
      "Authorization": `Basic ${btoa(username + ':' + password)}`
    });
    let options = { headers, responseType: 'text' as 'json' };

    return this.http.post<string>("/api/token", {}, options)
      .pipe(
        tap(token => {
          this.storeToken(token);
          this.loggedIn.next(true);
        }),
        map(() => true),
        catchError(() => {
          this.loggedIn.next(false);
          return of(false);
        })
      );
  }

  logout() {
    this.removeToken();
    this.loggedIn.next(false);
  }

  getToken(): string | null {
    return localStorage.getItem(this.tokenKey);
  }

  isTokenValid(): boolean {
    const jwtToken = this.getToken();
    if (!jwtToken) {
      return false;
    }

    const encodedPayload = jwtToken.split(".")[1];
    const payload = JSON.parse(atob(encodedPayload));

    return Math.floor(new Date().getTime() / 1000) < payload?.exp;
  }

  private storeToken(token: string) {
    localStorage.setItem(this.tokenKey, token);
  }

  private removeToken() {
    localStorage.removeItem(this.tokenKey);
  }
}
