import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { MatSnackBar } from '@angular/material/snack-bar';
import { Title } from '@angular/platform-browser';
import { Router } from '@angular/router';
import { AuthService } from 'src/app/service/auth.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {

  hide!: boolean;
  form!: FormGroup;

  constructor(private authService: AuthService, private router: Router, private formBuilder: FormBuilder, private snackBar: MatSnackBar, private title: Title) {
    this.hide = true;
  }

  ngOnInit(): void {
    
    if (this.authService.isAuthenticated()) {
      this.router.navigate(['/colmeias']);
    }

    this.buildForm();
    this.title.setTitle("BeeMonitor - Login");
  }

  buildForm() {
    this.form = this.formBuilder.group({
      username: ['', Validators.required],
      password: ['', Validators.required]
    });
  }

  submit() {
    const usuario = Object.assign({}, this.form.value);

    this.authService.login(usuario).subscribe(response => {
      this.router.navigate(['/colmeias']);
    },
    error => {
      this.showSnackBar(error.error.message, 'bg-danger');
    })
  }

  showSnackBar(mensagem: string, cor: string) {
    this.snackBar.open(mensagem, 'X', {
      horizontalPosition: 'center',
      verticalPosition: 'top',
      panelClass: [cor]
    });
  }
}