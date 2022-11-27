import { formatDate } from '@angular/common';
import { Component, Inject, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { Colmeia } from 'src/app/model/colmeia.model';
import { ColmeiaService } from 'src/app/service/colmeia.service';

@Component({
  selector: 'app-dialog-colmeia-form',
  templateUrl: './dialog-colmeia-form.component.html',
  styleUrls: ['./dialog-colmeia-form.component.css']
})
export class DialogColmeiaFormComponent implements OnInit {

  form!: FormGroup;
  now!: Date;

  constructor(
    @Inject(MAT_DIALOG_DATA) public data: any, 
    private colmeiaService: ColmeiaService, 
    private formBuilder: FormBuilder,
    private matDialogRef: MatDialogRef<DialogColmeiaFormComponent>) {

    this.now = new Date();
  }

  ngOnInit(): void {
    
    if (this.data.colmeia) {
      this.buildForm(this.data.colmeia.id, this.data.colmeia.codigo, this.data.colmeia.telefone, this.data.colmeia.dataCadastro);
    }

    else {
      this.buildForm(null, null, null, this.now);
    }
  }

  buildForm(id: any, codigo: any, telefone: any, dataCadastro: any) {
    this.form = this.formBuilder.group({
      id: [id, Validators.nullValidator],
      codigo: [codigo, Validators.required],
      telefone: [telefone, Validators.required],
      dataCadastro: [formatDate(dataCadastro, 'dd/MM/yyyy', 'en-US'), Validators.nullValidator]
    });
  }

  getColmeia(values: any) {
    let colmeia = new Colmeia();

    colmeia.id = values.id;
    colmeia.codigo = values.codigo;
    colmeia.telefone = values.telefone;

    return colmeia;
  }

  submitForm () {
    let colmeia = this.getColmeia(Object.assign({}, this.form.value));

    if (colmeia.id) {
      this.colmeiaService.update(colmeia).subscribe(response => {
        this.matDialogRef.close({status: true, message: 'Sucesso'});
      },
      error => {
        this.matDialogRef.close({status: false, message: error.error.message});
      });
    }

    else {
      this.colmeiaService.save(colmeia).subscribe(response => {
        this.matDialogRef.close({status: true, message: 'Sucesso'});
      },
      error => {
        this.matDialogRef.close({status: false, message: error.error.message});
      });
    }
  }
}