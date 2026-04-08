import { TipoContaService } from '@/pages/service/tipo-conta-service';
import { ImportsModule } from '@/util/imports';
import { Util } from '@/util/util';
import { Component, OnInit, ViewChild } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { MessageService } from 'primeng/api';
import { Table } from 'primeng/table';
import { Observable } from 'rxjs';

@Component({
    selector: 'app-tipo-conta',
    standalone: true,
    imports: [ImportsModule],
    providers: [MessageService],
    template: `
        <div class="grid grid-cols-12 gap-5">
            <div class="col-span lg:col-span-4">
                <div class="card">
                    <h3><i class="pi pi-fw pi-bookmark" style="font-size: 1.5rem"></i> Tipo Conta</h3>
                    <form [formGroup]="tcForm" (ngSubmit)="onSubmit()">
                        <div class="card flex flex-col gap-6 md:w-full">
                            @if (uuidSelected) {
                                <p-message severity="warn" icon="pi pi-bell" size="large">Modo Edição !</p-message>
                            }
                            <div class="flex flex-col gap-2">
                                <label>Nome : </label>
                                <input type="text" pInputText placeholder="Nome" formControlName="nome" [invalid]="isInvalid('nome')" />
                            </div>

                            <div class="flex gap-6 items-center text-xl">
                                <label> <i class="pi pi-credit-card text-blue-500"></i> Cartão de Credito : </label>
                                <p-toggleswitch formControlName="cartaoCredito" />
                            </div>

                            <div class="flex gap-6 items-center text-xl">
                                <label> <i class="pi pi-check text-primary"></i> Ativo : </label>
                                <p-toggleswitch formControlName="ativo" />
                            </div>
                            <div class="flex gap-6 items-center">
                                <p-button label="Salvar" severity="success" raised icon="pi pi-save" [disabled]="tcForm.invalid" type="submit" [style]="{ width: '110px' }" />
                                @if (uuidSelected) {
                                    <p-button label="Cancelar" severity="warn" raised icon="pi pi-times" [style]="{ width: '110px' }" />
                                }
                            </div>
                        </div>
                    </form>
                </div>
            </div>
            <div class="col-span lg:col-span-6">
                <div class="card">
                    <p-table [value]="(tiposConta$ | async) ?? []" [loading]="!(tiposConta$ | async)" scrollHeight="flex" id="tableTipoConta" size="small" sortField="modalidade.nome" showGridlines selectionMode="single" stripedRows #tableTipoConta>
                        <ng-template #header>
                            <tr>
                                <th class="backGreeen" pSortableColumn="nome">Nome <p-sortIcon field="nome" /></th>
                                <th class="backGreeen" pSortableColumn="cartaoCredito" [style]="{ width: '150px' }">Cartão de Crédito <p-sortIcon field="cartaoCredito" /></th>
                                <th class="backGreeen" pSortableColumn="ativo" [style]="{ width: '80px' }">Ativo <p-sortIcon field="ativo" /></th>
                                <th></th>
                            </tr>
                        </ng-template>
                        <ng-template #body let-tipo>
                            <tr [pSelectableRow]="tipo" [ngStyle]="rowStyle(tipo)">
                                <td>{{ tipo.nome }}</td>
                                <td style="text-align: center;">
                                    @if (tipo.cartaoCredito) {
                                        <i class="pi pi-fw pi-credit-card"></i>
                                    }
                                </td>
                                <td style="text-align: center;">
                                    @if (!tipo.ativo) {
                                        <i class="pi pi-fw pi-exclamation-triangle"></i>
                                    }
                                </td>
                                <td style="text-align: center;">
                                    <i class="pi pi-pencil text-primary" pTooltip="Editar" (click)="editar(tipo)"></i>
                                </td>
                            </tr>
                        </ng-template>
                    </p-table>
                    <p-toast />
                </div>
            </div>
        </div>
    `
})
export class TipoConta implements OnInit {
    tiposConta$!: Observable<any[]>;
    tcForm: FormGroup;
    uuidSelected: any;
    @ViewChild('tableTipoConta') tabela!: Table;

    constructor(
        private fb: FormBuilder,
        private tipoContaService: TipoContaService,
        private messageService: MessageService
    ) {
        this.tcForm = this.fb.group({
            nome: [null, Validators.required],
            cartaoCredito: [false, ''],
            ativo: [true, '']
        });
    }

    onSubmit() {
        if (this.tcForm.valid) {
            const dados = this.tcForm.getRawValue();
            if (this.uuidSelected) dados.uuid = this.uuidSelected;
            console.dir(dados);
            this.tipoContaService.save(dados).subscribe({
                next: (res) => {
                    this.tcForm.reset();
                    Util.showMsgSuccess(this.messageService, 'Sucesso!');
                    this.uuidSelected = null;
                    this.tabela._filter();
                },
                error: (err) => {
                    Util.showMsgErro(this.messageService, 'Erro ao salvar tipo conta');
                }
            });
        }
    }

    editar(tipo: any) {
        this.tcForm.patchValue(tipo);
        this.uuidSelected = tipo.uuid;
    }

    isInvalid(controlName: string) {
        const control = this.tcForm.get(controlName);
        return control?.invalid && (control.touched || this.tcForm);
    }

    ngOnInit(): void {
        this.tiposConta$ = this.tipoContaService.getTipos();
    }

    rowStyle(tipo: any) {
        return !tipo.ativo ? { fontWeight: 'bold', fontStyle: 'italic', color: 'red' } : {};
    }
}
