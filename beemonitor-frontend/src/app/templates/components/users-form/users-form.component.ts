import { Component, Inject, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material/dialog';
import { User } from 'src/app/entities/user';
import { NotificationType } from 'src/app/enums/notification-type';
import { FacadeService } from 'src/app/services/facade.service';
import { MessageUtils } from 'src/app/utils/message-utils';

@Component({
  selector: 'app-users-form',
  templateUrl: './users-form.component.html',
  styleUrls: ['./users-form.component.sass']
})
export class UsersFormComponent implements OnInit {
  
  form!: FormGroup;
  hide!: boolean;

  constructor(
    @Inject(MAT_DIALOG_DATA) public data: any,
    private dialogRef: MatDialogRef<UsersFormComponent>,
    private facade: FacadeService,
    private formBuilder: FormBuilder
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

    this.form = this.formBuilder.group({
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
      
      this.facade.userSave(user).subscribe({

        complete: () => {
          this.facade.notificationShowNotification(MessageUtils.USER_UPDATE_SUCCESS, NotificationType.SUCCESS);
          this.dialogRef.close({status: true});
        },
  
        error: (error) => {
          console.error(error);
          this.facade.notificationShowNotification(MessageUtils.USER_UPDATE_FAIL + error.error[0].message, NotificationType.FAIL);   
        
        }
      });
    }

    else {

      this.facade.userSave(user).subscribe({

        complete: () => {
          this.facade.notificationShowNotification(MessageUtils.USER_SAVE_SUCCESS, NotificationType.SUCCESS);
          this.dialogRef.close({status: true});
        },
  
        error: (error) => {
          console.error(error);
          this.facade.notificationShowNotification(MessageUtils.USER_SAVE_FAIL + error.error[0].message, NotificationType.FAIL);   
        
        }
      });
    }
  }
}
