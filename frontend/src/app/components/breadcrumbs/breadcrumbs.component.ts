import { Component } from '@angular/core';
import { Authentication } from 'src/app/models/authentication';
import { Route } from 'src/app/models/route';
import { AuthenticationService } from 'src/app/services/authentication.service';
import { HiveService } from 'src/app/services/hive.service';
import { UserService } from 'src/app/services/user.service';

@Component({
	selector: 'app-breadcrumbs',
	templateUrl: './breadcrumbs.component.html',
	styleUrls: ['./breadcrumbs.component.sass'],
})
export class BreadcrumbsComponent {
	isBuilding!: boolean;
	routes!: Array<Route>;

	private _authentication!: Authentication;

	constructor(
		private _authenticationService: AuthenticationService,
		private _hiveService: HiveService,
		private _userService: UserService
	) { }

	ngOnInit(): void {
		
		this._authenticationService.getAuthentication().subscribe({

			next: (authentication) => {
				
				if (authentication) {
					this._authentication = authentication;
					this.isBuilding = true;
					this.routes = this.getRoutes();
				}
			}
		});
	}

	getObjectName() {

		let name = '';
		const path = window.location.pathname;

		if (path.includes('hives')) {
			this._hiveService.getHive().subscribe({
				next: (hive) => {
					name = hive ? hive.code.toUpperCase() : 'Cadastro';
					this.checkObjectChange();
				}
			});
		}

		else if (path.includes('users')) {
			this._userService.get().subscribe({
				next: (user) => {
					name = user ? user.name.toUpperCase() : 'Cadastro';
					this.checkObjectChange();
				}
			});
		}

		else {
			name = 'Cadastro';
		}

		return name;
	}

	getRoutes() {

		const routes: Array<Route> = [];
		const path = window.location.pathname
			.split('/')
			.filter((x) => x !== '' && x !== 'panel');

		this.isBuilding = true;

		path.forEach((route, index) => {
			switch (route) {
				case 'hives':
					routes.push({
						label: 'Colmeias',
						route: `/${this._authentication.profile.toLowerCase()}/hives`,
						active: index === path.length - 1,
					});
					break;
				case 'users':
					routes.push({
						label: 'Usuários',
						route: `/${this._authentication.profile.toLowerCase()}/users`,
						active: index === path.length - 1,
					});
					break;
				case this._authentication.profile.toLowerCase():
					routes.push({
						label: 'Início',
						route: `/${this._authentication.profile.toLowerCase()}/panel`,
						active: index === path.length - 1,
					});
					break;
				default:
					routes.push({
						label: this.getObjectName(),
						route: null,
						active: index === path.length - 1,
					});
			}
		});

		this.isBuilding = false;
		return routes;
	}
	
	private checkObjectChange() {
		!this.isBuilding ? this.ngOnInit() : null;
	}
}
