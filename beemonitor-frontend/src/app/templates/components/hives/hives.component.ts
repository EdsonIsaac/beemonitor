import { Component, OnInit } from '@angular/core';
import { Hive } from 'src/app/entities/hive';
import { FacadeService } from 'src/app/services/facade.service';

@Component({
  selector: 'app-hives',
  templateUrl: './hives.component.html',
  styleUrls: ['./hives.component.sass']
})
export class HivesComponent implements OnInit {

  currentUser!: any;
  hives!: Array<Hive>;

  constructor(
    private facade: FacadeService
  ) { }

  ngOnInit(): void {
    this.currentUser = this.facade.authGetCurrentUser();

    this.facade.hiveFindAll().subscribe({

      next: (hives) => {
        this.hives = hives;
        
        this.hives.forEach(hive => {
          this.facade.mensurationGetMensurations(hive.id, 1).subscribe({
            
            next: (mensurations) => {
              hive.mensurations = mensurations;    
            }
          })
        });
      },

      error: (error) => {
        console.error(error);

      },
    });
  }

  addHive() {

  }

  deleteHive(hive: Hive) {

  }

  filter(value: string) {

  }

  updateHive(hive: Hive) {

  }
}