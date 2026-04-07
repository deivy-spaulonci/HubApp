import { DespesaService } from '@/pages/service/despesa-service';
import { ImportsModule } from '@/util/imports';
import { Util } from '@/util/util';
import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { MessageService } from 'primeng/api';

@Component({
    selector: 'app-despesa-form',
    standalone: true,
    imports: [ImportsModule],
    providers: [MessageService],
    template: `
        <form [formGroup]="dForm" (ngSubmit)="onSubmit()">
            <div class="card flex flex-col gap-4 md:w-5/12">
                <div class="flex flex-col gap-2">
                    <label for="tipodespesa">Tipo de Despesa: </label>
                    <app-select-tipo-despesa-form [form]="dForm" />
                </div>
                <div class="flex flex-col gap-2">
                    <label for="fornecedor">Fornecedor: </label>
                    <app-autocomplete-fornecedor-form [form]="dForm" />
                </div>
                <div class="flex flex-col gap-2">
                    <label for="data">Data Pagamento: </label>
                    <app-input-data-form [form]="dForm" fcn="dataPagamento" [req]="true" placeh="Data Pagamento"/>
                </div>
                <div class="flex flex-col gap-2">
                    <label for="formaPagamento">Forma Pagamento: </label>
                    <app-select-forma-pagamento-form [form]="dForm" />
                </div>
                <div class="flex flex-col gap-2">
                    <label for="valor">Valor: </label>
                    <app-input-moeda-form [form]="dForm" fcn="valor"/>
                </div>
                <div class="flex flex-col gap-2">
                    <label for="valor">Observação: </label>
                    <textarea rows="3" cols="20" size="large" pTextarea [autoResize]="true" formControlName="observacao"></textarea>
                </div>
                <p-button label="Salvar" severity="success" raised icon="pi pi-save" [disabled]="dForm.invalid" type="submit" />
            </div>
        </form>
        <p-toast />
    `,
    styles: ``
})
export class DespesaForm implements OnInit {
    tipo!: any;
    fornecedor!: any;
    data!: any;
    formaPagamento!: any;
    valor!: any;
    observacao!: any;
    dForm: FormGroup;

    constructor(
        private fb: FormBuilder,
        private despesaService: DespesaService,
        private messageService: MessageService
    ) {
        this.dForm = this.fb.group({
            tipoDespesa: ['', Validators.required],
            fornecedor: ['', Validators.required],
            dataPagamento: ['', [Validators.required, Validators.pattern(/^(0[1-9]|[12][0-9]|3[01])\/(0[1-9]|1[0-2])\/\d{4}$/)]],
            formaPagamento: ['', Validators.required],
            valor: ['', Validators.required],
            observacao: ['', Validators.nullValidator]
        });
    }

    ngOnInit(): void {}

    onSubmit() {
        if (this.dForm.valid) {
            const dados = this.dForm.getRawValue();
            var valorSemFormato = dados.valor.replace(/\D/g, '') / 100;
            dados.valor = valorSemFormato;
            dados.dataPagamento = Util.dataBRtoDataIso(dados.dataPagamento);

            this.despesaService.save(dados).subscribe({
                next: (res) => {
                    this.dForm.reset();
                    Util.showMsgSuccess(this.messageService, 'Sucesso!');
                },
                error: (err) => {
                    Util.showMsgErro(this.messageService, 'Erro ao salvar despesa');
                }
            });
        }
    }
}
