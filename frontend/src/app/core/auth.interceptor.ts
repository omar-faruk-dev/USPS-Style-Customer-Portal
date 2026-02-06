import { HttpErrorResponse, HttpInterceptorFn } from '@angular/common/http';
import { inject } from '@angular/core';
import { AuthService } from './auth.service';
import { catchError, switchMap, throwError } from 'rxjs';

export const authInterceptor: HttpInterceptorFn = (req, next) => {
  const auth = inject(AuthService);
  const token = auth.token();
  const withAuth = token ? req.clone({ setHeaders: { Authorization: `Bearer ${token}` } }) : req;
  return next(withAuth).pipe(catchError((err: HttpErrorResponse) => {
    if (err.status === 401 && !req.url.includes('/auth/refresh')) {
      return auth.refresh().pipe(switchMap((t: any) => {
        localStorage.setItem('accessToken', t.accessToken);
        return next(req.clone({ setHeaders: { Authorization: `Bearer ${t.accessToken}` } }));
      }));
    }
    return throwError(() => err);
  }));
};
