import { Component, Inject } from '@angular/core';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material/dialog';
import { NotificationType } from 'src/app/enums/notification-type';
import { HiveService } from 'src/app/services/hive.service';
import { NotificationService } from 'src/app/services/notification.service';
import { MessageUtils } from 'src/app/utils/message-utils';

@Component({
	selector: 'app-hive-delete',
	templateUrl: './hive-delete.component.html',
	styleUrls: ['./hive-delete.component.sass']
})
export class HiveDeleteComponent {

	constructor(
		@Inject(MAT_DIALOG_DATA) public readonly data: any,
		private readonly _dialogRef: MatDialogRef<HiveDeleteComponent>,
		private readonly _hiveService: HiveService,
		private readonly _notificationService: NotificationService
	) { }

	submit() {

		this._hiveService.delete(this.data.hive).subscribe({

			complete: () => {
				this._notificationService.show(MessageUtils.HIVE_DELETE_SUCCESS, NotificationType.SUCCESS);
				this._dialogRef.close({ status: true });
			},

			error: (error) => {
				console.log(error);
				this._notificationService.show(MessageUtils.HIVE_DELETE_FAIL, NotificationType.FAIL);
			}
		});
	}
}