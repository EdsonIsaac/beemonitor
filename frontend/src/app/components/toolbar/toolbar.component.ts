import { Component, OnInit } from '@angular/core';
import { Authentication } from 'src/app/entities/authentication';
import { User } from 'src/app/entities/user';
import { NotificationType } from 'src/app/enums/notification-type';
import { AuthenticationService } from 'src/app/services/authentication.service';
import { NotificationService } from 'src/app/services/notification.service';
import { RedirectService } from 'src/app/services/redirect.service';
import { UserService } from 'src/app/services/user.service';
import { MessageUtils } from 'src/app/utils/message-utils';
import { environment } from 'src/environments/environment';

@Component({
  selector: 'app-toolbar',
  templateUrl: './toolbar.component.html',
  styleUrls: ['./toolbar.component.sass'],
})
export class ToolbarComponent implements OnInit {
  
  api!: string;
  authentication!: Authentication | null;
  theme!: string;
  user!: User;

  constructor(
    private _authenticationService: AuthenticationService,
    private _notificationService: NotificationService,
    private _redirectService: RedirectService,
    private _userService: UserService
  ) {}

  ngOnInit(): void {
    this.api = environment.api;
    this.authentication = this._authenticationService.getAuthentication();
    this.theme = localStorage.getItem('theme') ?? 'light';

    this.checkTheme();

    if (this.authentication) {
      this._userService
        .search(this.authentication.username, 0, 1, 'name', 'asc')
        .subscribe({
          next: (users) => {
            this.user = users.content[0];
          },

          error: (error) => {
            console.error(error);
            this._notificationService.show(
              MessageUtils.getMessage(error),
              NotificationType.FAIL
            );
          },
        });
    }
  }

  changeTheme() {
    this.theme = this.theme === 'dark' ? 'light' : 'dark';
    this.checkTheme();
  }

  checkTheme() {
  
    if (this.theme === 'dark') {
      document.documentElement.classList.add('dark');
      localStorage.setItem('theme', 'dark');
    }

    else {
      document.documentElement.classList.remove('dark');
      localStorage.setItem('theme', 'light');
    }
  }

  redirectToPanel() {
    this._redirectService.toPanel();
  }

  logout() {
    this._authenticationService.logout();
    this._redirectService.toLogin();
  }
}
