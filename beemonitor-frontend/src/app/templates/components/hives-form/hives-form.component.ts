import { Component, Inject, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material/dialog';
import { Hive } from 'src/app/entities/hive';
import { NotificationType } from 'src/app/enums/notification-type';
import { FacadeService } from 'src/app/services/facade.service';
import { MessageUtils } from 'src/app/utils/message-utils';

@Component({
  selector: 'app-hives-form',
  templateUrl: './hives-form.component.html',
  styleUrls: ['./hives-form.component.sass']
})
export class HivesFormComponent implements OnInit {

  form!: FormGroup;

  constructor(
    @Inject(MAT_DIALOG_DATA) public data: any,
    private dialogRef: MatDialogRef<HivesFormComponent>,
    private facade: FacadeService, 
    private formBuilder: FormBuilder
  ) { }

  ngOnInit(): void {
    
    if (this.data.hive) {
      this.buildForm(this.data.hive);
    } else {
      this.buildForm(null);
    }

  }

  buildForm(hive: Hive | null) {

    this.form = this.formBuilder.group({
      id: [hive?.id, Validators.nullValidator],
      code: [hive?.code, Validators.required]
    });
  }

  submit() {
    
    let hive: Hive = Object.assign({}, this.form.value);

    this.facade.hiveSave(hive).subscribe({

      complete: () => {
        this.facade.notificationShowNotification(hive.id ? MessageUtils.HIVE_UPDATE_SUCCESS : MessageUtils.HIVE_SAVE_SUCCESS, NotificationType.SUCCESS);
        this.dialogRef.close({status: true});
      },

      error: (error) => {
        console.error(error);
        this.facade.notificationShowNotification(hive.id ? (MessageUtils.HIVE_UPDATE_FAIL + error.error[0].message) : (MessageUtils.HIVE_SAVE_FAIL + error.error[0].message), NotificationType.FAIL);   
      }
    })
  }
}
