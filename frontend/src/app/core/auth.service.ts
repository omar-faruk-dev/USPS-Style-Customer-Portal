import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { tap } from 'rxjs';

@Injectable({ providedIn: 'root' })
export class AuthService {
  private api = 'http://localhost:8080/api/auth';
  constructor(private http: HttpClient) {}

  login(email: string, password: string) {
    return this.http.post<any>(`${this.api}/login`, { email, password }).pipe(tap(t => this.save(t)));
  }
  register(email: string, password: string) {
    return this.http.post<any>(`${this.api}/register`, { email, password }).pipe(tap(t => this.save(t)));
  }
  refresh() { return this.http.post<any>(`${this.api}/refresh`, { refreshToken: localStorage.getItem('refreshToken') }); }
  save(tokens: any) { localStorage.setItem('accessToken', tokens.accessToken); localStorage.setItem('refreshToken', tokens.refreshToken); }
  token() { return localStorage.getItem('accessToken'); }
  isAuthenticated() { return !!this.token(); }
}
