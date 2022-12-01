import { Component, OnInit } from '@angular/core';
import { FacadeService } from 'src/app/services/facade.service';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.sass']
})
export class HomeComponent implements OnInit {
  
  currentUser: any;

  constructor(private facade: FacadeService) {

  }

  ngOnInit(): void {
    this.currentUser = this.facade.authGetCurrentUser();
  }
}