import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Hive } from 'src/app/entities/hive';
import { NotificationType } from 'src/app/enums/notification-type';
import { AuthService } from 'src/app/services/auth.service';
import { HiveService } from 'src/app/services/hive.service';
import { NotificationService } from 'src/app/services/notification.service';
import { MessageUtils } from 'src/app/utils/message-utils';

@Component({
  selector: 'app-hive',
  templateUrl: './hive.component.html',
  styleUrls: ['./hive.component.sass']
})
export class HiveComponent implements OnInit {

  hive!: Hive;
  user!: any;

  constructor(
    private _activatedRoute: ActivatedRoute,
    private _authService: AuthService,
    private _hiveService: HiveService,
    private _notificationService: NotificationService
  ) { }

  ngOnInit(): void {
    
    this.user = this._authService.getUser();

    this._activatedRoute.params.subscribe({

      next: (x: any) => {
          
        if (x && x.id) {
          
          this._hiveService.findById(x.id).subscribe({
            
            next: (hive) => {
              this._hiveService.set(hive);
            },

            error: (error) => {
              console.error(error);
              this._notificationService.show(MessageUtils.HIVE_GET_FAIL, NotificationType.FAIL); 
            }
          });
        }
      },
    });

    this._hiveService.get().subscribe({

      next: (hive) => {
        
        if (hive) {
          this.hive = hive;
        }
      },
    });
  }
}