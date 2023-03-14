import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { AuthGuard } from 'src/app/guards/auth.guard';

import { HiveComponent } from '../../components/hive/hive.component';
import { HivesComponent } from '../../components/hives/hives.component';
import { HomeComponent } from '../../components/home/home.component';
import { LayoutComponent } from '../../components/layout/layout.component';
import { UserComponent } from '../../components/user/user.component';
import { UsersComponent } from '../../components/users/users.component';

const routes: Routes = [
  { path: '', component: LayoutComponent, children: [
    { path: '', component: HomeComponent, canActivate: [AuthGuard] },
    { path: 'hives', component: HivesComponent, canActivate: [AuthGuard] },
    { path: 'hives/:id', component: HiveComponent, canActivate: [AuthGuard] },
    { path: 'users', component: UsersComponent, canActivate: [AuthGuard] },
    { path: 'users/:id', component: UserComponent, canActivate: [AuthGuard] }
  ]}
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class SupportRoutingModule { }