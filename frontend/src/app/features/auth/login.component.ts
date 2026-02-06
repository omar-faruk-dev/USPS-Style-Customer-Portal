import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { Router, RouterLink } from '@angular/router';
import { AuthService } from '../../core/auth.service';

@Component({ standalone: true, imports: [FormsModule, RouterLink], template: `<h2>Login</h2>
<input [(ngModel)]="email" placeholder="Email"><input [(ngModel)]="password" type="password" placeholder="Password">
<button (click)="login()">Login</button> <a routerLink="/register">Register</a>` })
export class LoginComponent {
  email=''; password='';
  constructor(private auth: AuthService, private router: Router) {}
  login(){ this.auth.login(this.email,this.password).subscribe(()=>this.router.navigate(['/dashboard'])); }
}
