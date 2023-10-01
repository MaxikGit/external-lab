import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class CategoryService {

  constructor() { }

  getCategories(){
    let result: string[] = [];
    for (let i = 1; i <= 30; i++) {
      result.push(`category${i}`)
    }
    return result;
  }
}
