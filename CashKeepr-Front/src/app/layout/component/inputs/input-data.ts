import { FormsModule } from '@angular/forms';
import { Component, EventEmitter, Input, Output } from '@angular/core';
import { InputMaskModule } from 'primeng/inputmask';

@Component({
    selector: 'app-input-data',
    imports: [InputMaskModule, FormsModule],
    template: `<p-inputmask mask="99/99/9999"
    dateFormat="dd/MM/yyyy"
    [(ngModel)]="value"
    [required]="req"
                            size="large"
    [placeholder]="placeHolder"
    (ngModelChange)="valueChange.emit($event)"
    [style]="{ width: '100px', 'text-align': 'center' }" /> `,
    styles: ``
})
export class InputData {
    @Input() req: boolean = true;
    @Input() value!: any;
    @Input() placeHolder!: string;
    @Output() valueChange = new EventEmitter<any>();
}
