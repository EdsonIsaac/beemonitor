import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { BehaviorSubject } from 'rxjs';
import { environment } from 'src/environments/environment';

import { Page } from '../models/page';
import { User } from '../models/user';
import { AuthenticationService } from './authentication.service';

@Injectable({
	providedIn: 'root',
})
export class UserService {

	private _baseURL = `${environment.api}/users`;
	private _subject = new BehaviorSubject<User | null>(null);
	private _userLogged = new BehaviorSubject<User | null>(null);

	constructor(
		private readonly _authenticationService: AuthenticationService,
		private readonly _http: HttpClient
	) {
		this.init();
	}

	findAll(page: number, size: number, sort: string, direction: string) {
		
		return this._http.get<Page<User>>(this._baseURL, {
			params: {
				page: page,
				size: size,
				sort: sort,
				direction: direction,
			},
		});
	}

	findById(id: string) {
		return this._http.get<User>(`${this._baseURL}/${id}`);
	}

	get() {
		return this._subject.asObservable();
	}

	getUserLogged() {
		return this._userLogged.asObservable();
	}

	init() {

		this._authenticationService.getAuthentication().subscribe({

			next: (authentication) => {

				if (authentication) {

					this.search(authentication.username, 0, 1, 'name', 'asc').subscribe({

						next: (users) => {
							this._userLogged.next(users.content[0]);
						}
					});
				}

				else {
					this._userLogged.next(null);
				}
			}
		});
	}

	save(user: User, photo: any) {

		const form = new FormData();

		form.append('user', new Blob([JSON.stringify(user)], { type: 'application/json' }));

		if (photo) {
			form.append('photo', new Blob([photo], { type: 'multipart/form-data' }), 'photo.png');
		}

		return this._http.post<User>(this._baseURL, form);
	}

	search(
		value: string,
		page: number,
		size: number,
		sort: string,
		direction: string
	) {

		return this._http.get<Page<User>>(`${this._baseURL}/search`, {
			params: {
				value: value,
				page: page,
				size: size,
				sort: sort,
				direction: direction,
			},
		});
	}

	set(user: User | null) {
		this._subject.next(user);
	}

	setUserLogged(user: User | null) {
		this._userLogged.next(user);
	}

	update(user: User, photo: any) {

		const form = new FormData();

		form.append('user', new Blob([JSON.stringify(user)], { type: 'application/json' }));

		if (photo) {
			form.append('photo', new Blob([photo], { type: 'multipart/form-data' }), 'photo.png');
		}

		return this._http.put<User>(`${this._baseURL}/${user.id}`, form);
	}
}