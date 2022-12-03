import { Injectable } from '@angular/core';

import { Hive } from '../entities/hive';
import { NotificationType } from '../enums/notification-type';
import { AuthService } from './auth.service';
import { HiveService } from './hive.service';
import { MensurationService } from './mensuration.service';
import { NotificationService } from './notification.service';

@Injectable({
  providedIn: 'root'
})
export class FacadeService {

  constructor(
    private authService: AuthService,
    private hiveService: HiveService,
    private mensurationService: MensurationService,
    private notificationService: NotificationService
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
   * @param hive 
   * @returns 
   */
  hiveDelete(hive: Hive) {
    return this.hiveService.delete(hive);
  }

  /**
   * 
   * @returns 
   */
  hiveFindAll() {
    return this.hiveService.findAll();
  }

  /**
   * 
   * @param id 
   * @returns 
   */
  hiveFindById(id: string) {
    return this.hiveService.findById(id);
  }

  /**
   * 
   * @param hive 
   * @returns 
   */
  hiveSave(hive: Hive) {
    return this.hiveService.save(hive);
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

  ////////////////////////////////////////////////// NOTIFICATION //////////////////////////////////////////////////

  /**
   * 
   * @param message 
   * @param type 
   */
  notificationShowNotification(message: string, type: NotificationType) {
    this.notificationService.showNotification(message, type);
  }
}
