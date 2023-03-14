import { CommonModule } from '@angular/common';
import { NgModule } from '@angular/core';

import { AdministrationModule } from './administration/administration.module';
import { SupportModule } from './support/support.module';

@NgModule({
  declarations: [],
  imports: [
    CommonModule,
    AdministrationModule,
    SupportModule
  ]
})
export class ModulesModule { }