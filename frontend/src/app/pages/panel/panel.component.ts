import { Component, OnInit } from '@angular/core';
import { Authentication } from 'src/app/models/authentication';
import { AuthenticationService } from 'src/app/services/authentication.service';
import { RedirectService } from 'src/app/services/redirect.service';

@Component({
	selector: 'app-panel',
	templateUrl: './panel.component.html',
	styleUrls: ['./panel.component.sass'],
})
export class PanelComponent {

	public isLoading!: boolean;

	constructor(
		private readonly _authenticationService: AuthenticationService,
		private readonly _redirectService: RedirectService
	) { }

	hasRole(role: string) {
		return this._authenticationService.hasRole(role);
	}

	redirectToHiveList() {
		this._redirectService.toHiveList();
	}

	redirectToUserList() {
		this._redirectService.toUserList();
	}
}