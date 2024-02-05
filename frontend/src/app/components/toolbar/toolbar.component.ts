import { Component, OnInit } from '@angular/core';
import { User } from 'src/app/models/user';
import { AuthenticationService } from 'src/app/services/authentication.service';
import { RedirectService } from 'src/app/services/redirect.service';
import { UserService } from 'src/app/services/user.service';

@Component({
	selector: 'app-toolbar',
	templateUrl: './toolbar.component.html',
	styleUrls: ['./toolbar.component.sass'],
})
export class ToolbarComponent implements OnInit {

	theme!: string;
	user!: User;

	constructor(
		private readonly _authenticationService: AuthenticationService,
		private readonly _redirectService: RedirectService,
		private readonly _userService: UserService
	) { }

	ngOnInit(): void {
		this.theme = localStorage.getItem('theme') ?? 'light';

		this.checkTheme();

		this._userService.getUserLogged().subscribe({

			next: (user) => {
				if (user) this.user = user;
			}
		})
	}

	changeTheme() {
		this.theme = this.theme === 'dark' ? 'light' : 'dark';
		this.checkTheme();
	}

	checkTheme() {

		if (this.theme === 'dark') {
			document.documentElement.classList.add('dark');
			localStorage.setItem('theme', 'dark');
		} else {
			document.documentElement.classList.remove('dark');
			localStorage.setItem('theme', 'light');
		}
	}

	redirectToPanel() {
		this._redirectService.toPanel();
	}

	logout() {
		this._authenticationService.logout();
		this._redirectService.toLogin();
	}
}