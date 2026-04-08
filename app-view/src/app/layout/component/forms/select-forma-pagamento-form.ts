import { FormaPagamentoService } from '@/pages/service/forma-pagamento-service';
import { AsyncPipe } from '@angular/common';
import { Component, Input, OnInit } from '@angular/core';
import { FormGroup, FormsModule, ReactiveFormsModule } from '@angular/forms';
import { MessageService } from 'primeng/api';
import { Message } from 'primeng/message';
import { SelectModule } from 'primeng/select';
import { Observable } from 'rxjs/internal/Observable';

@Component({
    selector: 'app-select-forma-pagamento-form',
    imports: [SelectModule, AsyncPipe, FormsModule, ReactiveFormsModule, Message],
    template: `
        <div [formGroup]="form">
            <p-select [options]="(content$ | async) ?? []" style="width: 100%;"
                      size="large"
                      formControlName="formaPagamento" placeholder="Forma de Pagamento"
                      dataKey="uuid" selectOnFocus="true" [scrollHeight]="scrHeight">
                <ng-template #selectedItem let-formaSelected>
                    @if (formaSelected) {
                        <span>{{ formaSelected.modalidade.descricao + ' ' + (formaSelected.instituicao ?? '') }}</span>
                    }
                </ng-template>
                <ng-template let-forma #item>
                    <span>{{ forma.modalidade.descricao + ' ' + (forma.instituicao ?? '') }}</span>
                </ng-template>
            </p-select>
        </div>
        @if (isInvalid() && req) {
            <p-message severity="error" size="small" variant="simple">Forma de Pagamento obrigatória!</p-message>
        }
    `,
    styles: ``
})
export class SelectFormaPagamentoForm implements OnInit {
    content$!: Observable<any[]>;
    @Input({ required: true }) form!: FormGroup;
    @Input() req: boolean = true;
    @Input() scrHeight: string = '300px';

    constructor(
        private formaPagamentoService: FormaPagamentoService,
        private messageService: MessageService
    ) {}

    ngOnInit(): void {
        this.content$ = this.formaPagamentoService.getFormasPagamento(this.messageService);
    }

    isInvalid() {
        const control = this.form.get('formaPagamento');
        return control?.invalid && (control.touched || this.form);
    }
}
