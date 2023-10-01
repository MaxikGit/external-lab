import {Injectable} from '@angular/core';
import {Item} from "./item";
import {environment} from "../../environments/environment";
import {CategoryService} from "../shared/nav-bar/search-box/category.service";
import {delay, Observable, of, tap} from "rxjs";
import {IsLoadingService} from "./is-loading.service";

@Injectable({
  providedIn: 'root'
})
export class ItemsDummydataService {

  private readonly itemsInitSize = 500;
  private readonly loadingDelay = 1000;
  private readonly items: Item[] = [];
  private imageDefaultName = "gray_background.jpg";
  private imagesDir = environment.imagePath;

  constructor(private categoryService: CategoryService, private isLoadingService: IsLoadingService) {
    this.initItems();
  }

  getItems(page: number, itemsPerPage: number): Observable<Item[]> {
    const startIndex = (page - 1) * itemsPerPage;
    const endIndex = startIndex + itemsPerPage;
    return of(this.items.slice(startIndex, endIndex))
      .pipe(
        tap(() => this.isLoadingService.startLoading()),
        delay(this.loadingDelay),
        tap(() => this.isLoadingService.finishLoading())
      );
  }

  getItemByName(name: string): Item {
    return this.items.filter(item => item.name === name)[0];
  }

  getItemById(index: number): Item {
    return this.items.filter(item => item.id === index)[0];
  }

  addItem(item: Item): Item | undefined {
    if (this.getItemByName(item.name)) {
      return undefined;
    }
    item.id = Math.max(...this.items.map(item => item.id)) + 1;
    this.items.push(item);
    return item;
  }

  private initItems() {
    let categoriesNum = this.categoryService.getCategories().length;
    let result: Item[] = [];
    for (let i = 1; i <= this.itemsInitSize; i++) {
      result.push({
        name: `Coupon${i}`,
        description: "Lorem ipsum dolor sit amet, consectetur adipisicing elit. Iste, iusto, officia? Asperiores deserunt ea esse ...",
        duration: 11 * i % 30,
        id: i,
        price: (i + 50) * 123 % 7777,
        createDate: new Date(),
        lastUpdateDate: new Date(),
        tags: [
          `category${(i * 7) % categoriesNum}`,
          `category${(i * 7 + 1) % categoriesNum}`,
          `category${(i * 7 + 2) % categoriesNum}`,
        ],
        image: i === 1 ? "bbq_british_ribs.webp" : undefined,
        longDescription: "Vivamus sagittis lobortis turpis, semper fringilla est faucibus et. Proin at tincidunt mauris, eu lacinia ipsum. Nullam tincidunt orci et orci vulputate, et volutpat quam molestie. Sed pretium posuere blandit. Nulla fringilla, velit vitae ullamcorper consectetur, mauris velit dictum nisl, sed convallis mi sapien sed quam. Orci varius natoque penatibus et magnis dis parturient montes, nascetur ridiculus mus. Donec eu dolor id tortor ullamcorper gravida tristique non metus. Etiam interdum venenatis mauris, ut vestibulum dolor vulputate ullamcorper."
      });
    }
    this.items.push(...result.map(item => this.createImageFileName(item)));
    console.log(`items initiated, size = ${this.items.length}`)
  }

  private createImageFileName(item: Item) {
    if (item.image) {
      item.image = this.imagesDir + item.image;
    } else {
      item.image = this.imagesDir + this.imageDefaultName;
    }
    return item;
  }
}
