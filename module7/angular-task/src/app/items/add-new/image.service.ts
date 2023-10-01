import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {environment} from "../../../environments/environment.development";

@Injectable({
  providedIn: 'root',
})
export class ImageService {

  private readonly path = environment.imagePath;

  constructor(private http: HttpClient) {
  }

  processImageFile(file: File, company: string, name: string) {
    if (file) {
      console.log(file.name, file.size, file.webkitRelativePath)
      const newFileName = this.generateUniqueFileName(file.name, company, name); // Generate a unique filename
      const formData = new FormData();
      formData.append('image', file, newFileName);
      this.http.post('http://localhost:4200/', formData).subscribe(
        (res) => {
          console.log('File uploaded successfully', res);
          this.onSuccess();
        },
        (err) => {
          console.error('File upload error', err);
          this.onError();
        });
      return newFileName;
    } else {
      console.log("not an image");
      return "no file found"
    }
  }

  private generateUniqueFileName(originalFileName: string, company: string, name: string): string {
    // const fileExtension = originalFileName.split('.').pop();
    // return `${name}_${company}.${fileExtension}`;
    return `${this.path}${originalFileName}`;
  }

  private onSuccess() {
  }

  private onError() {

  }
}
