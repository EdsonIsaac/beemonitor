import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { environment } from 'src/environments/environment';

import { Mensuration } from '../models/mensuration';
import { Page } from '../models/page';

@Injectable({
	providedIn: 'root',
})
export class MensurationService {

	private _baseURL = `${environment.api}/mensurations`;

	constructor(private readonly _http: HttpClient) { }

	findAll(
		page: number,
		size: number,
		sort: string,
		direction: string
	) {
		return this._http.get<Page<Mensuration>>(this._baseURL, {
			params: {
				page: page,
				size: size,
				sort: sort,
				direction: direction,
			},
		});
	}

	search(
		hiveId: string,
		value: string,
		page: number,
		size: number,
		sort: string,
		direction: string
	) {
		return this._http.get<Page<Mensuration>>(`${this._baseURL}/search`, {
			params: {
				hiveId: hiveId,
				value: value,
				page: page,
				size: size,
				sort: sort,
				direction: direction,
			},
		});
	}
}