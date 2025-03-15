import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Component, ElementRef, inject, OnInit, ViewChild } from '@angular/core';
import { FormBuilder, FormControl, FormGroup } from '@angular/forms';

@Component({
  selector: 'app-file-upload',
  standalone: false,
  templateUrl: './file-upload.component.html',
  styleUrl: './file-upload.component.css'
})
export class FileUploadComponent implements OnInit {
  fb = inject(FormBuilder)
  http = inject(HttpClient)
  
  form!: FormGroup
  dataUri!: string;
  blob!: Blob;

  isLoading = false;

  ngOnInit(): void {
      this.form = this.createForm();
  }

  upload() {
    if (!this.dataUri) {
      console.log("no image uploaded.")
      return;
    }

    console.log(this.dataUri)
    
    const formData = new FormData();
    formData.set('comments', this.form.get('comments')?.value);
    formData.set('picture', this.dataURItoBlob(this.dataUri));

    console.log(formData)

    this.http.post('/api/post', formData)
      .subscribe({
        next: r => console.log(r),
        error: err => console.error(err)
      })
  }

  onImageUpload(event: Event) {
    this.isLoading = true;
    /** Use to assert that this won't be null
     *  (event.target as HTMLInputElement).files![0]
     */

    /** Chaining with Nullish Coalescing
     *  (event.target as HTMLInputElement.files?.[0] ?? null
     *  
     *  Optional chaining to safely access [0] only if files exists. Fallback to null
     */

    const input = event.target as HTMLInputElement;
    console.log(input.files)
    if (input.files && input.files.length > 0) {
      const file = input.files[0];
      const reader = new FileReader();
      reader.onload = () => {
        this.dataUri = reader.result as string;
        this.isLoading = false;
      }
      reader.readAsDataURL(file);
      this.form.get('picture')?.setValue(this.dataUri)

    }
  }

  private createForm(): FormGroup {
    return this.fb.group({
      comments: this.fb.control<string>(''),
      picture: this.fb.control<string>('')
    })
  }

  dataURItoBlob(dataURI: string): Blob{
    const [meta, base64Data] = dataURI.split(',');
    const mimeMatch = meta.match(/:(.*?);/);

    const mimeType = mimeMatch ? mimeMatch[1] : 'application/octet-stream';
    const byteString = atob(base64Data);
    const ab = new ArrayBuffer(byteString.length);
    const ia = new Uint8Array(ab);
    for(let i = 0; i < byteString.length; i++){
      ia[i] = byteString.charCodeAt(i);
    }
    return new Blob([ia], {type: mimeType});
  }
}
