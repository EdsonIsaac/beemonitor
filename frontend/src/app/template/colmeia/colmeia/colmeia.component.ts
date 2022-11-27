import { Component, OnDestroy, OnInit, ViewChild } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { MatSnackBar } from '@angular/material/snack-bar';
import { MatTableDataSource } from '@angular/material/table';
import { Colmeia } from 'src/app/model/colmeia.model';
import { ColmeiaService } from 'src/app/service/colmeia.service';
import { DialogColmeiaFormComponent } from '../dialog-colmeia-form/dialog-colmeia-form.component';
import { DialogColmeiaDeleteComponent } from '../dialog-colmeia-delete/dialog-colmeia-delete.component';
import { Router } from '@angular/router';

@Component({
  selector: 'app-colmeia',
  templateUrl: './colmeia.component.html',
  styleUrls: ['./colmeia.component.css']
})
export class ColmeiaComponent implements OnInit, OnDestroy {

  id!: any;
  colmeiasAll!: Array<Colmeia>;
  colmeias!: Array<Colmeia>;
  dataSource!: MatTableDataSource<Colmeia>;
  colunas: string[] = ['indice', 'codigo', 'telefone', 'data-cadastro', 'acao'];
  searchText!: string;

  constructor(private colmeiaService: ColmeiaService, private snackBar: MatSnackBar, private dialog: MatDialog, private router: Router) { }

  ngOnInit(): void {
    this.findData();
    this.id = setInterval(() => this.findData(), 30000);
  }

  ngOnDestroy(): void {
    if (this.id) clearInterval(this.id);
  }

  addColmeia() {
    this.dialog.open(DialogColmeiaFormComponent, {
      data: {
        colmeia: null
      },
      width: '100%'
    })
    .afterClosed().subscribe(result => {
      
      if (result.status != undefined && result.status) {
        this.showSnackBar('Colmeia cadastrada com sucesso!', 'bg-success');
        this.ngOnInit();
      }
      
      if (result.status != undefined && !result.status) {
        this.showSnackBar('Não foi possível salvar a colmeia! ' + result.message, 'bg-danger');
      }
    });
  }

  buildCards(colmeias: Array<Colmeia>) {
    this.colmeias = colmeias;
    this.colmeias.sort((one, two) => (one.codigo > two.codigo ? 1 : -1));
    this.colmeias.forEach(colmeia => colmeia.medicoes.sort((a, b) => (new Date(a.dataHoraCadastro).getTime() - new Date(b.dataHoraCadastro).getTime()) * -1));
  }

  deleteColmeia(colmeia: Colmeia) {
    this.dialog.open(DialogColmeiaDeleteComponent, {
      data: {
        colmeia: colmeia
      }
    })
    .afterClosed().subscribe(result => {
      
      if (result.status != undefined && result.status) {
        this.showSnackBar('Colmeia excluida com sucesso!', 'bg-success');
        this.ngOnInit();
      }
      
      if (result.status != undefined && !result.status) {
        this.showSnackBar('Não foi possível excluir a colmeia! ' + result.message, 'bg-danger');
      }
    });
  }

  filter(value: string) {
    this.buildCards(this.colmeiasAll.filter(colmeia => colmeia.codigo.includes(value)));
  }

  findData() {
    this.colmeiaService.findAllWithOneMedicao().subscribe(colmeias => {
      this.colmeiasAll = colmeias;
      this.buildCards(this.colmeiasAll);
    }, 
    error => this.showSnackBar('Erro! Não foi possível listar as colmeias!', 'bg-danger'));
  }

  showColmeia(colmeia: Colmeia) {
    this.router.navigate(['/colmeias/' + colmeia.id]);
  }

  showSnackBar(mensagem: string, cor: string) {
    this.snackBar.open(mensagem, 'X', {
      horizontalPosition: 'center',
      verticalPosition: 'top',
      panelClass: [cor]
    });
  }

  updateColmeia(colmeia: Colmeia) {
    this.dialog.open(DialogColmeiaFormComponent, {
      data: {
        colmeia: colmeia
      },
      width: '100%'
    })
    .afterClosed().subscribe(result => {
      
      if (result.status != undefined && result.status) {
        this.showSnackBar('Colmeia atualizada com sucesso!', 'bg-success');
        this.ngOnInit();
      }
      
      if (result.status != undefined && !result.status) {
        this.showSnackBar('Não foi possível atualizar a colmeia! ' + result.message, 'bg-danger');
      }
    });
  }
}