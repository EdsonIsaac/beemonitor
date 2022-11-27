import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { ColmeiaComponent } from './colmeia/colmeia.component';
import { MatButtonModule } from '@angular/material/button';
import { MatDatepickerModule } from '@angular/material/datepicker';
import { MatDialogModule } from '@angular/material/dialog';
import { MatDividerModule } from '@angular/material/divider';
import { MatMenuModule } from '@angular/material/menu';
import { MatPaginatorModule } from '@angular/material/paginator';
import { MatSortModule } from '@angular/material/sort';
import { MatTableModule } from '@angular/material/table';
import { OutrosModule } from '../outros/outros.module';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { DialogColmeiaFormComponent } from './dialog-colmeia-form/dialog-colmeia-form.component';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { DialogColmeiaDeleteComponent } from './dialog-colmeia-delete/dialog-colmeia-delete.component';
import { ColmeiaViewComponent } from './colmeia-view/colmeia-view.component';
import { MatCardModule } from '@angular/material/card';
import { MatIconModule } from '@angular/material/icon';
import { MatNativeDateModule, MAT_DATE_LOCALE } from '@angular/material/core';
import { ChartsModule } from 'ng2-charts';
import { RouterModule } from '@angular/router';

@NgModule({
  declarations: [
    ColmeiaComponent,
    ColmeiaViewComponent,
    DialogColmeiaFormComponent,
    DialogColmeiaDeleteComponent
  ],
  imports: [
    CommonModule,
    OutrosModule,
    RouterModule,
    ChartsModule,
    MatButtonModule,
    MatCardModule,
    MatDatepickerModule,
    MatDialogModule,
    MatDividerModule,
    MatFormFieldModule,
    MatIconModule,
    MatInputModule,
    MatMenuModule,
    MatNativeDateModule,
    MatPaginatorModule,
    MatSortModule,
    MatTableModule,
    FormsModule,
    ReactiveFormsModule
  ],
  providers: [{ provide: MAT_DATE_LOCALE, useValue: 'pt-BR' }]
})
export class ColmeiaModule { }