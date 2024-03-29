import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { NotificationType } from 'src/app/enums/notification-type';
import { AuthenticationService } from 'src/app/services/authentication.service';
import { NotificationService } from 'src/app/services/notification.service';
import { RedirectService } from 'src/app/services/redirect.service';
import { FormUtils } from 'src/app/utils/form-utils';
import { MessageUtils } from 'src/app/utils/message-utils';

@Component({
	selector: 'app-login',
	templateUrl: './login.component.html',
	styleUrls: ['./login.component.sass'],
})
export class LoginComponent implements OnInit {

	form!: FormGroup;
	hide!: boolean;

	constructor(
		private readonly _authenticationService: AuthenticationService,
		private readonly _formBuilder: FormBuilder,
		private readonly _notificationService: NotificationService,
		private readonly _redirectService: RedirectService
	) { }

	async ngOnInit(): Promise<void> {

		this.hide = true;
		const isAuthenticated = await this._authenticationService.isAuthenticated();
		isAuthenticated ? this._redirectService.toPanel() : this.buildForm();
	}

	buildForm() {

		this.form = this._formBuilder.group({
			username: [null, Validators.required],
			password: [null, Validators.required],
		});
	}

	getErrorMessage(controlName: string) {
		return FormUtils.getErrorMessage(this.form, controlName);
	}

	hasError(controlName: string) {
		return FormUtils.hasError(this.form, controlName);
	}

	submit() {

		const user = Object.assign({}, this.form.value);

		this._authenticationService.token(user).subscribe({

			next: (authentication) => {
				this._authenticationService.setAuthentication(authentication);
				this._redirectService.toPanel();
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