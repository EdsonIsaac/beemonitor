import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { MatDialog } from '@angular/material/dialog';
import { User } from 'src/app/entities/user';
import { NotificationType } from 'src/app/enums/notification-type';
import { AuthService } from 'src/app/services/auth.service';
import { ImageService } from 'src/app/services/image.service';
import { NotificationService } from 'src/app/services/notification.service';
import { UserService } from 'src/app/services/user.service';
import { MessageUtils } from 'src/app/utils/message-utils';
import { environment } from 'src/environments/environment';

import { SelectImageComponent } from '../select-image/select-image.component';

@Component({
  selector: 'app-user-informations',
  templateUrl: './user-informations.component.html',
  styleUrls: ['./user-informations.component.sass']
})
export class UserInformationsComponent implements OnInit {
  
  apiURL!: string;
  form!: FormGroup;
  hide!: boolean;
  photo!: any;
  photoToSave!: any;
  user!: User;
  userLogged!: any;

  constructor(
    private _authService: AuthService,
    private _dialog: MatDialog,
    private _formBuilder: FormBuilder,
    private _imageService: ImageService,
    private _notificationService: NotificationService,
    private _userService: UserService,
  ) { }

  ngOnInit(): void {
    
    this.apiURL = environment.apiURL;
    this.hide = true;
    this.userLogged = this._authService.getUser();
    
    this._userService.get().subscribe({

      next: (user) => {
        
        if (user) {
          this.user = user;
          this.buildForm(user);

          if (user.photo) {
            this.photo = { id: user.photo.id, data: null, name: user.photo.name };
          }
        }
      }
    });
  }

  addPhoto() {

    this._dialog.open(SelectImageComponent, {
      data: {
        multiple: false
      },
      width: '100%'
    })
    .afterClosed().subscribe(result => {

      if (result && result.status) {

        this._imageService.toBase64(result.images[0])?.then(data => {

          let imagem = { id: new Date().getTime(), data: data, name: null };

          this.photo = imagem;
          this.photoToSave = result.images[0];
        });
      }
    });
  }

  buildForm(user: User) {

    this.form = this._formBuilder.group({
      id: [user.id, Validators.nullValidator],
      name: [user.name, Validators.required],
      username: [user.username, Validators.required],
      password: [user.password, Validators.required],
      department: [user.department, Validators.required],
      enabled: [user.enabled, Validators.required],
      photo: [user.photo, Validators.nullValidator]
    });

    this.form.disable();
  }

  cancel() {
    this.buildForm(this.user);
  }

  removePhoto() {
    this.photo = null;
    this.photoToSave = null;
    this.form.get('photo')?.patchValue(null);
  }

  submit() {

    const user: User = Object.assign({}, this.form.getRawValue());

    this._userService.update(user, this.photoToSave).subscribe({

      next: (user) => {
        this.photoToSave = null;
        this._userService.set(user);
        this._notificationService.show(MessageUtils.USER_UPDATE_SUCCESS, NotificationType.SUCCESS);
      },

      error: (error) => {
        console.error(error);
        this._notificationService.show(MessageUtils.USER_UPDATE_FAIL + error.error[0].message, NotificationType.FAIL);   
      }
    });
  }

  update() {

    this.form.enable();
    
    if (this.user.username === this.userLogged.username) {
      this.form.get('username')?.disable();
      this.form.get('department')?.disable();
      this.form.get('enabled')?.disable();
    }
  }
}