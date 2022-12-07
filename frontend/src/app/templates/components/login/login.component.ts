import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { Department } from 'src/app/enums/department';
import { NotificationType } from 'src/app/enums/notification-type';
import { FacadeService } from 'src/app/services/facade.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.sass']
})
export class LoginComponent implements OnInit {

  form!: FormGroup;
  hide!: boolean;

  constructor(
    private facade: FacadeService,
    private formBuilder: FormBuilder,
    private router: Router
  ) { }

  ngOnInit(): void {

    this.hide = true;
    this.buildForm();
  }

  buildForm() {

    this.form = this.formBuilder.group({
      username: [null, Validators.required],
      password: [null, Validators.required]
    });
  }

  submit() {
    const user = Object.assign({}, this.form.value);

    this.facade.authLogin(user).subscribe({

      next: (authentication) => {

        this.facade.authSetCurrentUser({
          token: authentication.access_token,
          role: authentication.role
        })
      },

      complete: () => {
        let currentUser = this.facade.authGetCurrentUser();

        switch (currentUser.role) {

          case Department.ADMINISTRATION:
            this.router.navigate(['/administration']);
            break;
          case Department.SUPPORT:
            this.router.navigate(['/support']);
            break;
        }
      },

      error: (error) => {
        console.error(error);
        this.facade.notificationShowNotification(error.error.message, NotificationType.FAIL);
      }
    })
  }
}