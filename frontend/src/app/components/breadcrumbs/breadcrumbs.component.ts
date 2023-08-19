import { Component } from '@angular/core';
import { Authentication } from 'src/app/entities/authentication';
import { AuthenticationService } from 'src/app/services/authentication.service';
import { HiveService } from 'src/app/services/hive.service';
import { UserService } from 'src/app/services/user.service';

interface Route {
  label: string;
  route: string | null;
  active: boolean;
}

@Component({
  selector: 'app-breadcrumbs',
  templateUrl: './breadcrumbs.component.html',
  styleUrls: ['./breadcrumbs.component.sass'],
})
export class BreadcrumbsComponent {
  isBuilding!: boolean;
  routes!: Array<Route>;

  private _authentication!: Authentication | null;

  constructor(
    private _authenticationService: AuthenticationService,
    private _hiveService: HiveService,
    private _userService: UserService
  ) {}

  ngOnInit(): void {
    this._authentication = this._authenticationService.getAuthentication();
    this.isBuilding = true;
    this.routes = this.getRoutes();
  }

  private checkObjectChange() {
    !this.isBuilding ? this.ngOnInit() : null;
  }

  getObjectName() {
    let name = '';
    const path = window.location.pathname;

    if (path.includes('hives')) {
      this._hiveService.get().subscribe({
        next: (hive) => {
          name = hive ? hive.code.toUpperCase() : 'Cadastro';
          this.checkObjectChange();
        }
      });
    }
    
    else if (path.includes('users')) {
      this._userService.get().subscribe({
        next: (user) => {
          name = user ? user.name.toUpperCase() : 'Cadastro';
          this.checkObjectChange();
        }
      });
    }

    else {
      name = 'Cadastro';
    }

    return name;
  }

  getRoutes() {
    const routes: Array<Route> = [];
    const path = window.location.pathname
      .split('/')
      .filter((x) => x !== '' && x !== 'panel');

    this.isBuilding = true;

    path.forEach((route, index) => {
      switch (route) {
        case 'hives':
          routes.push({
            label: 'Colmeias',
            route: `/${this._authentication?.role.toLowerCase()}/hives`,
            active: index === path.length - 1,
          });
          break;
        case 'users':
          routes.push({
            label: 'Usuários',
            route: `/${this._authentication?.role.toLowerCase()}/users`,
            active: index === path.length - 1,
          });
          break;
        case this._authentication?.role.toLowerCase():
          routes.push({
            label: 'Início',
            route: `/${this._authentication?.role.toLowerCase()}/panel`,
            active: index === path.length - 1,
          });
          break;
        default:
          routes.push({
            label: this.getObjectName(),
            route: null,
            active: index === path.length - 1,
          });
      }
    });

    this.isBuilding = false;
    return routes;
  }
}
