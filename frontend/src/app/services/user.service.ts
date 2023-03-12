import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { environment } from 'src/environments/environment';

import { User } from '../entities/user';
import { Page } from '../entities/page';

@Injectable({
  providedIn: 'root'
})
export class UserService {

  private _baseURL = environment.apiURL + '/users';

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
   * @param user 
   * @returns 
   */
  save(user: User) {
    return this.http.post<User>(this._baseURL, user);
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
   * @returns 
   */
  update(user: User) {
    return this.http.put<User>(this._baseURL + '/' + user.id, user);
  }
}