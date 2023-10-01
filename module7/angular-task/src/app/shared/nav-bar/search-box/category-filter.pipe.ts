import { Pipe, PipeTransform } from '@angular/core';

@Pipe({
  name: 'categoryFilter'
})
export class CategoryFilterPipe implements PipeTransform {

  transform(categories: string[], name: string): string[] {
    return categories.filter(category => category.includes(name));
  }

}
