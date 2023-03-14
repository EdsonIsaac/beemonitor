import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { MatDialog, MatDialogRef } from '@angular/material/dialog';
import { User } from 'src/app/entities/user';
import { NotificationType } from 'src/app/enums/notification-type';
import { ImageService } from 'src/app/services/image.service';
import { NotificationService } from 'src/app/services/notification.service';
import { UserService } from 'src/app/services/user.service';
import { MessageUtils } from 'src/app/utils/message-utils';

import { SelectImageComponent } from '../select-image/select-image.component';

@Component({
  selector: 'app-user-form',
  templateUrl: './user-form.component.html',
  styleUrls: ['./user-form.component.sass']
})
export class UserFormComponent implements OnInit {
  
  form!: FormGroup;
  hide!: boolean;
  photo!: any;
  photoToSave!: any;

  constructor(
    private _dialog: MatDialog,
    private _dialogRef: MatDialogRef<UserFormComponent>,
    private _formBuilder: FormBuilder,
    private _imageService: ImageService,
    private _notificationService: NotificationService,
    private _userService: UserService
  ) { }

  ngOnInit(): void {
    this.hide = true;
    this.buildForm();
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

          let imagem = { id: new Date().getTime(), data: data };

          this.photo = imagem;
          this.photoToSave = result.images[0];
        });
      }
    });
  }

  buildForm() {

    this.form = this._formBuilder.group({
      id: [null, Validators.nullValidator],
      name: [null, Validators.required],
      username: [null, Validators.required],
      password: [null, Validators.required],
      department: [null, Validators.required],
      enabled: [null, Validators.required],
      photo: [null, Validators.nullValidator]
    });
  }

  removePhoto() {
    this.photo = null;
    this.photoToSave = null;
    this.form.get('photo')?.patchValue(null);
  }

  submit() {

    const user: User = Object.assign({}, this.form.getRawValue());

    this._userService.save(user, this.photoToSave).subscribe({

      complete: () => {
        this._notificationService.show(MessageUtils.USER_SAVE_SUCCESS, NotificationType.SUCCESS);
        this._dialogRef.close({status: true});
      },

      error: (error) => {
        console.error(error);
        this._notificationService.show(MessageUtils.USER_SAVE_FAIL + error.error[0].message, NotificationType.FAIL);   
      
      }
    });
  }
}