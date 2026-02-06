import { Component } from '@angular/core';
import { RouterLink, RouterOutlet } from '@angular/router';

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [RouterOutlet, RouterLink],
  template: `<nav>
    <a routerLink="/dashboard">Dashboard</a>
    <a routerLink="/shipments">Shipments</a>
    <a routerLink="/track">Track</a>
    <a routerLink="/login">Login</a>
  </nav>
  <div class="container"><router-outlet/></div>`
})
export class AppComponent {}
