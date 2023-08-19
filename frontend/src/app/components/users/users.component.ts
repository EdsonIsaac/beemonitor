import { AfterViewInit, Component, ViewChild } from '@angular/core';
import { MatPaginator } from '@angular/material/paginator';
import { User } from 'src/app/entities/user';
import { NotificationType } from 'src/app/enums/notification-type';
import { NotificationService } from 'src/app/services/notification.service';
import { RedirectService } from 'src/app/services/redirect.service';
import { UserService } from 'src/app/services/user.service';
import { MessageUtils } from 'src/app/utils/message-utils';
import { OperatorUtils } from 'src/app/utils/operator-utils';
import { environment } from 'src/environments/environment';

@Component({
  selector: 'app-users',
  templateUrl: './users.component.html',
  styleUrls: ['./users.component.sass'],
})
export class UsersComponent implements AfterViewInit {
  api!: string;
  filterString!: string;
  isLoadingResults!: boolean;
  pageIndex!: number;
  pageSize!: number;
  resultsLength!: number;
  users!: Array<User>;

  @ViewChild(MatPaginator) paginator!: MatPaginator;

  constructor(
    private _notificationService: NotificationService,
    private _redirectService: RedirectService,
    private _userService: UserService
  ) {
    this.api = environment.api;
    this.isLoadingResults = true;
    this.resultsLength = 0;
  }

  ngAfterViewInit(): void {
    this.findAll();
  }

  add() {
    this._redirectService.toUser('register');
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
        this._notificationService.show(
          MessageUtils.getMessage(error),
          NotificationType.FAIL
        );
      },
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
        this._notificationService.show(
          MessageUtils.getMessage(error),
          NotificationType.FAIL
        );
      },
    });
  }

  update(user: User) {
    this._redirectService.toUser(user.id);
  }
}
