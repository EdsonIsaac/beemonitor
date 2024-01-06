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
	) { }

	canActivate(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): boolean {

		if (this._authenticationService.isAuthenticated()) {
			return true;
		}

		this._router.navigate(['/']);
		return false;
	}
}