import { Component, Inject } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { NotificationType } from 'src/app/enums/notification-type';
import { FacadeService } from 'src/app/services/facade.service';
import { MessageUtils } from 'src/app/utils/message-utils';

@Component({
  selector: 'app-hives-delete',
  templateUrl: './hives-delete.component.html',
  styleUrls: ['./hives-delete.component.sass']
})
export class HivesDeleteComponent {

  constructor(
    @Inject(MAT_DIALOG_DATA) public data: any,
    private dialogRef: MatDialogRef<HivesDeleteComponent>,
    private facade: FacadeService
  ) { }

  submit() {

    this.facade.hiveDelete(this.data.hive).subscribe({

      complete: () => {
        this.facade.notificationShowNotification(MessageUtils.HIVE_DELETE_SUCCESS, NotificationType.SUCCESS);
        this.dialogRef.close({status: true});
      },

      error: (error) => {
        console.log(error);
        this.facade.notificationShowNotification(MessageUtils.HIVE_DELETE_FAIL, NotificationType.FAIL);
      }
    });
  }
}