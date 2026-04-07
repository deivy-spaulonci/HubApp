import { Component, Input, OnInit } from '@angular/core';
import { Observable } from 'rxjs/internal/Observable';
import { SelectModule } from 'primeng/select';
import { AsyncPipe } from '@angular/common';
import { FormGroup, FormsModule, ReactiveFormsModule } from '@angular/forms';
import { Message } from 'primeng/message';
import { TipoContaService } from '@/pages/service/tipo-conta-service';

@Component({
    selector: 'app-select-tipo-conta-form',
    imports: [SelectModule, AsyncPipe, FormsModule, ReactiveFormsModule, Message],
    template: `
        <div [formGroup]="form">
            <p-select [options]="(content$ | async) ?? []" #tipoConta selectOnFocus="true" optionLabel="nome" 
            style="width: 100%" formControlName="tipoConta" placeholder="Tipo de Conta" [scrollHeight]="scrHeight"
            dataKey="uuid" filter="true"/>
        </div>
        @if (isInvalid() && req) {
            <p-message severity="error" size="small" variant="simple">Tipo de Conta obrigatório!</p-message>
        }
    `,
    styles: ``
})
export class SelectTipoContaForm implements OnInit {
    @Input({ required: true }) form!: FormGroup;
    content$!: Observable<any[]>;
    @Input() req: boolean = true;
    @Input() scrHeight: string = '400px';


    constructor(private tipoContaService: TipoContaService) {}

    ngOnInit(): void {
        this.content$ = this.tipoContaService.getTipos();
    }

    isInvalid() {
        const control = this.form.get('tipoConta');
        return control?.invalid && (control.touched || this.form);
    }
}
