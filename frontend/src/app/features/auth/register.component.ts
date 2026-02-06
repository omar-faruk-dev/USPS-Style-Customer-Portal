import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { Router } from '@angular/router';
import { AuthService } from '../../core/auth.service';

@Component({ standalone: true, imports: [FormsModule], template: `<h2>Register</h2>
<input [(ngModel)]="email" placeholder="Email"><input [(ngModel)]="password" type="password" placeholder="Password">
<button (click)="register()">Create account</button>` })
export class RegisterComponent {
  email=''; password='';
  constructor(private auth: AuthService, private router: Router) {}
  register(){ this.auth.register(this.email,this.password).subscribe(()=>this.router.navigate(['/dashboard'])); }
}
