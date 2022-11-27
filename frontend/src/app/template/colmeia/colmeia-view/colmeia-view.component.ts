import { Component, OnDestroy, OnInit, ViewChild } from '@angular/core';
import { MatSort } from '@angular/material/sort';
import { MatTableDataSource } from '@angular/material/table';
import { ActivatedRoute } from '@angular/router';
import { ChartDataSets, ChartOptions, ChartType } from 'chart.js';
import { Color, Label } from 'ng2-charts';
import { Colmeia } from 'src/app/model/colmeia.model';
import { Medicao } from 'src/app/model/medicao.model';
import { ColmeiaService } from 'src/app/service/colmeia.service';
import { MatInput } from '@angular/material/input';
import { MatPaginator } from '@angular/material/paginator';

@Component({
  selector: 'app-colmeia-view',
  templateUrl: './colmeia-view.component.html',
  styleUrls: ['./colmeia-view.component.css']
})
export class ColmeiaViewComponent implements OnInit, OnDestroy {

  id!: any;
  searchDate!: any;
  viewGraph!: boolean;
  dataSource!: MatTableDataSource<Medicao>;
  colmeia!: Colmeia;
  colunas: string[] = ['indice', 'data-hora-cadastro', 'temperatura', 'umidade', 'peso'];
  now!: Date;

  lineChartDataTemperatura!: ChartDataSets[];
  lineChartLabelsTemperatura!: Label[];
  lineChartOptionsTemperatura: (ChartOptions & { annotation: any }) = {responsive: true, annotation: true, scales: {xAxes: [{ ticks: { stepSize: 1, min: 0,maxRotation: 90, minRotation: 90,autoSkip: false }}]}};
  lineChartColorsTemperatura: Color[] = [{ backgroundColor: 'rgba(255, 0, 0, 0.3)' }];
  lineChartLegendTemperatura = true;
  lineChartTypeTemperatura: ChartType = 'line';
  lineChartPluginsTemperatura = [];

  lineChartDataUmidade!: ChartDataSets[];
  lineChartLabelsUmidade!: Label[];
  lineChartOptionsUmidade: (ChartOptions & { annotation: any }) = {responsive: true, annotation: true, scales: {xAxes: [{ ticks: { stepSize: 1, min: 0,maxRotation: 90, minRotation: 90,autoSkip: false }}]}};
  lineChartColorsUmidade: Color[] = [{ backgroundColor: 'rgba(0, 0, 255, 0.3)' }];
  lineChartLegendUmidade = true;
  lineChartTypeUmidade: ChartType = 'line';
  lineChartPluginsUmidade = [];

  lineChartDataPeso!: ChartDataSets[];
  lineChartLabelsPeso!: Label[];
  lineChartOptionsPeso: (ChartOptions & { annotation: any }) = {responsive: true, annotation: true, scales: {xAxes: [{ ticks: { stepSize: 1, min: 0,maxRotation: 90, minRotation: 90,autoSkip: false }}]}};
  lineChartColorsPeso: Color[] = [{ backgroundColor: 'rgba(0, 0, 0, 0.3)' }];
  lineChartLegendPeso = true;
  lineChartTypePeso: ChartType = 'line';
  lineChartPluginsPeso = [];

  @ViewChild(MatSort) sort!: MatSort;
  @ViewChild(MatPaginator) paginator!: MatPaginator;
  @ViewChild('searchDate', { read: MatInput }) inputSearchDate!: MatInput;

  constructor(private colmeiaService: ColmeiaService, private activatedRoute: ActivatedRoute) { 
    this.now = new Date();
  }

  ngOnInit(): void {
    this.findData();
    this.id = setInterval(() => this.findData(), 30000);
  }
  
  ngOnDestroy(): void {
    if (this.id) clearInterval(this.id);
  }

  cleanFilter () {
    this.inputSearchDate.value = '';
    this.filterTable('');
  }

  buildGraph (filter: string) {
    let data = this.dataSource.data;

    data = data.filter(x => new Date(x.dataHoraCadastro).toLocaleDateString().includes(filter));
    
    if (data.length > 0 && filter !== '') {
      data.sort((a, b) => {return new Date(a.dataHoraCadastro).getTime() - new Date(b.dataHoraCadastro).getTime();});

      this.lineChartDataTemperatura = [{data: data.map(x => x.temperatura), label: 'Temperatura'}];
      this.lineChartDataUmidade = [{data: data.map(x => x.umidade), label: 'Umidade'}];
      this.lineChartDataPeso = [{data: data.map(x => x.peso), label: 'Peso'}];
      
      this.lineChartLabelsTemperatura = data.map(x => new Date(x.dataHoraCadastro).toLocaleTimeString());
      this.lineChartLabelsUmidade = data.map(x => new Date(x.dataHoraCadastro).toLocaleTimeString());
      this.lineChartLabelsPeso = data.map(x => new Date(x.dataHoraCadastro).toLocaleTimeString());
      
      this.viewGraph = true;
    } else {
      this.viewGraph = false;
    }
  }

  filterTable(value: any) {
    this.searchDate = value;
    this.dataSource.filter = value;
    this.buildGraph(value);
  }

  findData () {
    this.activatedRoute.params.subscribe(x => {

      if (x && x.id) {
        this.colmeiaService.findById(x.id).subscribe(response => {
          this.colmeia = response;
          this.dataSource = new MatTableDataSource(this.colmeia.medicoes);
    
          this.dataSource.sortingDataAccessor = (item: any, property: any) => {
            switch(property) {
              case 'data-hora-cadastro': return new Date(item.dataHoraCadastro);
              default: return item[property];
            }
          };

          this.dataSource.filterPredicate = (data: Medicao, filter: string) => new Date(data.dataHoraCadastro).toLocaleDateString().includes(filter);
          this.dataSource.paginator = this.paginator;
          this.dataSource.sort = this.sort;
          
          if (this.searchDate) {
            this.filterTable(this.searchDate);
          }
        });
      }
    });
  }
}