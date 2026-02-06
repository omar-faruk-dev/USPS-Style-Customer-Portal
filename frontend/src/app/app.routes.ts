import { Routes } from '@angular/router';
import { LoginComponent } from './features/auth/login.component';
import { RegisterComponent } from './features/auth/register.component';
import { DashboardComponent } from './features/dashboard/dashboard.component';
import { ShipmentsComponent } from './features/shipments/shipments.component';
import { ShipmentDetailComponent } from './features/shipments/shipment-detail.component';
import { TrackComponent } from './features/tracking/track.component';
import { authGuard } from './core/auth.guard';

export const routes: Routes = [
  { path: 'login', component: LoginComponent },
  { path: 'register', component: RegisterComponent },
  { path: 'dashboard', canActivate: [authGuard], component: DashboardComponent },
  { path: 'shipments', canActivate: [authGuard], component: ShipmentsComponent },
  { path: 'shipments/:id', canActivate: [authGuard], component: ShipmentDetailComponent },
  { path: 'track', canActivate: [authGuard], component: TrackComponent },
  { path: '', pathMatch: 'full', redirectTo: 'dashboard' }
];
