import { DatePipe } from '@angular/common';
import { AfterViewInit, Component, QueryList, ViewChild, ViewChildren } from '@angular/core';
import { MatPaginator } from '@angular/material/paginator';
import { MatSort } from '@angular/material/sort';
import { MatTableDataSource } from '@angular/material/table';
import { ChartConfiguration, ChartType } from 'chart.js';
import { BaseChartDirective } from 'ng2-charts';
import { Hive } from 'src/app/entities/hive';
import { Mensuration } from 'src/app/entities/mensuration';
import { NotificationType } from 'src/app/enums/notification-type';
import { HiveService } from 'src/app/services/hive.service';
import { MensurationService } from 'src/app/services/mensuration.service';
import { NotificationService } from 'src/app/services/notification.service';
import { MessageUtils } from 'src/app/utils/message-utils';
import { OperatorUtils } from 'src/app/utils/operator-utils';

@Component({
  selector: 'app-hive-mensurations',
  templateUrl: './hive-mensurations.component.html',
  styleUrls: ['./hive-mensurations.component.sass']
})
export class HiveMensurationsComponent implements AfterViewInit {

  columns!: Array<string>;
  dataSource!: MatTableDataSource<Mensuration>;
  filterDate!: Date | null;
  hive!: Hive;
  isLoadingResults!: boolean;
  resultsLength!: number;

  lineChartDataTemperature: ChartConfiguration['data'] = {
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

  lineChartDataHumidity: ChartConfiguration['data'] = {
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

  lineChartDataWeight: ChartConfiguration['data'] = {
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

  lineChartOptions: ChartConfiguration['options'] = {
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

  lineChartType: ChartType = 'line';

  @ViewChildren(BaseChartDirective) charts!: QueryList<BaseChartDirective>;
  @ViewChild(MatPaginator) paginator!: MatPaginator;
  @ViewChild(MatSort) sort!: MatSort;

  constructor(
    private _datePipe: DatePipe,
    private _hiveService: HiveService,
    private _mensurationService: MensurationService,
    private _notificationService: NotificationService
  ) {
    this.columns = ['index', 'createdDate', 'temperature', 'humidity', 'weight'];
    this.dataSource = new MatTableDataSource();
    this.isLoadingResults = false;
    this.resultsLength = 0;
  }

  ngAfterViewInit(): void {

    this._hiveService.get().subscribe({

      next: (hive) => {

        if (hive) {
          this.hive = hive;
        }
      }
    });
  }

  buildGraph(mensurations: Array<Mensuration>) {
    
    if (mensurations.length > 0) {
    
      mensurations = mensurations.sort((a, b) => new Date(a.createdDate).getTime() - new Date(b.createdDate).getTime());

      this.lineChartDataTemperature.datasets.forEach((x) => x.data = mensurations.map(mensuration => mensuration.temperature));
      this.lineChartDataTemperature.labels = mensurations.map(mensuration => new Date(mensuration.createdDate).toLocaleString());

      this.lineChartDataHumidity.datasets.forEach((x) => x.data = mensurations.map(mensuration => mensuration.humidity));
      this.lineChartDataHumidity.labels = mensurations.map(mensuration => new Date(mensuration.createdDate).toLocaleString());

      this.lineChartDataWeight.datasets.forEach((x) => x.data = mensurations.map(mensuration => mensuration.weight));
      this.lineChartDataWeight.labels = mensurations.map(mensuration => new Date(mensuration.createdDate).toLocaleString());

      this.charts.forEach(chart => {
        chart.chart?.update();
      });
    }
  }

  pageChange() {
    this.search();
  }

  async search() {

    const value: any = this._datePipe.transform(this.filterDate, 'yyyy-MM-dd');
    const page: number = this.paginator.pageIndex;
    const size: number = this.paginator.pageSize;
    const sort: string = this.sort.active;
    const direction: string = this.sort.direction;

    this.isLoadingResults = true;
    await OperatorUtils.delay(1000);

    this._mensurationService.search(this.hive.id, value, page, size, sort, direction).subscribe({

      complete: () => {
        this.isLoadingResults = false;
      },

      next: (mensurations) => {
        this.dataSource.data = mensurations.content;
        this.resultsLength = mensurations.totalElements;
        this.buildGraph(mensurations.content);
      },

      error: (error) => {
        this.isLoadingResults = false;
        console.error(error);
        this._notificationService.show(MessageUtils.MENSURATIONS_GET_FAIL, NotificationType.FAIL); 
      }
    });
  }

  sortChange() {
    this.paginator.pageIndex = 0;
    this.search()
  }
}