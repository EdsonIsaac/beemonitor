import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Hive } from 'src/app/models/hive';
import { NotificationType } from 'src/app/enums/notification-type';
import { HiveService } from 'src/app/services/hive.service';
import { NotificationService } from 'src/app/services/notification.service';
import { RedirectService } from 'src/app/services/redirect.service';
import { FormUtils } from 'src/app/utils/form-utils';
import { MessageUtils } from 'src/app/utils/message-utils';

@Component({
	selector: 'app-hive-informations',
	templateUrl: './hive-informations.component.html',
	styleUrls: ['./hive-informations.component.sass'],
})
export class HiveInformationsComponent implements OnInit {

	form!: FormGroup;
	hive!: Hive;

	constructor(
		private readonly _formBuilder: FormBuilder,
		private readonly _hiveService: HiveService,
		private readonly _notificationService: NotificationService,
		private readonly _redirectService: RedirectService
	) { }

	ngOnInit(): void {

		this._hiveService.getHive().subscribe({

			next: (hive) => {
				if (hive) {
					this.hive = hive;
					this.buildForm(hive);
				} else {
					this.buildForm(null);
				}
			},
		});
	}

	buildForm(hive: Hive | null) {

		this.form = this._formBuilder.group({
			id: [hive?.id, Validators.nullValidator],
			code: [hive?.code, Validators.required],
			mensurations: [[], Validators.nullValidator],
		});
	}

	back() {
		this._redirectService.toHiveList();
	}

	getErrorMessage(controlName: string) {
		return FormUtils.getErrorMessage(this.form, controlName);
	}

	hasError(controlName: string) {
		return FormUtils.hasError(this.form, controlName);
	}

	submit() {

		if (this.form.valid) {

			const hive: Hive = Object.assign({}, this.form.value);

			if (hive.id) {

				this._hiveService.update(hive).subscribe({
					next: (hive) => {
						this._hiveService.setHive(hive);
						this._notificationService.show(
							MessageUtils.HIVE_UPDATE_SUCCESS,
							NotificationType.SUCCESS
						);
					},

					error: (error) => {
						console.error(error);
						this._notificationService.show(
							MessageUtils.getMessage(error),
							NotificationType.FAIL
						);
					},
				});
			} else {

				this._hiveService.save(hive).subscribe({
					next: (hive) => {
						this._notificationService.show(
							MessageUtils.HIVE_SAVE_SUCCESS,
							NotificationType.SUCCESS
						);
						this._redirectService.toHive(hive.id);
					},

					error: (error) => {
						console.error(error);
						this._notificationService.show(
							MessageUtils.getMessage(error),
							NotificationType.FAIL
						);
					},
				});
			}
		}
	}
}