import { Component, OnInit, ViewChild } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { MatPaginator } from '@angular/material/paginator';
import { Hive } from 'src/app/entities/hive';
import { NotificationType } from 'src/app/enums/notification-type';
import { AuthService } from 'src/app/services/auth.service';
import { HiveService } from 'src/app/services/hive.service';
import { MensurationService } from 'src/app/services/mensuration.service';
import { NotificationService } from 'src/app/services/notification.service';
import { MessageUtils } from 'src/app/utils/message-utils';
import { OperatorUtils } from 'src/app/utils/operator-utils';

import { HiveFormComponent } from '../hive-form/hive-form.component';

@Component({
  selector: 'app-hives',
  templateUrl: './hives.component.html',
  styleUrls: ['./hives.component.sass']
})
export class HivesComponent implements OnInit {

  filterString!: string;
  hives!: Array<Hive>;
  isLoadingResults!: boolean;
  pageIndex!: number;
  pageSize!: number;
  resultsLength!: number;
  user!: any;

  @ViewChild(MatPaginator) paginator!: MatPaginator;

  constructor(
    private _authService: AuthService,
    private _dialog: MatDialog,
    private _hiveService: HiveService,
    private _mensurationService: MensurationService,
    private _notificationService: NotificationService
  ) { }

  ngOnInit(): void {
    this.isLoadingResults = false;
    this.pageIndex = 0;
    this.pageSize = 10;
    this.resultsLength = 0;
    this.user = this._authService.getUser();
    this.findAll();
  }

  add() {

    this._dialog.open(HiveFormComponent, {
      data: {
        hive: null
      },
      width: '100%'
    })
    .afterClosed().subscribe({

      next: (result) => {
          
        if (result && result.status) {
          this.findAll();
        }
      },
    });
  }

  async findAll() {

    const page: number = this.pageIndex;
    const size: number = this.pageSize;
    const sort: string = 'code';
    const direction: string = 'asc';

    this.isLoadingResults = true;
    await OperatorUtils.delay(1000);

    this._hiveService.findAll(page, size, sort, direction).subscribe({

      complete: () => {
        this.isLoadingResults = false;
        this.findMensurations();
      },

      next: (hives) => {
        this.hives = hives.content;
        this.resultsLength = hives.totalElements;
      },

      error: (error) => {
        this.isLoadingResults = false;
        console.error(error);
        this._notificationService.show(MessageUtils.HIVES_GET_FAIL, NotificationType.FAIL);
      }
    });
  }

  findMensurations() {

    this.hives.forEach(hive => {

      const page = 0;
      const size = 1;
      const sort = 'createdDate';
      const direction = 'desc';

      this._mensurationService.findAll(hive.id, page, size, sort, direction).subscribe({

        next: (mensurations) => {
          hive.mensurations = mensurations.content;
        },

        error: (error) => {
          this.isLoadingResults = false;
          console.error(error);
          this._notificationService.show(MessageUtils.MENSURATIONS_GET_FAIL, NotificationType.FAIL);
        }
      });
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
    const page: number = this.pageIndex;
    const size: number = this.pageSize;
    const sort: string = 'code';
    const direction: string = 'asc';

    this.isLoadingResults = true;
    await OperatorUtils.delay(1000);

    this._hiveService.search(value, page, size, sort, direction).subscribe({

      complete: () => {
        this.isLoadingResults = false;
      },

      next: (hives) => {
        this.hives = hives.content;
        this.resultsLength = hives.totalElements;
      },

      error: (error) => {
        this.isLoadingResults = false;
        console.error(error);
        this._notificationService.show(MessageUtils.HIVES_GET_FAIL, NotificationType.FAIL);
      }
    });
  }
}