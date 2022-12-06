import { Component, OnInit, ViewChild } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { MatPaginator } from '@angular/material/paginator';
import { MatSort } from '@angular/material/sort';
import { MatTableDataSource } from '@angular/material/table';
import { User } from 'src/app/entities/user';
import { NotificationType } from 'src/app/enums/notification-type';
import { FacadeService } from 'src/app/services/facade.service';
import { MessageUtils } from 'src/app/utils/message-utils';

import { UsersFormComponent } from '../users-form/users-form.component';

@Component({
  selector: 'app-users',
  templateUrl: './users.component.html',
  styleUrls: ['./users.component.sass']
})
export class UsersComponent implements OnInit {
  
  columns!: Array<string>;
  currentUser!: any;
  dataSource!: MatTableDataSource<User>;

  @ViewChild(MatPaginator, { static: false }) set paginator(value: MatPaginator) { if (this.dataSource) this.dataSource.paginator = value }
  @ViewChild(MatSort, { static: false }) set sort(value: MatSort) { if (this.dataSource) this.dataSource.sort = value }

  constructor (
    private dialog: MatDialog,
    private facade: FacadeService
  ) { }

  ngOnInit(): void {
    this.columns = ['index', 'name', 'username', 'enabled', 'action'];
    this.currentUser = this.facade.authGetCurrentUser();
    this.dataSource = new MatTableDataSource();
    this.findAllUsers();
  }

  addUser() {
    
    this.dialog.open(UsersFormComponent, {
      data: {
        user: null
      },
      width: '100%'
    })
    .afterClosed().subscribe((result) => {

      if (result && result.status) {
        this.findAllUsers();
      }
    });
  }

  buildTable(users: Array<User>) {
    this.dataSource.data = users;
    this.dataSource.filterPredicate = (user: User, filter: string) => user.name.toUpperCase().includes(filter) || user.username.toUpperCase().includes(filter);
  }

  filter(value: string) {
    value = value.toUpperCase();
    this.dataSource.filter = value;
  }

  findAllUsers() {

    this.facade.userFindAll().subscribe({

      next: (users) => {
        this.buildTable(users);
      },

      error: (error) => {
        console.error(error);
        this.facade.notificationShowNotification(MessageUtils.USERS_GET_FAIL, NotificationType.FAIL);
      }
    });
  }

  updateUser(user: User) {

    this.dialog.open(UsersFormComponent, {
      data: {
        user: user
      },
      width: '100%'
    })
    .afterClosed().subscribe((result) => {

      if (result && result.status) {
        this.findAllUsers();
      }
    });
  }
}