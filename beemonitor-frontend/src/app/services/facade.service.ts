import { Injectable } from '@angular/core';

import { AuthService } from './auth.service';
import { HiveService } from './hive.service';
import { MensurationService } from './mensuration.service';

@Injectable({
  providedIn: 'root'
})
export class FacadeService {

  constructor(
    private authService: AuthService,
    private hiveService: HiveService,
    private mensurationService: MensurationService
  ) { }

  ////////////////////////////////////////////////// AUTHENTICATION //////////////////////////////////////////////////

  /**
     * 
     * @returns 
     */
   authIsAuthenticated() {
    return this.authService.isAuthenticated();
  }

  /**
   * 
   * @param user 
   * @returns 
   */
  authLogin(user: any) {
    return this.authService.login(user);
  }

  /**
   * 
   * @returns 
   */
  authLogout() {
    return this.authService.logout();
  }

  /**
   * 
   * @returns 
   */
  authGetCurrentUser() {
    return this.authService.getCurrentUser();
  }

  /**
   * 
   * @param currentUser 
   * @returns 
   */
  authSetCurrentUser(currentUser: any) {
    return this.authService.setCurrentUser(currentUser);
  }

  ////////////////////////////////////////////////// HIVE //////////////////////////////////////////////////

  /**
   * 
   * @returns 
   */
  hiveFindAll() {
    return this.hiveService.findAll();
  }

  ////////////////////////////////////////////////// MENSURATION //////////////////////////////////////////////////

  /**
   * 
   * @param hiveId 
   * @param size 
   * @returns 
   */
  mensurationGetMensurations(hiveId: string, size: number) {
    return this.mensurationService.getMensurations(hiveId, size);
  }
}
