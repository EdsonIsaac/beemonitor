import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { User } from 'src/app/entities/user';
import { NotificationType } from 'src/app/enums/notification-type';
import { AuthService } from 'src/app/services/auth.service';
import { NotificationService } from 'src/app/services/notification.service';
import { UserService } from 'src/app/services/user.service';
import { MessageUtils } from 'src/app/utils/message-utils';

@Component({
  selector: 'app-user',
  templateUrl: './user.component.html',
  styleUrls: ['./user.component.sass']
})
export class UserComponent implements OnInit {

  user!: any;
  userRemote!: User;

  constructor(
    private _activatedRoute: ActivatedRoute,
    private _authService: AuthService,
    private _notificationService: NotificationService,
    private _userService: UserService,
  ) { }

  ngOnInit(): void {
    
    this.user = this._authService.getUser();

    this._activatedRoute.params.subscribe({

      next: (x: any) => {
          
        if (x && x.id) {
          
          this._userService.findById(x.id).subscribe({
            
            next: (user) => {
              this._userService.set(user);
            },

            error: (error) => {
              console.error(error);
              this._notificationService.show(MessageUtils.USER_GET_FAIL, NotificationType.FAIL); 
            }
          });
        }
      },
    });

    this._userService.get().subscribe({

      next: (user) => {
        
        if (user) {
          this.userRemote = user;
        }
      }
    });
  }
}