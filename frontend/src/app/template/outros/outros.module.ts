import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { SidebarComponent } from './sidebar/sidebar.component';
import { NavbarComponent } from './navbar/navbar.component';
import { LayoutComponent } from './layout/layout.component';
import { RouterModule } from '@angular/router';

import { MatButtonModule } from '@angular/material/button';
import { MatInputModule } from '@angular/material/input';
import { NotFoundComponent } from './not-found/not-found.component';

@NgModule({
  declarations: [
    SidebarComponent,
    NavbarComponent,
    LayoutComponent,
    NotFoundComponent
  ],
  imports: [
    CommonModule,
    RouterModule,
    MatButtonModule,
    MatInputModule
  ],
  exports: [
    LayoutComponent
  ]
})
export class OutrosModule { }