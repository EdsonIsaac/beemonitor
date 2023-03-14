import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { BehaviorSubject } from 'rxjs';
import { environment } from 'src/environments/environment';

import { Page } from '../entities/page';
import { User } from '../entities/user';

@Injectable({
  providedIn: 'root'
})
export class UserService {

  private _baseURL = environment.apiURL + '/users';
  private _subject = new BehaviorSubject<User | null>(null);

  constructor(
    private http: HttpClient
  ) { }

  /**
   * 
   * @returns 
   */
  findAll(page: number, size: number, sort: string, direction: string) {
    
    return this.http.get<Page<User>>(this._baseURL, {

      params: {
        page: page,
        size: size, 
        sort: sort,
        direction: direction
      }
    });
  }

  /**
   * 
   * @param id 
   * @returns 
   */
  findById(id: string) {
    return this.http.get<User>(this._baseURL + '/' + id);
  }

  /**
   * 
   * @returns 
   */
  get() {
    return this._subject.asObservable();
  }

  /**
   * 
   * @param user 
   * @returns 
   */
  save(user: User, photo: any) {

    const form = new FormData();

    form.append('user', new Blob([JSON.stringify(user)], { type: 'application/json' }));

    if (photo) {
      form.append('photo', new Blob([photo], { type: 'multipart/form-data' }), 'photo.png');
    }

    return this.http.post<User>(this._baseURL, form);
  }

  /**
   * 
   * @param value 
   * @param page 
   * @param size 
   * @param sort 
   * @param direction 
   * @returns 
   */
  search(value: string, page: number, size: number, sort: string, direction: string) {

    return this.http.get<Page<User>>(this._baseURL + '/search', {
      
      params: {
        value: value,
        page: page,
        size: size, 
        sort: sort,
        direction: direction
      }
    });
  }

  /**
   * 
   * @param user 
   */
  set(user: User) {
    this._subject.next(user);
  }

  /**
   * 
   * @param user 
   * @returns 
   */
  update(user: User, photo: any) {

    const form = new FormData();

    form.append('user', new Blob([JSON.stringify(user)], { type: 'application/json' }));

    if (photo) {
      form.append('photo', new Blob([photo], { type: 'multipart/form-data' }), 'photo.png');
    }

    return this.http.put<User>(this._baseURL + '/' + user.id, form);
  }
}