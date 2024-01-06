import { CommonModule } from '@angular/common';
import { NgModule } from '@angular/core';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { MatButtonModule } from '@angular/material/button';
import { MatCardModule } from '@angular/material/card';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatIconModule } from '@angular/material/icon';
import { MatInputModule } from '@angular/material/input';
import { MatPaginatorModule } from '@angular/material/paginator';
import { MatTabsModule } from '@angular/material/tabs';

import { ComponentsModule } from '../components/components.module';
import { HiveComponent } from './hive/hive.component';
import { HivesComponent } from './hives/hives.component';
import { LoginComponent } from './login/login.component';
import { NotFoundComponent } from './not-found/not-found.component';
import { PanelComponent } from './panel/panel.component';
import { UserComponent } from './user/user.component';
import { UsersComponent } from './users/users.component';

@NgModule({
	declarations: [
		HiveComponent,
		HivesComponent,
		LoginComponent,
		NotFoundComponent,
		PanelComponent,
		UserComponent,
		UsersComponent
	],
	imports: [
		CommonModule,
		ComponentsModule,
		FormsModule,
		MatButtonModule,
		MatCardModule,
		MatFormFieldModule,
		MatIconModule,
		MatInputModule,
		MatPaginatorModule,
		MatTabsModule,
		ReactiveFormsModule

	]
})
export class PagesModule { }