import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { AuthGuard } from './guards/authentication.guard';
import { LoginComponent } from './pages/login/login.component';
import { NotFoundComponent } from './pages/not-found/not-found.component';

const routes: Routes = [
	{ 
		path: '', 
		component: LoginComponent 
	},
	{
		path: 'administration',
		loadChildren: () => import('./modules/administration/administration.module').then((m) => m.AdministrationModule),
		canActivate: [AuthGuard],
	},
	{
		path: 'support',
		loadChildren: () => import('./modules/support/support.module').then((m) => m.SupportModule),
		canActivate: [AuthGuard],
	},
	{
		path: '**',
		component: NotFoundComponent,
	},
];

@NgModule({
	imports: [RouterModule.forRoot(routes)],
	exports: [RouterModule],
})
export class AppRoutingModule { }