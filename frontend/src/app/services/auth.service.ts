import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { JwtHelperService } from '@auth0/angular-jwt';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  private _jwtHelper!: JwtHelperService;

  constructor(
    private _http: HttpClient, 
  ) {
    this._jwtHelper = new JwtHelperService();
  }

  getUser() {

    let user: any;
    
    try {
      user = localStorage.getItem("user");
      user = JSON.parse(user);
    } catch (error) { }

    return user;
  }

  isAuthenticated() {
    const user = this.getUser();

    if (user) {

      const token = user.token.substring(7);

      if (token) {
        return !this._jwtHelper.isTokenExpired(token);
      }
    }

    return false;
  }

  login(user: any) {
    return this._http.post<any>(environment.apiURL + '/login', user);
  }

  logout() {
    localStorage.removeItem("user");
  }

  setUser(user: any) {

    if (localStorage.getItem("user")) {
      localStorage.removeItem("user");
    }

    localStorage.setItem("user", JSON.stringify({
      role: user.role,
      token: user.access_token,
      username: this._jwtHelper.decodeToken(user.access_token).sub
    }));
  }
}