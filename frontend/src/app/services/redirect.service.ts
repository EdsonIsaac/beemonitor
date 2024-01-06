import { Injectable } from '@angular/core';
import { Router } from '@angular/router';

import { AuthenticationService } from './authentication.service';

@Injectable({
	providedIn: 'root',
})
export class RedirectService {
	
	private _baseURL!: string;

	constructor(
		private readonly _authenticationService: AuthenticationService,
		private readonly _router: Router
	) {

		this._authenticationService.getAuthentication().subscribe({

			next: (authentication) => {

				if (authentication) {
					this._baseURL = '/' + authentication.profile.toLowerCase();
				}
			}
		});
	}

	toHive(id: string) {
		this._router.navigate([`${this._baseURL}/hives/${id}`]);
	}

	toHiveList() {
		this._router.navigate([`${this._baseURL}/hives`]);
	}

	toLogin() {
		this._router.navigate([``]);
	}

	toPanel() {
		this._router.navigate([`${this._baseURL}/panel`]);
	}

	toUser(id: string) {
		this._router.navigate([`${this._baseURL}/users/${id}`]);
	}

	toUserList() {
		this._router.navigate([`${this._baseURL}/users`]);
	}
}