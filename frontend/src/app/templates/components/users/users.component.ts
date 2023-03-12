import { AfterViewInit, Component, ViewChild } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { MatPaginator } from '@angular/material/paginator';
import { MatSort } from '@angular/material/sort';
import { MatTableDataSource } from '@angular/material/table';
import { User } from 'src/app/entities/user';
import { NotificationType } from 'src/app/enums/notification-type';
import { AuthService } from 'src/app/services/auth.service';
import { NotificationService } from 'src/app/services/notification.service';
import { UserService } from 'src/app/services/user.service';
import { MessageUtils } from 'src/app/utils/message-utils';

import { UserFormComponent } from '../user-form/user-form.component';
import { OperatorUtils } from 'src/app/utils/operator-utils';

@Component({
  selector: 'app-users',
  templateUrl: './users.component.html',
  styleUrls: ['./users.component.sass']
})
export class UsersComponent implements AfterViewInit {
  
  columns!: Array<string>;
  dataSource!: MatTableDataSource<User>;
  filterString!: string;
  isLoadingResults!: boolean;
  resultsLength!: number;
  user!: any;

  @ViewChild(MatPaginator) paginator!: MatPaginator;
  @ViewChild(MatSort) sort!: MatSort;

  constructor (
    private _authService: AuthService,
    private _dialog: MatDialog,
    private _notificationService: NotificationService,
    private _userService: UserService
  ) {
    this.columns = ['index', 'name', 'username', 'enabled', 'action'];
    this.dataSource = new MatTableDataSource();
    this.isLoadingResults = true;
    this.resultsLength = 0;
    this.user = this._authService.getUser();
  }

  ngAfterViewInit(): void {
    this.findAll();
  }

  add() {
    
    this._dialog.open(UserFormComponent, {
      data: {
        user: null
      },
      width: '100%'
    })
    .afterClosed().subscribe((result) => {

      if (result && result.status) {
        this.findAll();
      }
    });
  }

  async findAll() {

    const page: number = this.paginator.pageIndex;
    const size: number = this.paginator.pageSize;
    const sort: string = this.sort.active;
    const direction: string = this.sort.direction;

    this.isLoadingResults = true;
    await OperatorUtils.delay(1000);

    this._userService.findAll(page, size, sort, direction).subscribe({

      complete: () => {
        this.isLoadingResults = false;
      },

      next: (users) => {
        this.dataSource.data = users.content;
        this.resultsLength = users.totalElements;
      },

      error: (error) => {
        this.isLoadingResults = false;
        console.error(error);
        this._notificationService.show(MessageUtils.USERS_GET_FAIL, NotificationType.FAIL);
      }
    });
  }

  pageChange() {
    
    if (this.filterString) {
      this.search();
    }

    this.findAll();
  }

  async search() {

    const value: string = this.filterString;
    const page: number = this.paginator.pageIndex;
    const size: number = this.paginator.pageSize;
    const sort: string = this.sort.active;
    const direction: string = this.sort.direction;

    this.isLoadingResults = true;
    await OperatorUtils.delay(1000);

    this._userService.search(value, page, size, sort, direction).subscribe({

      complete: () => {
        this.isLoadingResults = false;
      },

      next: (users) => {
        this.dataSource.data = users.content;
        this.resultsLength = users.totalElements;
      },

      error: (error) => {
        this.isLoadingResults = false;
        console.error(error);
        this._notificationService.show(MessageUtils.USERS_GET_FAIL, NotificationType.FAIL);
      }
    });
  }

  sortChange() {

    this.paginator.pageIndex = 0;
    
    if (this.filterString) {
      this.search();
    }

    this.findAll();
  }

  update(user: User) {

    this._dialog.open(UserFormComponent, {
      data: {
        user: user
      },
      width: '100%'
    })
    .afterClosed().subscribe((result) => {

      if (result && result.status) {
        this.findAll();
      }
    });
  }
}