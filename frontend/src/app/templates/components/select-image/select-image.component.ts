import { Component, Inject } from '@angular/core';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material/dialog';

@Component({
  selector: 'app-select-image',
  templateUrl: './select-image.component.html',
  styleUrls: ['./select-image.component.sass']
})
export class SelectImageComponent {

  images!: Array<File>;

  constructor(
    @Inject(MAT_DIALOG_DATA) public data: any, 
    private _matDialogRef: MatDialogRef<SelectImageComponent>
  ) { }

  onChange(event: any) {
    const selectedFiles = <FileList> event.srcElement.files;
    
    this.images = new Array();

    for (let i = 0; i< selectedFiles.length; i++) {
      this.images.push(selectedFiles[i]);
    }
  }

  readImage(image: File) {

    const fileReader = new FileReader();

    fileReader.readAsDataURL(image);

    fileReader.onloadend = () => {
      return fileReader.result;
    }
  }

  submit () {

    if (this.images && this.images.length > 0) {
      this._matDialogRef.close({status: true, images: this.images});
    }
  }
}