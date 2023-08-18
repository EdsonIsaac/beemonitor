import { Component, OnInit } from '@angular/core';
import { Authentication } from 'src/app/entities/authentication';
import { AuthenticationService } from 'src/app/services/authentication.service';
import { RedirectService } from 'src/app/services/redirect.service';

@Component({
  selector: 'app-panel',
  templateUrl: './panel.component.html',
  styleUrls: ['./panel.component.sass'],
})
export class PanelComponent implements OnInit {
  authentication!: Authentication | null;

  constructor(
    private _authenticationService: AuthenticationService,
    private _redirectService: RedirectService
  ) {}
  ngOnInit(): void {
    this.authentication = this._authenticationService.getAuthentication();
  }

  hasAuthority(authorities: Array<string>) {
    return this._authenticationService.hasAuthority(authorities);
  }

  redirectToHiveList() {
    this._redirectService.toHiveList();
  }

  redirectToUserList() {
    this._redirectService.toUserList();
  }
}
