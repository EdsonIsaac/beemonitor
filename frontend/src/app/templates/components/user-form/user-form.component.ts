import { Component, Inject, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material/dialog';
import { User } from 'src/app/entities/user';
import { NotificationType } from 'src/app/enums/notification-type';
import { NotificationService } from 'src/app/services/notification.service';
import { UserService } from 'src/app/services/user.service';
import { MessageUtils } from 'src/app/utils/message-utils';

@Component({
  selector: 'app-user-form',
  templateUrl: './user-form.component.html',
  styleUrls: ['./user-form.component.sass']
})
export class UserFormComponent implements OnInit {
  
  form!: FormGroup;
  hide!: boolean;

  constructor(
    @Inject(MAT_DIALOG_DATA) public data: any,
    private _dialogRef: MatDialogRef<UserFormComponent>,
    private _formBuilder: FormBuilder,
    private _notificationService: NotificationService,
    private _userService: UserService
  ) { }

  ngOnInit(): void {
    
    this.hide = true;

    if (this.data.user) {
      this.buildForm(this.data.user);
    } else {
      this.buildForm(null);
    }
  }

  buildForm(user: User | null) {

    this.form = this._formBuilder.group({
      id: [user?.id, Validators.nullValidator],
      name: [user?.name, Validators.required],
      username: [user?.username, Validators.required],
      password: [user?.password, Validators.required],
      department: [user?.department, Validators.required],
      enabled: [user?.enabled, Validators.required]
    });
  }

  submit() {

    let user: User = Object.assign({}, this.form.value);

    if (user.id) {
      
      this._userService.update(user).subscribe({

        complete: () => {
          this._notificationService.show(MessageUtils.USER_UPDATE_SUCCESS, NotificationType.SUCCESS);
          this._dialogRef.close({status: true});
        },
  
        error: (error) => {
          console.error(error);
          this._notificationService.show(MessageUtils.USER_UPDATE_FAIL + error.error[0].message, NotificationType.FAIL);   
        
        }
      });
    }

    else {

      this._userService.save(user).subscribe({

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
}