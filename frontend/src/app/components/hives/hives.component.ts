import { Component, OnInit, ViewChild } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { MatPaginator } from '@angular/material/paginator';
import { Hive } from 'src/app/entities/hive';
import { NotificationType } from 'src/app/enums/notification-type';
import { HiveService } from 'src/app/services/hive.service';
import { MensurationService } from 'src/app/services/mensuration.service';
import { NotificationService } from 'src/app/services/notification.service';
import { RedirectService } from 'src/app/services/redirect.service';
import { MessageUtils } from 'src/app/utils/message-utils';
import { OperatorUtils } from 'src/app/utils/operator-utils';

import { HiveDeleteComponent } from '../hive-delete/hive-delete.component';

@Component({
  selector: 'app-hives',
  templateUrl: './hives.component.html',
  styleUrls: ['./hives.component.sass'],
})
export class HivesComponent implements OnInit {
  filterString!: string;
  hives!: Array<Hive>;
  isLoadingResults!: boolean;
  pageIndex!: number;
  pageSize!: number;
  resultsLength!: number;

  @ViewChild(MatPaginator) paginator!: MatPaginator;

  constructor(
    private _dialog: MatDialog,
    private _hiveService: HiveService,
    private _mensurationService: MensurationService,
    private _notificationService: NotificationService,
    private _redirectService: RedirectService
  ) {}

  ngOnInit(): void {
    this.isLoadingResults = false;
    this.pageIndex = 0;
    this.pageSize = 10;
    this.resultsLength = 0;
    this.findAll();
  }

  add() {
    this._redirectService.toHive('register');
  }

  delete(hive: Hive) {
    this._dialog
      .open(HiveDeleteComponent, {
        data: {
          hive: hive,
        },
        width: '100%',
      })
      .afterClosed()
      .subscribe({
        next: (result) => {
          if (result && result.status) {
            this.search();
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
      next: (hives) => {
        this.hives = hives.content;
        this.resultsLength = hives.totalElements;
        this.isLoadingResults = false;
        this.findMensurations();
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

  findMensurations() {
    this.hives.forEach((hive) => {
      const page = 0;
      const size = 1;
      const sort = 'createdDate';
      const direction = 'desc';

      this._mensurationService
        .search(hive.id, '', page, size, sort, direction)
        .subscribe({
          next: (mensurations) => {
            hive.mensurations = mensurations.content;
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
    const value: string = this.filterString ?? '';
    const page: number = this.pageIndex;
    const size: number = this.pageSize;
    const sort: string = 'code';
    const direction: string = 'asc';

    this.isLoadingResults = true;
    await OperatorUtils.delay(1000);

    this._hiveService.search(value, page, size, sort, direction).subscribe({
      next: (hives) => {
        this.hives = hives.content;
        this.resultsLength = hives.totalElements;
        this.isLoadingResults = false;
        this.findMensurations();
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

  update(hive: Hive) {
    this._redirectService.toHive(hive.id);
  }
}
