import { Component, OnInit } from '@angular/core';
import { AuthService } from 'src/app/services/auth.service';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.sass']
})
export class HomeComponent implements OnInit {
  
  user!: any;

  constructor(
    private _authService: AuthService
  ) { }

  ngOnInit(): void {
    this.user = this._authService.getUser();
  }
}