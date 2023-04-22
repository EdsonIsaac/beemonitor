import { AfterViewInit, Component, ViewChild } from '@angular/core';
import { MatPaginator } from '@angular/material/paginator';
import { Router } from '@angular/router';
import { User } from 'src/app/entities/user';
import { NotificationType } from 'src/app/enums/notification-type';
import { AuthService } from 'src/app/services/auth.service';
import { NotificationService } from 'src/app/services/notification.service';
import { UserService } from 'src/app/services/user.service';
import { MessageUtils } from 'src/app/utils/message-utils';
import { OperatorUtils } from 'src/app/utils/operator-utils';
import { environment } from 'src/environments/environment';

@Component({
  selector: 'app-users',
  templateUrl: './users.component.html',
  styleUrls: ['./users.component.sass']
})
export class UsersComponent implements AfterViewInit {
  
  apiURL!: string;
  filterString!: string;
  isLoadingResults!: boolean;
  pageIndex!: number;
  pageSize!: number;
  resultsLength!: number;
  user!: any;
  users!: Array<User>;

  @ViewChild(MatPaginator) paginator!: MatPaginator;

  constructor (
    private _authService: AuthService,
    private _notificationService: NotificationService,
    private _router: Router,
    private _userService: UserService
  ) {
    this.apiURL = environment.apiURL;
    this.isLoadingResults = true;
    this.resultsLength = 0;
    this.user = this._authService.getUser();
  }

  ngAfterViewInit(): void {
    this.findAll();
  }

  add() {
    this._router.navigate(['/' + this.user.role.toLowerCase() + '/users/register']);
  }

  async findAll() {

    const page: number = this.paginator.pageIndex;
    const size: number = this.paginator.pageSize;
    const sort: string = 'name';
    const direction: string = 'asc';

    this.isLoadingResults = true;
    await OperatorUtils.delay(1000);

    this._userService.findAll(page, size, sort, direction).subscribe({

      complete: () => {
        this.isLoadingResults = false;
      },

      next: (users) => {
        this.users = users.content;
        this.resultsLength = users.totalElements;
      },

      error: (error) => {
        this.isLoadingResults = false;
        console.error(error);
        this._notificationService.show(MessageUtils.USERS_GET_FAIL, NotificationType.FAIL);
      }
    });
  }

  pageChange(event: any) {
    
    this.pageIndex = event.pageIndex;
    this.pageSize = event.pageSize;

    if (this.filterString) {
      this.search();
    }

    this.findAll();
  }

  async search() {

    const value: string = this.filterString;
    const page: number = this.paginator.pageIndex;
    const size: number = this.paginator.pageSize;
    const sort: string = 'name';
    const direction: string = 'asc';

    this.isLoadingResults = true;
    await OperatorUtils.delay(1000);

    this._userService.search(value, page, size, sort, direction).subscribe({

      complete: () => {
        this.isLoadingResults = false;
      },

      next: (users) => {
        this.users = users.content;
        this.resultsLength = users.totalElements;
      },

      error: (error) => {
        this.isLoadingResults = false;
        console.error(error);
        this._notificationService.show(MessageUtils.USERS_GET_FAIL, NotificationType.FAIL);
      }
    });
  }

  show(user: User) {
    this._router.navigate(['/' + this.user.role.toLowerCase() + '/users/' + user.id]);
  }
}