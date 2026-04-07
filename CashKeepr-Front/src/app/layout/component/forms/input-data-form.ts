import { FormGroup, FormsModule, ReactiveFormsModule } from '@angular/forms';
import { Component, EventEmitter, Input, Output } from '@angular/core';
import { InputMaskModule } from 'primeng/inputmask';
import { Message } from 'primeng/message';

@Component({
    selector: 'app-input-data-form',
    imports: [InputMaskModule, FormsModule, ReactiveFormsModule, Message],
    template: `
        <div [formGroup]="form">
            <p-inputmask mask="99/99/9999"
            dateFormat="dd/MM/yyyy"
                         size="large"
            [formControlName]="fcn"
            [placeholder]="placeh"
            [style]="{ width: '150px', 'text-align': 'center' }"
            />
        </div>
        @if (isInvalid() && req) {
            <p-message severity="error" size="small" variant="simple">Data inválida ou obrigatória!</p-message>
        }
    `,
    styles: ``
})
export class InputDataForm {
    @Input({ required: true }) fcn!: string;
    @Input() placeh: string = 'Data';
    @Input({ required: true }) form!: FormGroup;
    @Input() req: boolean = true;

    isInvalid() {
        const control = this.form.get(this.fcn);
        return control?.invalid && (control.touched || this.form);
    }
}
