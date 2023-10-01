import {FormGroup} from "@angular/forms";

export abstract class FormInputComponent {

  inputForm!: FormGroup;

  canDeactivate() {
    return this.inputForm.pristine;
  }

  abstract resetForm(): void;

  protected abstract initFormControls(): void;

  protected abstract getFormData(): any;

  abstract onSubmit(): void;
}
