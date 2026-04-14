import { SelectTipoContaForm } from '@/layout/component/forms/select-tipo-conta-form';
import { ContaService } from '@/pages/service/conta-service';
import { ImportsModule } from '@/util/imports';
import { Util } from '@/util/util';
import { Component, OnInit, ViewChild } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { MessageService } from 'primeng/api';
import { Select } from 'primeng/select';
import { Observable } from 'rxjs';

@Component({
    selector: 'app-conta-form',
    standalone: true,
    imports: [ImportsModule],
    providers: [MessageService],
    template: `
        <form [formGroup]="cForm" (ngSubmit)="onSubmit()">
            <div class="card flex flex-col gap-4 md:w-5/12">
                <div class="flex flex-col gap-2">
                    <label for="codigoBarras">Codigo de Barras : </label>
                    <p-inputmask [mask]="maskCnpj" autoClear="false" formControlName="codigoBarras" placeholder="Codigo de Barras" slotChar="" [styleClass]="'w-full'" />
                    @if (isInvalid('codigoBarra')) {
                        <p-message severity="error" size="small" variant="simple">Codigo de Barras obrigatório!</p-message>
                    }
                </div>
                <div class="flex flex-col gap-2">Tipo de Conta: <app-select-tipo-conta-form [form]="cForm" [req]="true" scrHeight="550px" /></div>

                <div class="flex flex-wrap gap-6">
                    <div class="flex flex-col grow basis-0 gap-2">Emissão:<app-input-data-form [form]="cForm" placeh="emissao" [req]="true" fcn="emissao" /></div>
                    <div class="flex flex-col grow basis-0 gap-2">Vencimento:<app-input-data-form [form]="cForm" placeh="vencimento" [req]="true" fcn="vencimento" /></div>
                </div>

                <div class="flex flex-wrap gap-6">
                    <div class="flex flex-col grow basis-0 gap-2">
                        Parcela:
                        <div>
                            <p-inputnumber mode="decimal" inputId="withoutgrouping" [useGrouping]="false" [min]="0" [max]="100" formControlName="parcela" [inputSize]="10" />
                        </div>
                    </div>
                    <div class="flex flex-col grow basis-0 gap-2">
                        Total das Parcelas:
                        <div>
                            <p-inputnumber mode="decimal" inputId="withoutgrouping" [useGrouping]="false" [min]="0" [max]="100" formControlName="totalParcela" [inputSize]="10" />
                        </div>
                    </div>
                </div>

                <div class="flex flex-col gap-2">
                    Valor:
                    <app-input-moeda-form [form]="cForm" fcn="valor" />
                </div>

                <p-divider align="center" type="solid">
                    <b>Pagamento</b>
                </p-divider>

                <div class="flex flex-col gap-2">Forma Pagamento: <app-select-forma-pagamento-form [form]="cForm" scrHeight="600px" /></div>

                <div class="flex flex-wrap gap-6">
                    <div class="flex flex-col grow basis-0 gap-2">Data Pagamento: <app-input-data-form [form]="cForm" placeh="Data Pagamento" [req]="true" fcn="dataPagamento" /></div>
                    <div class="flex flex-col grow basis-0 gap-2">Valor Pago:<app-input-moeda-form [form]="cForm" fcn="valorPago" /></div>
                </div>

                <p-divider align="center" type="solid" />

                <div class="flex flex-col gap-2">Observação:<textarea rows="3" cols="20" pTextarea [autoResize]="true" formControlName="observacao"></textarea></div>
                <p-button label="Salvar" severity="success" raised icon="pi pi-save" [disabled]="cForm.invalid" type="submit" />
            </div>
        </form>

        <p-toast />
    `,
    styles: ``
})
export class ContaForm implements OnInit {
    cForm: FormGroup;
    maskCnpj: string = '999999999999999999999999999999999999999999999999';
    uuidEdicao!: string;

    constructor(
        private fb: FormBuilder,
        private route: ActivatedRoute,
        private contaService: ContaService,
        private messageService: MessageService
    ) {
        this.cForm = this.fb.group({
            tipoConta: [null, Validators.required],
            codigoBarras: [null, Validators.required],
            vencimento: [null, [Validators.required, Validators.pattern(/^(0[1-9]|[12][0-9]|3[01])\/(0[1-9]|1[0-2])\/\d{4}$/)]],
            emissao: [null, [Validators.required, Validators.pattern(/^(0[1-9]|[12][0-9]|3[01])\/(0[1-9]|1[0-2])\/\d{4}$/)]],
            parcela: ['0', Validators.nullValidator],
            totalParcela: ['0', Validators.nullValidator],
            valor: [null, Validators.required],
            observacao: [null, Validators.nullValidator],
            dataPagamento: [null, Validators.nullValidator],
            formaPagamento: [null, Validators.nullValidator],
            valorPago: [null, Validators.nullValidator]
        });
    }

    ngOnInit(): void {
        this.route.queryParams.subscribe((params) => {
            if (params['uuid']) {
                this.carregar(params['uuid']);
            }
        });
    }

    carregar(uuid: string) {
        this.contaService.getContaByUuuid(uuid).subscribe((resp) => {
            this.uuidEdicao = resp.uuid;
            this.cForm.patchValue({
                tipoConta: resp.tipoConta,
                codigoBarras: resp.codigoBarras,
                emissao: Util.dateToDataBR(resp.emissao),
                vencimento: Util.dateToDataBR(resp.vencimento),
                parcela: resp.parcela,
                totalParcela: resp.totalParcela,
                valor: Util.formatFloatToReal(resp.valor),
                dataPagamento: Util.dateToDataBR(resp.dataPagamento + ''),
                valorPago: Util.formatFloatToReal(resp.valorPago),
                observacao: resp.observacao,
                formaPagamento: resp.formaPagamento
            });

        });
    }

    onSubmit() {
        if (this.cForm.valid) {
            const dados = this.cForm.getRawValue();

            dados.valor = dados.valor.replace(/\D/g, '') / 100;
            dados.emissao = Util.dataBRtoDataIso(dados.emissao);
            dados.vencimento = Util.dataBRtoDataIso(dados.vencimento);
            // Pagamento

            if (!dados.formaPagamento || !dados.dataPagamento || !dados.valorPago) {
                dados.dataPagamento = null;
                dados.valorPago = null;
                dados.formaPagamento = null;
            } else {
                if (dados.dataPagamento) dados.dataPagamento = Util.dataBRtoDataIso(dados.dataPagamento);
                if (dados.valorPago) dados.valorPago = dados.valorPago.replace(/\D/g, '') / 100;
            }

            if (this.uuidEdicao) dados.uuid = this.uuidEdicao;

            this.contaService.save(dados).subscribe({
                next: (res) => {
                    this.cForm.reset();
                    Util.showMsgSuccess(this.messageService, 'Sucesso!');
                },
                error: (err) => {
                    Util.showMsgErro(this.messageService, 'Erro ao salvar conta');
                }
            });
        }
    }

    isInvalid(controlName: string) {
        const control = this.cForm.get(controlName);
        return control?.invalid && (control.touched || this.cForm);
    }
}
