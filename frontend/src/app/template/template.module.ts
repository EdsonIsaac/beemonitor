import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { OutrosModule } from './outros/outros.module';
import { GeralModule } from './geral/geral.module';
import { ColmeiaModule } from './colmeia/colmeia.module';
import { MatPaginatorIntl } from '@angular/material/paginator';
import { getDutchPaginatorIntl } from '../suport/Internacionalizacao-paginator';

@NgModule({
  declarations: [],
  imports: [
    CommonModule,
    ColmeiaModule,
    GeralModule,
    OutrosModule
  ],
  exports: [
    ColmeiaModule,
    GeralModule,
    OutrosModule
  ],
  providers: [
    {provide: MatPaginatorIntl, useValue: getDutchPaginatorIntl() }
  ]
})
export class TemplateModule { }