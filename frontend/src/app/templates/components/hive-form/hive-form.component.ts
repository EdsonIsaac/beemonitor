import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { MatDialogRef } from '@angular/material/dialog';
import { Hive } from 'src/app/entities/hive';
import { NotificationType } from 'src/app/enums/notification-type';
import { HiveService } from 'src/app/services/hive.service';
import { NotificationService } from 'src/app/services/notification.service';
import { MessageUtils } from 'src/app/utils/message-utils';

@Component({
  selector: 'app-hive-form',
  templateUrl: './hive-form.component.html',
  styleUrls: ['./hive-form.component.sass']
})
export class HiveFormComponent implements OnInit {

  form!: FormGroup;

  constructor(
    private _dialogRef: MatDialogRef<HiveFormComponent>,
    private _formBuilder: FormBuilder,
    private _hiveService: HiveService,
    private _notificationService: NotificationService
  ) { }

  ngOnInit(): void {
    this.buildForm();
  }

  buildForm() {

    this.form = this._formBuilder.group({
      id: [null, Validators.nullValidator],
      code: [null, Validators.required]
    });
  }

  submit() {
    
    let hive: Hive = Object.assign({}, this.form.value);

    if (hive.id) {

      this._hiveService.update(hive).subscribe({

        complete: () => {
          this._notificationService.show(MessageUtils.HIVE_UPDATE_SUCCESS, NotificationType.SUCCESS);
          this._dialogRef.close({status: true});
        },
  
        error: (error) => {
          console.error(error);
          this._notificationService.show(MessageUtils.HIVE_UPDATE_FAIL + error.error[0].message, NotificationType.FAIL);   
        }
      });
    }

    else {
      this._hiveService.save(hive).subscribe({

        complete: () => {
          this._notificationService.show(MessageUtils.HIVE_SAVE_SUCCESS, NotificationType.SUCCESS);
          this._dialogRef.close({status: true});
        },
  
        error: (error) => {
          console.error(error);
          this._notificationService.show(MessageUtils.HIVE_SAVE_FAIL + error.error[0].message, NotificationType.FAIL);   
        }
      });
    }    
  }
}