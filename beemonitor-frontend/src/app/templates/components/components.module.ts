import { CommonModule } from '@angular/common';
import { NgModule } from '@angular/core';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { MatButtonModule } from '@angular/material/button';
import { MatCardModule } from '@angular/material/card';
import { MatDialogModule } from '@angular/material/dialog';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatIconModule } from '@angular/material/icon';
import { MatInputModule } from '@angular/material/input';
import { MatMenuModule } from '@angular/material/menu';
import { MatPaginatorIntl, MatPaginatorModule } from '@angular/material/paginator';
import { MatSelectModule } from '@angular/material/select';
import { MatSnackBarModule } from '@angular/material/snack-bar';
import { MatSortModule } from '@angular/material/sort';
import { MatTableModule } from '@angular/material/table';
import { MatToolbarModule } from '@angular/material/toolbar';
import { RouterModule } from '@angular/router';
import { getDutchPaginatorIntl } from 'src/app/configurations/internacionalization';

import { FooterComponent } from './footer/footer.component';
import { HiveComponent } from './hive/hive.component';
import { HivesDeleteComponent } from './hives-delete/hives-delete.component';
import { HivesFormComponent } from './hives-form/hives-form.component';
import { HivesComponent } from './hives/hives.component';
import { HomeComponent } from './home/home.component';
import { LayoutComponent } from './layout/layout.component';
import { LoginComponent } from './login/login.component';
import { NotFoundComponent } from './not-found/not-found.component';
import { ToolbarComponent } from './toolbar/toolbar.component';
import { UsersFormComponent } from './users-form/users-form.component';
import { UsersComponent } from './users/users.component';

@NgModule({
  declarations: [
    FooterComponent,
    HiveComponent,
    HivesComponent,
    HivesDeleteComponent,
    HivesFormComponent,
    HomeComponent,
    LayoutComponent,
    LoginComponent,
    NotFoundComponent,
    ToolbarComponent,
    UsersComponent,
    UsersFormComponent,
  ],
  imports: [
    CommonModule,
    FormsModule,
    MatButtonModule,
    MatCardModule,
    MatDialogModule,
    MatFormFieldModule,
    MatIconModule,
    MatInputModule,
    MatMenuModule,
    MatPaginatorModule,
    MatSelectModule,
    MatSnackBarModule,
    MatSortModule,
    MatTableModule,
    MatToolbarModule,
    ReactiveFormsModule,
    RouterModule
  ],
  providers: [
    { provide: MatPaginatorIntl, useValue: getDutchPaginatorIntl() }
  ]
})
export class ComponentsModule { }