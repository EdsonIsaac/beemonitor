import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { JwtHelperService } from '@auth0/angular-jwt';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';
import { map } from 'rxjs/operators';

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  private jwtHelper : JwtHelperService;

  constructor(private http: HttpClient) {
    this.jwtHelper = new JwtHelperService();
  }

  isAuthenticated () : boolean {
    const currentUser = this.getCurrentUser();

    if (currentUser) {
      
      const token = currentUser.token.substring(7);

      if (token) {
        return !this.jwtHelper.isTokenExpired(token);
      }
    }

    return false;
  }

  login(usuario: any) : Observable<any> {
    
    return this.http.post(environment.serverURL + "/login", usuario, {
      observe: 'response',
      responseType: 'json'
    })
    .pipe(map(response => {
      this.setCurrentUser(
        {token: response.headers.get('Authorization')}
      )
    }));
  }

  logout () {
    localStorage.removeItem("currentUser");
  }

  getCurrentUser() : any {
    let currentUser: any = localStorage.getItem("currentUser");

    try {
      currentUser = JSON.parse(currentUser);
      currentUser.username = this.jwtHelper.decodeToken(currentUser.token).sub;
    } catch (error) { }
    
    return currentUser;
  }

  setCurrentUser(currentUser : any) : void {

    if (localStorage.getItem("currentUser")) {
      localStorage.removeItem("currentUser");
    }

    localStorage.setItem("currentUser", JSON.stringify(currentUser));
  }
}