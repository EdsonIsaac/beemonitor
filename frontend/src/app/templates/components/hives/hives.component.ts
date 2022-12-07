import { Component, OnInit } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { Hive } from 'src/app/entities/hive';
import { NotificationType } from 'src/app/enums/notification-type';
import { FacadeService } from 'src/app/services/facade.service';
import { MessageUtils } from 'src/app/utils/message-utils';

import { HivesDeleteComponent } from '../hives-delete/hives-delete.component';
import { HivesFormComponent } from '../hives-form/hives-form.component';

@Component({
  selector: 'app-hives',
  templateUrl: './hives.component.html',
  styleUrls: ['./hives.component.sass']
})
export class HivesComponent implements OnInit {

  currentUser!: any;
  hives!: Array<Hive>;
  hivesToShow!: Array<Hive>;

  constructor(
    private dialog: MatDialog,
    private facade: FacadeService
  ) { }

  ngOnInit(): void {
    this.currentUser = this.facade.authGetCurrentUser();
    this.findAllHives();
  }

  addHive() {

    this.dialog.open(HivesFormComponent, {
      data: {
        hive: null
      },
      width: '100%'
    })
    .afterClosed().subscribe({

      next: (result) => {
          
        if (result && result.status) {
          this.findAllHives();
        }
      },
    });
  }

  deleteHive(hive: Hive) {

    this.dialog.open(HivesDeleteComponent, {
      data: {
        hive: hive
      },
      width: '100%'
    })
    .afterClosed().subscribe({

      next: (result) => {
          
        if (result && result.status) {
          this.findAllHives();
        }
      },
    });
  }

  filter(value: string) {
    this.hivesToShow = this.hives.filter(hive => hive.code.toUpperCase().includes(value.toUpperCase()));
  }

  findAllHives() {

    this.facade.hiveFindAll().subscribe({

      next: (hives) => {
        this.hives = hives.sort((a, b) => a.code.toUpperCase() > b.code.toUpperCase() ? 1 : -1);
        
        this.hives.forEach(hive => {
          this.facade.mensurationGetMensurations(hive.id, 1).subscribe({
            
            next: (mensurations) => {
              hive.mensurations = mensurations;    
            }
          })
        });

        this.hivesToShow = this.hives;
      },

      error: (error) => {
        console.error(error);
        this.facade.notificationShowNotification(MessageUtils.HIVES_GET_FAIL, NotificationType.FAIL);
      }
    });
  }

  updateHive(hive: Hive) {

    this.dialog.open(HivesFormComponent, {
      data: {
        hive: hive
      },
      width: '100%'
    })
    .afterClosed().subscribe({

      next: (result) => {
          
        if (result && result.status) {
          this.findAllHives();
        }
      },
    });
  }
}