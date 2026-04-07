import { Component, EventEmitter, Input, Output } from '@angular/core';
import { FormGroup, FormsModule, ReactiveFormsModule } from '@angular/forms';
import { InputText } from 'primeng/inputtext';
import { Message } from 'primeng/message';

@Component({
    selector: 'app-input-moeda-form',
    imports: [InputText, FormsModule, ReactiveFormsModule, Message],
    template: `
        <div [formGroup]="form">
            <input pInputText
            type="text"
                   size="large"
            inputmode="numeric"
            [formControlName]="fcn"
            placeholder="R$ 0,00"
            (input)="onInput($event)" />
        </div>
        @if (isInvalid() && req) {
            <p-message severity="error" size="small" variant="simple">Valor obrigatória!</p-message>
        }
    `,
    styles: ``
})
export class InputMoedaForm {
    @Input({ required: true }) fcn!: string;
    @Input({ required: true }) form!: FormGroup;
    @Input() req: boolean = true;
    @Input() value!: any;
    @Output() valueChange = new EventEmitter<any>();

    valorNumerico = 0; // valor em centavos
    valorFormatado = 'R$ 0,00';

    onInput(event: Event) {
        const input = event.target as HTMLInputElement;
        // remove tudo que não for número
        const apenasNumeros = input.value.replace(/\D/g, '');
        // evita NaN
        this.valorNumerico = Number(apenasNumeros || 0);
        // converte centavos para reais
        const valor = this.valorNumerico / 100;
        // formata para moeda brasileira
        this.valorFormatado = valor.toLocaleString('pt-BR', {
            style: 'currency',
            currency: 'BRL'
        });
        // mantém o input sincronizado
        input.value = this.valorFormatado;
    }

    isInvalid() {
        const control = this.form.get(this.fcn);
        return control?.invalid && (control.touched || this.form);
    }
}
