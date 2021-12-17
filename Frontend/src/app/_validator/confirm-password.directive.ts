import {Directive, Input} from '@angular/core';
import {AbstractControl, NG_VALIDATORS, Validator} from "@angular/forms";

@Directive({
  selector: '[appConfirmPassword]',
  providers: [{provide: NG_VALIDATORS, useExisting: ConfirmPasswordDirective, multi: true}]
})
export class ConfirmPasswordDirective implements Validator {

  @Input() password;

  // @Input() passwordC;

  validate(control: AbstractControl): { [key: string]: any } | null {
    // console.log(control.parent.controls['password'].value +' '+ control.parent.controls['passwordC'].value)
    // let NIC_REGEX = /^[0-9]{9}[Vv]$|^[0-9]{11}[Vv]$/; // Regular Expression 1
    if (control.parent.controls['password'] === undefined || control.parent.controls['passwordC'] === undefined) {
      return null;
    }

    if (control.parent.controls['password'].value === control.parent.controls['passwordC'].value) {
      control.parent.controls['password'].errors = null;
      control.parent.controls['passwordC'].errors = null;
      control.parent.controls['password'].status = 'VALID'
      control.parent.controls['passwordC'].status = 'VALID'
      // console.log(control.parent.controls)
      return null;
    }

    return {'confirmPassword': true};
  }

  constructor() {
  }

}
