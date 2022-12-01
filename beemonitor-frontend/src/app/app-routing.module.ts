import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { LoginComponent } from './templates/components/login/login.component';

const routes: Routes = [
  { path: '', component: LoginComponent },
  { path: 'administration', loadChildren: () => import('./templates/modules/administration/administration.module').then(m => m.AdministrationModule) },
  { path: 'support', loadChildren: () => import('./templates/modules/support/support.module').then(m => m.SupportModule) }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }