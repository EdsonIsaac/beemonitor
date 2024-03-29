import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { MatDialog } from '@angular/material/dialog';
import { Authentication } from 'src/app/models/authentication';
import { User } from 'src/app/models/user';
import { NotificationType } from 'src/app/enums/notification-type';
import { AuthenticationService } from 'src/app/services/authentication.service';
import { ImageService } from 'src/app/services/image.service';
import { NotificationService } from 'src/app/services/notification.service';
import { RedirectService } from 'src/app/services/redirect.service';
import { UserService } from 'src/app/services/user.service';
import { MessageUtils } from 'src/app/utils/message-utils';
import { environment } from 'src/environments/environment';

import { SelectImageComponent } from '../select-image/select-image.component';
import { FormUtils } from 'src/app/utils/form-utils';

@Component({
	selector: 'app-user-informations',
	templateUrl: './user-informations.component.html',
	styleUrls: ['./user-informations.component.sass'],
})
export class UserInformationsComponent implements OnInit {

	authentication!: Authentication;
	form!: FormGroup;
	hide!: boolean;
	photo!: any;
	user!: User;

	constructor(
		private readonly _authenticationService: AuthenticationService,
		private readonly _dialog: MatDialog,
		private readonly _formBuilder: FormBuilder,
		private readonly _imageService: ImageService,
		private readonly _notificationService: NotificationService,
		private readonly _redirectService: RedirectService,
		private readonly _userService: UserService
	) { }

	ngOnInit(): void {

		this.hide = true;
		this.photo = null;

		this._authenticationService.getAuthentication().subscribe({

			next: (authentication) => {

				if (authentication) {
					this.authentication = authentication;
				}
			}
		});

		this._userService.get().subscribe({

			next: (user) => {

				if (user) {
					this.user = user;
					this.buildForm(user);

					if (user.photo) {
						this.photo = {
							id: user.photo.id,
							name: user.photo.name,
							path: user.photo.path
						};
					}
				} else {
					this.buildForm(null);
				}
			},
		});
	}

	addPhoto() {

		this._dialog
			.open(SelectImageComponent, {
				data: {
					multiple: false,
				},
				width: '100%',
			})
			.afterClosed()
			.subscribe((result) => {
				if (result && result.status) {
					this._imageService.toBase64(result.images[0])?.then((data) => {
						this.photo = {
							id: new Date().getTime(),
							data: data,
							file: result.images[0],
						};
					});
				}
			});
	}

	buildForm(user: User | null) {

		this.form = this._formBuilder.group({
			id: [user?.id, Validators.nullValidator],
			name: [user?.name, Validators.required],
			username: [user?.username, Validators.required],
			password: [user?.password, Validators.required],
			department: [user?.department, Validators.required],
			enabled: [user?.enabled, Validators.required],
			photo: [user?.photo, Validators.nullValidator],
		});

		if (user && user.username === this.authentication.username) {
			this.form.get('username')?.disable();
			this.form.get('department')?.disable();
			this.form.get('enabled')?.disable();
		}
	}

	back() {
		this._redirectService.toUserList();
	}

	getErrorMessage(controlName: string) {
		return FormUtils.getErrorMessage(this.form, controlName);
	}

	hasError(controlName: string) {
		return FormUtils.hasError(this.form, controlName);
	}

	removePhoto() {
		this.photo = null;
		this.form.get('photo')?.patchValue(null);
	}

	submit() {

		if (this.form.valid) {

			const user: User = Object.assign({}, this.form.getRawValue());
			const image: File = this.photo?.file;

			if (user.id) {

				this._userService.update(user, image).subscribe({
					
					next: (user) => {
						this._userService.set(user);
						this._notificationService.show(
							MessageUtils.USER_UPDATE_SUCCESS,
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
			}

			else {

				this._userService.save(user, image).subscribe({

					next: (user) => {
						this._notificationService.show(
							MessageUtils.USER_SAVE_SUCCESS,
							NotificationType.SUCCESS
						);
						this._redirectService.toUser(user.id);
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
