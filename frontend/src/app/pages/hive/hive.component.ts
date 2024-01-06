import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Hive } from 'src/app/models/hive';
import { NotificationType } from 'src/app/enums/notification-type';
import { HiveService } from 'src/app/services/hive.service';
import { NotificationService } from 'src/app/services/notification.service';
import { MessageUtils } from 'src/app/utils/message-utils';

@Component({
	selector: 'app-hive',
	templateUrl: './hive.component.html',
	styleUrls: ['./hive.component.sass'],
})
export class HiveComponent implements OnInit {

	hive!: Hive;

	constructor(
		private readonly _activatedRoute: ActivatedRoute,
		private readonly _hiveService: HiveService,
		private readonly _notificationService: NotificationService
	) { }

	ngOnInit(): void {

		const id = this._activatedRoute.snapshot.paramMap.get('id');

		if (id) {

			if (id.includes('register')) {
				this._hiveService.setHive(null);
			}

			else {

				this._hiveService.findById(id).subscribe({

					next: (hive) => {
						this._hiveService.setHive(hive);
					},

					error: (error) => {
						console.error(error);
						this._notificationService.show(
							MessageUtils.HIVE_GET_FAIL,
							NotificationType.FAIL
						);
					},
				});
			}
		}

		this._hiveService.getHive().subscribe({
			next: (hive) => {
				if (hive) {
					this.hive = hive;
				}
			},
		});
	}
}