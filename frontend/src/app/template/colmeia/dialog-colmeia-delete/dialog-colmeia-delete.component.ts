import { Component, Inject, OnInit } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { ColmeiaService } from 'src/app/service/colmeia.service';

@Component({
  selector: 'app-dialog-colmeia-delete',
  templateUrl: './dialog-colmeia-delete.component.html',
  styleUrls: ['./dialog-colmeia-delete.component.css']
})
export class DialogColmeiaDeleteComponent implements OnInit {

  constructor(@Inject(MAT_DIALOG_DATA) public data: any, private colmeiaService: ColmeiaService, private matDialogRef: MatDialogRef<DialogColmeiaDeleteComponent>) { }

  ngOnInit(): void { }

  delete() {
    this.colmeiaService.delete(this.data.colmeia.id).subscribe(result => {
      this.matDialogRef.close({status: true, message: 'Sucesso'});
    },
    error => {
      this.matDialogRef.close({status: false, message: error.error.message});
    });
  }
}