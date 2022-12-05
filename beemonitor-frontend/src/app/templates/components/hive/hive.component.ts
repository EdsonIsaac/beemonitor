import { Component, OnInit, ViewChild } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { MatDialog } from '@angular/material/dialog';
import { MatPaginator } from '@angular/material/paginator';
import { MatSort } from '@angular/material/sort';
import { MatTableDataSource } from '@angular/material/table';
import { ActivatedRoute, Router } from '@angular/router';
import { ChartConfiguration, ChartOptions, ChartType } from 'chart.js';
import { Hive } from 'src/app/entities/hive';
import { Mensuration } from 'src/app/entities/mensuration';
import { NotificationType } from 'src/app/enums/notification-type';
import { FacadeService } from 'src/app/services/facade.service';
import { MessageUtils } from 'src/app/utils/message-utils';

import { HivesDeleteComponent } from '../hives-delete/hives-delete.component';

@Component({
  selector: 'app-hive',
  templateUrl: './hive.component.html',
  styleUrls: ['./hive.component.sass']
})
export class HiveComponent implements OnInit {

  columns!: Array<string>;
  currentUser!: any;
  dataSource!: MatTableDataSource<Mensuration>;
  form!: FormGroup;
  hive!: Hive;
  showGraphs!: boolean;
  showTable!: boolean;

  public lineChartDataTemperature: ChartConfiguration['data'] = {
    datasets: [
      {
        data: [],
        label: 'Temperatura',
        backgroundColor: 'rgba(255, 0, 0, 0.3)',
        borderColor: 'rgba(255, 0, 0, 1)',
        pointBackgroundColor: 'rgba(255, 0, 0, 1)',
        pointBorderColor: '#fff',
        pointHoverBackgroundColor: '#fff',
        pointHoverBorderColor: 'rgba(255, 0, 0, 0.8)',
        fill: 'origin'
      }
    ]
  }

  public lineChartDataHumidity: ChartConfiguration['data'] = {
    datasets: [
      {
        data: [],
        label: 'Umidade',
        backgroundColor: 'rgba(0, 0, 255, 0.3)',
        borderColor: 'rgba(0, 0, 255, 1)',
        pointBackgroundColor: 'rgba(0, 0, 255, 1)',
        pointBorderColor: '#fff',
        pointHoverBackgroundColor: '#fff',
        pointHoverBorderColor: 'rgba(0, 0, 255, 0.8)',
        fill: 'origin'
      }
    ]
  }

  public lineChartDataWeight: ChartConfiguration['data'] = {
    datasets: [
      {
        data: [],
        label: 'Peso',
        backgroundColor: 'rgba(0, 0, 0, 0.3)',
        borderColor: 'rgba(0, 0, 0, 1)',
        pointBackgroundColor: 'rgba(0, 0, 0, 1)',
        pointBorderColor: '#fff',
        pointHoverBackgroundColor: '#fff',
        pointHoverBorderColor: 'rgba(0, 0, 0, 0.8)',
        fill: 'origin'
      }
    ]
  }

  public lineChartOptions: ChartConfiguration['options'] = {
    elements: {
      line: {
        tension: 0.5
      }
    },
    scales: {
      y: {
        position: 'left',
      }
    }
  }

  public lineChartType: ChartType = 'line';

  @ViewChild(MatPaginator, { static: false }) set paginator(value: MatPaginator) { if (this.dataSource) this.dataSource.paginator = value }
  @ViewChild(MatSort, { static: false }) set sort(value: MatSort) { if (this.dataSource) this.dataSource.sort = value }

  constructor(
    private activatedRoute: ActivatedRoute,
    private dialog: MatDialog,
    private facade: FacadeService,
    private formBuilder: FormBuilder,
    private router: Router
  ) { }

  ngOnInit(): void {
    this.columns = ['index', 'created-date', 'temperature', 'humidity', 'weight'];
    this.dataSource = new MatTableDataSource();
    this.currentUser = this.facade.authGetCurrentUser();
    this.showGraphs = false;
    this.showTable = false;

    this.activatedRoute.params.subscribe((x: any) => {

      if (x && x.id) {

        this.facade.hiveFindById(x.id).subscribe({

          next: (hive) => {
            this.hive = hive;
            this.hive.mensurations = this.hive.mensurations.sort((a, b) => b.createdDate > a.createdDate ? 1 : -1);
            this.buildForm(this.hive);
          },

          error: (error) => {
            console.error(error);
            this.facade.notificationShowNotification(MessageUtils.HIVE_GET_FAIL, NotificationType.FAIL);
          }
        })
      }
    });
  }

  buildForm(hive: Hive) {

    this.form = this.formBuilder.group({
      id: [hive.id, Validators.nullValidator],
      code: [hive.code, Validators.required]
    });

    this.form.disable();
  }

  buildGraph(mensurations: Array<Mensuration>) {

    if (mensurations.length > 0) {
      this.lineChartDataTemperature.datasets.forEach((x) => x.data = mensurations.map(mensuration => mensuration.temperature));
      this.lineChartDataTemperature.labels = mensurations.map(mensuration => new Date(mensuration.createdDate).toLocaleString());

      this.lineChartDataHumidity.datasets.forEach((x) => x.data = mensurations.map(mensuration => mensuration.humidity));
      this.lineChartDataHumidity.labels = mensurations.map(mensuration => new Date(mensuration.createdDate).toLocaleString());

      this.lineChartDataWeight.datasets.forEach((x) => x.data = mensurations.map(mensuration => mensuration.weight));
      this.lineChartDataWeight.labels = mensurations.map(mensuration => new Date(mensuration.createdDate).toLocaleString());

      this.showGraphs = true;
    } else {
      this.showGraphs = false;
    }

  }

  buildTable(mensurations: Array<Mensuration>) {

    this.dataSource.data = mensurations;

    this.dataSource.sortingDataAccessor = (item: any, property: any) => {
      switch (property) {
        case 'created-date': return new Date(item.createdDate);
        default: return item[property];
      }
    };

    this.showTable = true;
  }

  deleteHive(hive: Hive) {

    this.dialog.open(HivesDeleteComponent, {
      data: {
        hive: hive
      },
      width: '100%'
    })
    .afterClosed().subscribe({

      next: (result) => {

        if (result && result.status) {
          this.router.navigate(['/' + this.currentUser.role.toLowerCase() + '/hives']);
        }
      },
    });
  }

  filterTable(value: string) {
    let mensurations = this.hive.mensurations.filter(mensuration => new Date(mensuration.createdDate).toLocaleDateString().includes(value));
    this.buildTable(mensurations)
    this.buildGraph(mensurations);
  }

  submit() {

    let hive: Hive = Object.assign({}, this.form.value);

    this.facade.hiveSave(hive).subscribe({

      complete: () => {
        
        this.facade.hiveFindById(this.hive.id).subscribe({

          next: (hive) => {
            this.hive = hive;
            this.hive.mensurations = this.hive.mensurations.sort((a, b) => b.createdDate > a.createdDate ? 1 : -1);
            this.buildForm(this.hive);
            this.facade.notificationShowNotification(MessageUtils.HIVE_UPDATE_SUCCESS, NotificationType.SUCCESS);
          },

          error: (error) => {
            console.error(error);
            this.facade.notificationShowNotification(MessageUtils.HIVE_GET_FAIL, NotificationType.FAIL);   
          }
        })
      },

      error: (error) => {
        console.error(error);
        this.facade.notificationShowNotification(MessageUtils.HIVE_UPDATE_FAIL + error.error[0].message, NotificationType.FAIL);   
      }
    });
  }

  updateHive() {
    this.form.enable();
  }
}