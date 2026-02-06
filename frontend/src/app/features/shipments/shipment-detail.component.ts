import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpClient } from '@angular/common/http';
import { FormsModule } from '@angular/forms';

@Component({ standalone: true, imports: [CommonModule, FormsModule], template: `<h2>Shipment Detail</h2>
<div *ngIf="shipment">Tracking: {{shipment.trackingNumber}}</div>
<input [(ngModel)]="status" placeholder="New status"><button (click)="save()">Save</button>` })
export class ShipmentDetailComponent implements OnInit {
  shipment:any; id=''; status='';
  constructor(private route: ActivatedRoute, private http: HttpClient) {}
  ngOnInit(){ this.id=this.route.snapshot.paramMap.get('id')||''; this.http.get(`http://localhost:8080/api/shipments/${this.id}`).subscribe(s=>this.shipment=s); }
  save(){ this.http.patch(`http://localhost:8080/api/shipments/${this.id}`,{status:this.status}).subscribe(); }
}
