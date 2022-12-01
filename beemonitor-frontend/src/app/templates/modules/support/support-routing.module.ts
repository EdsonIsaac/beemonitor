import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { AuthGuard } from 'src/app/guards/auth.guard';

import { HivesComponent } from '../../components/hives/hives.component';
import { HomeComponent } from '../../components/home/home.component';
import { LayoutComponent } from '../../components/layout/layout.component';
import { UsersComponent } from '../../components/users/users.component';

const routes: Routes = [
  { path: '', component: LayoutComponent, children: [
    { path: '', component: HomeComponent, canActivate: [AuthGuard], data: { breadcrumb: 'Início' }  },
    { path: 'hives', component: HivesComponent, canActivate: [AuthGuard], data: { breadcrumb: 'Colmeias' } },
    { path: 'users', component: UsersComponent, canActivate: [AuthGuard], data: { breadcrumb: 'Usuários' } }
  ]}
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class SupportRoutingModule { }
