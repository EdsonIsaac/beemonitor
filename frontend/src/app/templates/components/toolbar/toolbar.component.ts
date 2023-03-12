import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { User } from 'src/app/entities/user';
import { NotificationType } from 'src/app/enums/notification-type';
import { AuthService } from 'src/app/services/auth.service';
import { NotificationService } from 'src/app/services/notification.service';
import { UserService } from 'src/app/services/user.service';
import { MessageUtils } from 'src/app/utils/message-utils';

@Component({
  selector: 'app-toolbar',
  templateUrl: './toolbar.component.html',
  styleUrls: ['./toolbar.component.sass']
})
export class ToolbarComponent implements OnInit {
  
  user!: any;
  userRemoto!: User;

  constructor(
    private _authService: AuthService,
    private _notificationService: NotificationService,
    private _router: Router,
    private _userService: UserService
  ) { }

  ngOnInit(): void {
    
    this.user = this._authService.getUser();
    
    this._userService.search(this.user.username, 0, 1, 'name', 'asc').subscribe({

      next: (users) => {
        this.userRemoto = users.content[0];
      },

      error: (error) => {
        console.error(error);
        this._notificationService.show(MessageUtils.USER_GET_FAIL, NotificationType.FAIL); 
      }
    })
  }

  logout() {
    this._authService.logout();
    this._router.navigate(['']);
  }
}