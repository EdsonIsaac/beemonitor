import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { JwtHelperService } from '@auth0/angular-jwt';
import { CookieService } from 'ngx-cookie-service';
import { BehaviorSubject } from 'rxjs';
import { environment } from 'src/environments/environment';

import { Authentication } from '../models/authentication';

@Injectable({
	providedIn: 'root',
})
export class AuthenticationService {
	
	private _baseURL: string = `${environment.api}/auth`;
	private _jwtHelper: JwtHelperService = new JwtHelperService();
	private _subject = new BehaviorSubject<Authentication | null>(null);

	constructor(
		private readonly _http: HttpClient,
		private readonly _cookieService: CookieService
	) {
		this.init();
	}

	getAuthentication() {
		return this._subject.asObservable();
	}

	hasRole(role: string) {
		const authentication = this._subject.value;
		return (!!authentication && authentication.roles.some(r => r.toUpperCase() === role.toUpperCase()));
	}

	init() {

		const access_token = this._cookieService.get('access_token');

		if (access_token && access_token !== '') {
			const authentication = this.decodeToken(access_token);
			this._subject.next(authentication);
		}
	}

	isAuthenticated(): boolean {
		const authentication = this._subject.value;
		return (!!authentication && !this._jwtHelper.isTokenExpired(authentication.access_token));
	}

	token(user: any) {
		return this._http.post<Authentication>(`${this._baseURL}/token`, user);
	}

	logout() {
		this._cookieService.delete('access_token', '/');
		this._subject.next(null);
	}

	setAuthentication(authentication: Authentication) {

		authentication = this.decodeToken(authentication.access_token);
		this._subject.next(authentication);
		this._cookieService.set("access_token", authentication.access_token, {
			expires: new Date(authentication.expires * 1000),
			path: '/'
		});
	}

	private decodeToken(access_token: string) {
		
		const decodedToken = this._jwtHelper.decodeToken(access_token);
		const authentication: Authentication = {
			access_token: access_token,
			username: decodedToken.sub,
			expires: decodedToken.exp,
			roles: decodedToken.scope,
			profile: decodedToken.profile
		};

		return authentication;
	}
}