import { CommonModule, DatePipe } from '@angular/common';
import { NgModule } from '@angular/core';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { MatButtonModule } from '@angular/material/button';
import { MatCardModule } from '@angular/material/card';
import { MAT_DATE_LOCALE, MatNativeDateModule } from '@angular/material/core';
import { MatDatepickerModule } from '@angular/material/datepicker';
import { MatDialogModule } from '@angular/material/dialog';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatIconModule } from '@angular/material/icon';
import { MatInputModule } from '@angular/material/input';
import { MatMenuModule } from '@angular/material/menu';
import { MatPaginatorIntl, MatPaginatorModule } from '@angular/material/paginator';
import { MatProgressBarModule } from '@angular/material/progress-bar';
import { MatSelectModule } from '@angular/material/select';
import { MatSnackBarModule } from '@angular/material/snack-bar';
import { MatSortModule } from '@angular/material/sort';
import { MatTableModule } from '@angular/material/table';
import { MatToolbarModule } from '@angular/material/toolbar';
import { RouterModule } from '@angular/router';
import { NgChartsModule } from 'ng2-charts';
import { getDutchPaginatorIntl } from 'src/app/configurations/internacionalization';

import { BreadcrumbsComponent } from './breadcrumbs/breadcrumbs.component';
import { FooterComponent } from './footer/footer.component';
import { HiveDeleteComponent } from './hive-delete/hive-delete.component';
import { HiveInformationsComponent } from './hive-informations/hive-informations.component';
import { HiveMensurationsComponent } from './hive-mensurations/hive-mensurations.component';
import { LayoutComponent } from './layout/layout.component';
import { LoadingComponent } from './loading/loading.component';
import { NoContentComponent } from './no-content/no-content.component';
import { NotificationComponent } from './notification/notification.component';
import { SelectImageComponent } from './select-image/select-image.component';
import { ToolbarComponent } from './toolbar/toolbar.component';
import { UserInformationsComponent } from './user-informations/user-informations.component';

@NgModule({
	declarations: [
		BreadcrumbsComponent,
		FooterComponent,
		HiveDeleteComponent,
		HiveInformationsComponent,
		HiveMensurationsComponent,
		LayoutComponent,
		LoadingComponent,
		NoContentComponent,
		NotificationComponent,
		SelectImageComponent,
		ToolbarComponent,
		UserInformationsComponent,
	],
	imports: [
		CommonModule,
		FormsModule,
		MatButtonModule,
		MatCardModule,
		MatDatepickerModule,
		MatDialogModule,
		MatFormFieldModule,
		MatIconModule,
		MatInputModule,
		MatMenuModule,
		MatNativeDateModule,
		MatPaginatorModule,
		MatProgressBarModule,
		MatSelectModule,
		MatSnackBarModule,
		MatSortModule,
		MatTableModule,
		MatToolbarModule,
		NgChartsModule.forRoot(),
		ReactiveFormsModule,
		RouterModule,
	],
	exports: [
		BreadcrumbsComponent,
		FooterComponent,
		HiveInformationsComponent,
		HiveMensurationsComponent,
		LoadingComponent,
		NoContentComponent,
		ToolbarComponent,
		UserInformationsComponent
	],
	providers: [
		{ provide: DatePipe },
		{ provide: MatPaginatorIntl, useValue: getDutchPaginatorIntl() },
		{ provide: MAT_DATE_LOCALE, useValue: 'pt-BR' },
	],
})
export class ComponentsModule { }
