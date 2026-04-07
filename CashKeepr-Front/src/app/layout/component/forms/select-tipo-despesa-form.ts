import { TipoDespesaService } from '@/pages/service/tipo-despesa-service';
import { Component, Input, OnInit } from '@angular/core';
import { Observable } from 'rxjs/internal/Observable';
import { SelectModule } from 'primeng/select';
import { AsyncPipe } from '@angular/common';
import { FormGroup, FormsModule, ReactiveFormsModule } from '@angular/forms';
import { Message } from 'primeng/message';
import { map } from 'rxjs';

@Component({
    selector: 'app-select-tipo-despesa-form',
    imports: [SelectModule, AsyncPipe, FormsModule, ReactiveFormsModule, Message],
    template: `
        <div [formGroup]="form">
            <p-select [options]="(content$ | async) ?? []"
                optionLabel="nome"
                style="width: 100%"
                      size="large"
                formControlName="tipoDespesa"
                placeholder="Tipo de Despesa"
                scrollHeight="400px" />
        </div>
        @if (isInvalid() && req) {
            <p-message severity="error" size="small" variant="simple">Tipo de despesa obrigatória!</p-message>
        }
    `,
    styles: ``
})
export class SelectTipoDespesaForm implements OnInit {
    @Input({ required: true }) form!: FormGroup;
    @Input() uuidSelecionado!: string;
    content$!: Observable<any[]>;
    @Input() req: boolean = true;

    constructor(private tipoDespesaService: TipoDespesaService) {}

    ngOnInit(): void {
        this.content$ = this.tipoDespesaService.getTipos();
        if(this.uuidSelecionado)
            this.content$.pipe(map(lista => lista.find(item => item.uuid === this.uuidSelecionado)))
    }

    isInvalid() {
        const control = this.form.get('tipoDespesa');
        return control?.invalid && (control.touched || this.form);
    }
}
