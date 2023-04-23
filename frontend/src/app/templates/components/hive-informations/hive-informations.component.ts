import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { MatDialog } from '@angular/material/dialog';
import { Router } from '@angular/router';
import { Hive } from 'src/app/entities/hive';
import { NotificationType } from 'src/app/enums/notification-type';
import { AuthService } from 'src/app/services/auth.service';
import { HiveService } from 'src/app/services/hive.service';
import { NotificationService } from 'src/app/services/notification.service';
import { MessageUtils } from 'src/app/utils/message-utils';

import { HiveDeleteComponent } from '../hive-delete/hive-delete.component';

@Component({
  selector: 'app-hive-informations',
  templateUrl: './hive-informations.component.html',
  styleUrls: ['./hive-informations.component.sass']
})
export class HiveInformationsComponent implements OnInit {

  form!: FormGroup;
  hive!: Hive;
  user!: any;

  constructor(
    private _authService: AuthService,
    private _dialog: MatDialog,
    private _formBuilder: FormBuilder,
    private _hiveService: HiveService,
    private _notificationService: NotificationService,
    private _router: Router
  ) { }

  ngOnInit(): void {
    
    this.user = this._authService.getUser();

    this._hiveService.get().subscribe({

      next: (hive) => {
        
        if (hive) {
          this.hive = hive;
          this.buildForm(hive);
        }

        else {
          this.buildForm(null);
        }
      }
    });
  }

  buildForm(hive: Hive | null) {

    this.form = this._formBuilder.group({
      id: [hive?.id, Validators.nullValidator],
      code: [hive?.code, Validators.required],
      mensurations: [[], Validators.nullValidator]
    });

    hive ? this.form.disable() : this.form.enable();
  }

  cancel() {
    this.hive ? this.buildForm(this.hive) : this._router.navigate(['/' + this.user.role.toLowerCase() + '/hives']);
  }

  delete() {

    this._dialog.open(HiveDeleteComponent, {
      data: {
        hive: this.hive
      },
      width: '100%'
    })
    .afterClosed().subscribe({

      next: (result) => {

        if (result && result.status) {
          this._router.navigate(['/' + this.user.role.toLowerCase() + '/hives']);
        }
      },
    });
  }

  submit() {

    const hive: Hive = Object.assign({}, this.form.value);

    if (hive.id) {
      
      this._hiveService.update(hive).subscribe({

        next: (hive) => {
          this._hiveService.set(hive);
          this._notificationService.show(MessageUtils.HIVE_UPDATE_SUCCESS, NotificationType.SUCCESS);
        },
  
        error: (error) => {
          console.error(error);
          this._notificationService.show(MessageUtils.HIVE_UPDATE_FAIL + error.error[0].message, NotificationType.FAIL);   
        }
      });
    }

    else {

      this._hiveService.save(hive).subscribe({

        next: (hive) => {
          this._router.navigate(['/' + this.user.role.toLowerCase() + '/hives/' + hive.id]);
          this._notificationService.show(MessageUtils.HIVE_SAVE_SUCCESS, NotificationType.SUCCESS);
        },
  
        error: (error) => {
          console.error(error);
          this._notificationService.show(MessageUtils.HIVE_SAVE_FAIL + error.error[0].message, NotificationType.FAIL);   
        }
      });
    }
  }

  update() {
    this.form.enable();
  }
}