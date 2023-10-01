import {Component, ElementRef, OnInit, ViewChild} from '@angular/core';
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {Router} from "@angular/router";
import {SnackBarService} from "../../shared/snack-bar.service";
import {Item} from "../item";
import {ItemsService} from "../items.service";
import {FormInputComponent} from "../../form-input-component";
import {CategoryService} from "../../shared/nav-bar/search-box/category.service";
import {FutureDateValidator} from "../validators/future-date-validator";
import {ImageService} from "./image.service";
import {BackButtonService} from "../../shared/back-button.service";

@Component({
  selector: 'add-new',
  templateUrl: './add-new.component.html',
  styleUrls: ['./add-new.component.css']
})
export class AddNewComponent extends FormInputComponent implements OnInit {

  readonly priceMin = 20;
  readonly priceMax = 50_000;
  categoriesList: string[] = [];
  @ViewChild('imageInput') imageInput!: ElementRef;

  constructor(private formBuilder: FormBuilder, private route: Router, private _snackBar: SnackBarService,
              private itemService: ItemsService, private categoryService: CategoryService,
              private imageService: ImageService, private backButton: BackButtonService) {
    super();
  }

  onSubmit() {
    console.log(this.inputForm);
    let item: Item = this.getFormData();
    console.log(item);
    if (this.itemService.addItem(item)) {
      this.inputForm.reset();
      this._snackBar.openSuccessSnackBar("Item successfully added");
    } else {
      this._snackBar.openInfoSnackBar("Seems the item already exist");
    }
  }

  ngOnInit(): void {
    this.inputForm = this.initFormControls();
    console.log(this.inputForm.get("tags"))
  }

  resetForm() {
    if (this.inputForm.pristine) {
      this.backButton.back();
    } else this.inputForm = this.initFormControls();
  }

  get categories() {
    return this.inputForm.get('categories');
  }

  protected getFormData(): Item {
    const image = this.imageInput.nativeElement.files[0];
    const companyName: string = this.inputForm.get('company')!.value;
    const itemName: string = this.inputForm.get('name')!.value;
    return {
      id: 0,
      name: itemName,
      company: companyName,
      createDate: new Date(),
      description: this.inputForm.get('description')!.value,
      duration: this.getDurationDays(this.inputForm.get('validTo')!.value),
      lastUpdateDate: new Date(),
      longDescription: this.inputForm.get('longDescription')!.value,
      price: this.inputForm.get('price')!.value,
      tags: this.inputForm.get('categories')?.value,
      image: this.imageService.processImageFile(image, companyName, itemName),
    };
  }

  private getDurationDays(value: Date) {
    console.log(`getDurationDays = ${value}`);
    let validToTime = new Date(value);
    let now = new Date();
    return Math.ceil((validToTime.getTime() - now.getTime()) / 1000 / 60 / 60 / 24);
  }

  protected initFormControls(): FormGroup {
    this.categoriesList = this.categoryService.getCategories();
    return this.formBuilder.group({
      company: ["", {validators: [Validators.required]}],
      name: ["", {validators: [Validators.required, Validators.minLength(3)]}],
      validTo: ["", {validators: [Validators.required, FutureDateValidator.validateDateIsFuture]}],
      description: ["", {
        updateOn: "change",
        validators: [Validators.required, Validators.maxLength(55)]
      }],
      longDescription: ["", {
        updateOn: "change",
        validators: [Validators.minLength(55)]
      }],
      price: ["", {validators: [Validators.min(this.priceMin), Validators.max(this.priceMax), Validators.required]}],
      image: ["", {updateOn: "change"}],
      categories: [[this.categoriesList[0]]],
    }, {
      updateOn: "blur"
    });
  }
}
