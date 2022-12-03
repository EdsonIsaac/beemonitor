import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { FacadeService } from 'src/app/services/facade.service';

@Component({
  selector: 'app-toolbar',
  templateUrl: './toolbar.component.html',
  styleUrls: ['./toolbar.component.sass']
})
export class ToolbarComponent {

  constructor(
    private facade: FacadeService,
    private router: Router
  ) { }

  logout() {
    this.facade.authLogout();
    this.router.navigate(['']);
  }
}