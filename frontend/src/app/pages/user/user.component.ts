import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { User } from 'src/app/models/user';
import { NotificationType } from 'src/app/enums/notification-type';
import { NotificationService } from 'src/app/services/notification.service';
import { UserService } from 'src/app/services/user.service';
import { MessageUtils } from 'src/app/utils/message-utils';

@Component({
	selector: 'app-user',
	templateUrl: './user.component.html',
	styleUrls: ['./user.component.sass'],
})
export class UserComponent implements OnInit {
	user!: User | null;

	constructor(
		private _activatedRoute: ActivatedRoute,
		private _notificationService: NotificationService,
		private _userService: UserService
	) { }

	ngOnInit(): void {

		const id = this._activatedRoute.snapshot.paramMap.get('id');

		if (id) {

			if (id.includes('register')) {
				this._userService.set(null);
			}

			else {

				this._userService.findById(id).subscribe({

					next: (user) => {
						this._userService.set(user);
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

		this._userService.get().subscribe({
			next: (user) => {
				this.user = user;
			},
		});
	}
}