import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { BehaviorSubject } from 'rxjs';
import { environment } from 'src/environments/environment';

import { Hive } from '../entities/hive';
import { Page } from '../entities/page';

@Injectable({
  providedIn: 'root',
})
export class HiveService {
  private _baseURL = `${environment.api}/hives`;
  private _subject = new BehaviorSubject<Hive | null>(null);

  constructor(private http: HttpClient) {}

  delete(hive: Hive) {
    return this.http.delete(`${this._baseURL}/${hive.id}`);
  }

  findAll(page: number, size: number, sort: string, direction: string) {
    return this.http.get<Page<Hive>>(this._baseURL, {
      params: {
        page: page,
        size: size,
        sort: sort,
        direction: direction,
      },
    });
  }

  findById(id: string) {
    return this.http.get<Hive>(`${this._baseURL}/${id}`);
  }

  get() {
    return this._subject.asObservable();
  }

  save(hive: Hive) {
    return this.http.post<Hive>(this._baseURL, hive);
  }

  search(
    value: string,
    page: number,
    size: number,
    sort: string,
    direction: string
  ) {
    return this.http.get<Page<Hive>>(`${this._baseURL}/search`, {
      params: {
        value: value,
        page: page,
        size: size,
        sort: sort,
        direction: direction,
      },
    });
  }

  set(hive: Hive | null) {
    this._subject.next(hive);
  }

  update(hive: Hive) {
    return this.http.put<Hive>(`${this._baseURL}/${hive.id}`, hive);
  }
}
