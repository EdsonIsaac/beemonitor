import { Injectable } from '@angular/core';
import { Router } from '@angular/router';

import { Authentication } from '../entities/authentication';
import { AuthenticationService } from './authentication.service';

@Injectable({
  providedIn: 'root',
})
export class RedirectService {
  private _authentication!: Authentication | null;
  private _baseURL!: string;

  constructor(
    private _authenticationService: AuthenticationService,
    private _router: Router
  ) {
    this._authenticationService.getAuthenticationAsObservable().subscribe({
      next: (authentication) => {
        if (authentication) {
          this._authentication = authentication;
          this._baseURL = '/' + this._authentication?.role.toLowerCase();
        }   
      }
    });
  }

  toHive(id: string) {
    this._router.navigate([`${this._baseURL}/hives/${id}`]);
  }

  toHiveList() {
    this._router.navigate([`${this._baseURL}/hives`]);
  }

  toLogin() {
    this._router.navigate([``]);
  }

  toPanel() {
    this._router.navigate([`${this._baseURL}/panel`]);
  }

  toUser(id: string) {
    this._router.navigate([`${this._baseURL}/users/${id}`]);
  }

  toUserList() {
    this._router.navigate([`${this._baseURL}/users`]);
  }
}
