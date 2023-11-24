import { ValidatorFn } from '@angular/forms';

export function matchValidator(field1: string, field2: string): ValidatorFn {
  return control => {
    const _field1: string = control.get(field1).value;
    const _field2: string = control.get(field2).value;

    if (!_field1 && !_field2) {
      if (control.get(field1).errors) {
        delete control.get(field1).errors['mismatch'];
        if (Object.keys(control.get(field1).errors).length === 0) {
          control.get(field1).setErrors(null);
        }
      }
      return null;
    }

    if (_field1 !== _field2) {
      control.get(field1).setErrors({ mismatch: true });
    }

    return null;
  };
}
