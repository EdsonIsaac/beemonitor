import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { LayoutComponent } from 'src/app/components/layout/layout.component';
import { HiveComponent } from 'src/app/pages/hive/hive.component';
import { HivesComponent } from 'src/app/pages/hives/hives.component';
import { PanelComponent } from 'src/app/pages/panel/panel.component';
import { UserComponent } from 'src/app/pages/user/user.component';
import { UsersComponent } from 'src/app/pages/users/users.component';

const routes: Routes = [
	{
		path: '',
		component: LayoutComponent,
		children: [
			{ path: 'panel', component: PanelComponent },
			{ path: 'hives', component: HivesComponent },
			{ path: 'hives/:id', component: HiveComponent },
			{ path: 'users', component: UsersComponent },
			{ path: 'users/:id', component: UserComponent },
		],
	},
];

@NgModule({
	imports: [RouterModule.forChild(routes)],
	exports: [RouterModule],
})
export class SupportRoutingModule { }