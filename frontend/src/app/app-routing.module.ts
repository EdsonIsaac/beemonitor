import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { AuthGuard } from './guard/auth.guard';
import { ColmeiaViewComponent } from './template/colmeia/colmeia-view/colmeia-view.component';
import { ColmeiaComponent } from './template/colmeia/colmeia/colmeia.component';
import { LoginComponent } from './template/geral/login/login.component';
import { LayoutComponent } from './template/outros/layout/layout.component';
import { NotFoundComponent } from './template/outros/not-found/not-found.component';

const routes: Routes = [
  {path: '', component: LoginComponent},
  {path: '', component: LayoutComponent, children: [
    {path: 'colmeias', component: ColmeiaComponent, canActivate: [AuthGuard]},
    {path: 'colmeias/:id', component: ColmeiaViewComponent, canActivate: [AuthGuard]},
    {path: '**', component: NotFoundComponent}
  ]}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }