import { Component, EventEmitter, Input, Output } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { InputText } from 'primeng/inputtext';

@Component({
    selector: 'app-input-moeda',
    imports: [InputText, FormsModule],
    template: ` <input pInputText type="text" inputmode="numeric" placeholder="R$ 0,00"
                       [(ngModel)]="value" (ngModelChange)="valueChange.emit($event)"
                       (input)="onInput($event)" size="large"/> `,
    styles: ``
})
export class InputMoeda {
    // @Input({ required: true })
    // @Input() req: boolean= true;
    // @Input() legenda: string = 'Valor';

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
}
