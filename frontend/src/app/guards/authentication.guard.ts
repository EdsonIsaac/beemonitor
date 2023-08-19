import { Injectable } from '@angular/core';
import { ActivatedRouteSnapshot, Router, RouterStateSnapshot } from '@angular/router';

import { AuthenticationService } from '../services/authentication.service';

@Injectable({
  providedIn: 'root',
})
export class AuthGuard {
  constructor(
    private _authenticationService: AuthenticationService,
    private _router: Router
  ) {}

  async canActivate(
    route: ActivatedRouteSnapshot,
    state: RouterStateSnapshot
  ): Promise<boolean> {
    
    const isAuthenticated = await this._authenticationService.isAuthenticated();
    
    if (isAuthenticated) {
      return true;
    }

    this._router.navigate(['/']);
    return false;
  }
}
