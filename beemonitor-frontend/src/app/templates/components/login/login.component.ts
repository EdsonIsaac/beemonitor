import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { MatSnackBar } from '@angular/material/snack-bar';
import { Router } from '@angular/router';
import { Color } from 'src/app/enums/color';
import { Department } from 'src/app/enums/department';
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
    private router: Router,
    private snackBar: MatSnackBar
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

  showSnackBar(message: string, color: Color) {

    this.snackBar.open(message, undefined, {
      duration: 3000,
      horizontalPosition: 'center',
      verticalPosition: 'bottom',
      //panelClass: [color]
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
        this.showSnackBar(error.error.message, Color.DANGER);
      }
    })
  }
}