import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { Department } from 'src/app/enums/department';
import { NotificationType } from 'src/app/enums/notification-type';
import { AuthService } from 'src/app/services/auth.service';
import { NotificationService } from 'src/app/services/notification.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.sass']
})
export class LoginComponent implements OnInit {

  form!: FormGroup;
  hide!: boolean;

  constructor(
    private _authService: AuthService,
    private _formBuilder: FormBuilder,
    private _notificationService: NotificationService,
    private _router: Router
  ) { }

  ngOnInit(): void {
    
    this.hide = true;

    if (this._authService.isAuthenticated()) {
    
      let user: any = this._authService.getUser();
      this._router.navigate(['/' + user.role.toLowerCase()]);
    } 
    
    else {
      this.buildForm();
    }
  }

  buildForm() {

    this.form = this._formBuilder.group({
      username: [null, Validators.required],
      password: [null, Validators.required]
    });
  }

  submit() {
    const user = Object.assign({}, this.form.value);

    this._authService.login(user).subscribe({

      next: (user) => {
        this._authService.setUser(user);
      },

      complete: () => {
        let user = this._authService.getUser();

        switch (user.role) {

          case Department.ADMINISTRATION:
            this._router.navigate(['/administration']);
            break;
          case Department.SUPPORT:
            this._router.navigate(['/support']);
            break;
        }
      },

      error: (error) => {
        console.error(error);
        this._notificationService.show(error.error.message, NotificationType.FAIL);
      }
    })
  }
}