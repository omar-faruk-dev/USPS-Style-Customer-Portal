import { Component, OnInit } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { HttpClient } from '@angular/common/http';
import { RouterLink } from '@angular/router';

@Component({ standalone: true, imports: [CommonModule, FormsModule, RouterLink], template: `<h2>Shipments</h2>
<div><input [(ngModel)]="trackingNumber" placeholder="Tracking #"><input [(ngModel)]="status" placeholder="Status"><button (click)="create()">Create</button></div>
<table><tr><th>ID</th><th>Tracking</th><th>Status</th></tr>
<tr *ngFor="let s of items"><td><a [routerLink]="['/shipments', s.id]">{{s.id}}</a></td><td>{{s.trackingNumber}}</td><td>{{s.status}}</td></tr></table>` })
export class ShipmentsComponent implements OnInit {
  items:any[]=[]; trackingNumber=''; status='CREATED';
  constructor(private http: HttpClient) {}
  ngOnInit(){ this.load(); }
  load(){ this.http.get<any>('http://localhost:8080/api/shipments').subscribe(r=>this.items=r.content||[]); }
  create(){ this.http.post('http://localhost:8080/api/shipments',{trackingNumber:this.trackingNumber,status:this.status}).subscribe(()=>this.load()); }
}
