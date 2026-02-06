import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { HttpClient } from '@angular/common/http';

@Component({ standalone: true, imports: [CommonModule, FormsModule], template: `<h2>Track package</h2>
<input [(ngModel)]="tracking" placeholder="Tracking Number"><button (click)="lookup()">Track</button>
<pre>{{result | json}}</pre>` })
export class TrackComponent {
  tracking=''; result:any;
  constructor(private http: HttpClient) {}
  lookup(){ this.http.get(`http://localhost:8080/api/track/${this.tracking}`).subscribe(r=>this.result=r); }
}
