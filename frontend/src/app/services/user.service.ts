import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { environment } from 'src/environments/environment';

import { User } from '../entities/user';

@Injectable({
  providedIn: 'root'
})
export class UserService {

  constructor(private http: HttpClient) { }

  /**
   * 
   * @returns 
   */
  findAll() {
    return this.http.get<Array<User>>(environment.apiURL + '/users');
  }

  /**
   * 
   * @param user 
   * @returns 
   */
  save(user: User) {
    return this.http.post<User>(environment.apiURL + '/users', user);
  }

  /**
   * 
   * @param user 
   * @returns 
   */
  update(user: User) {
    return this.http.put<User>(environment.apiURL + '/users/' + user.id, user);
  }
}