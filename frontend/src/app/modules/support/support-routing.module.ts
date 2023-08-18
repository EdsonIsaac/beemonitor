import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { HiveComponent } from 'src/app/components/hive/hive.component';
import { HivesComponent } from 'src/app/components/hives/hives.component';
import { LayoutComponent } from 'src/app/components/layout/layout.component';
import { PanelComponent } from 'src/app/components/panel/panel.component';
import { UserComponent } from 'src/app/components/user/user.component';
import { UsersComponent } from 'src/app/components/users/users.component';

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
export class SupportRoutingModule {}
