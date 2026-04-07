import { PessoaFisicaService } from '@/pages/service/pessoa-fisica-service';
import { ImportsModule } from '@/util/imports';
import { Util } from '@/util/util';
import { Component, OnInit, ViewChild } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { MessageService } from 'primeng/api';
import { Table, TableLazyLoadEvent } from 'primeng/table';
import { Observable } from 'rxjs';

@Component({
    selector: 'app-pessoa-fisica',
    imports: [ImportsModule],
    providers: [MessageService],
    template: `
        <div class="grid grid-cols-12 gap-5">
            <div class="col-span-full lg:col-span-5">
                <div class="card">
                    <h3><i class="pi pi-fw pi-user" style="font-size: 1.5rem"></i> Pessoa Física</h3>
                    <form [formGroup]="pfForm" (ngSubmit)="onSubmit()">
                        <div class="card flex flex-col gap-4">
                            <div class="flex flex-col gap-2">
                                <label>Nome: </label>
                                <input type="text" pInputText placeholder="Nome" formControlName="nome" [invalid]="isInvalid('nome')" />
                                @if (isInvalid('nome')) {
                                    <p-message severity="error" size="small" variant="simple">Nome obrigatório!</p-message>
                                }
                            </div>
                            <div class="flex flex-col gap-2">
                                <label>CPF: </label>
                                <input type="text" pInputText placeholder="CPF" formControlName="cpf" [invalid]="isInvalid('cpf')" />
                                @if (isInvalid('cpf')) {
                                    <p-message severity="error" size="small" variant="simple">CPF obrigatório!</p-message>
                                }
                            </div>
                            <div class="flex flex-col gap-2 md:w-6/12">
                                <label>Estado: </label>
                                <p-select
                                    [options]="estadoResponse$ | async"
                                    optionLabel="sigla"
                                    optionValue="sigla"
                                    [(ngModel)]="estadoSelected"
                                    [ngModelOptions]="{ standalone: true }"
                                    (onChange)="changeEstadoSelected($event.value)"
                                    scrollHeight="500px"
                                    style="width: 50%"
                                    placeholder="Estado"
                                />
                            </div>
                            <div class="flex flex-col gap-2">
                                <label>Cidade: </label>
                                <p-select
                                    [options]="cidadeResponse$ | async"
                                    filter="true"
                                    optionLabel="nome"
                                    optionValue="id"
                                    scrollHeight="500px"
                                    style="width: 100%"
                                    formControlName="codigoIbge"
                                    placeholder="Cidades"
                                    [invalid]="isInvalid('codigoIbge')"
                                />
                                @if (isInvalid('codigoIbge')) {
                                    <p-message severity="error" size="small" variant="simple">Cidade obrigatória!</p-message>
                                }
                            </div>
                            <p-button label="Salvar" severity="success" raised icon="pi pi-save" [disabled]="pfForm.invalid" type="submit" />
                        </div>
                    </form>
                </div>
            </div>
            <div class="col-span-full lg:col-span-7">
                <div class="card">
                    <div style="display: grid; grid-template-columns: 1fr auto; gap: 10px;">
                        <input type="text" pInputText placeholder="busca..." [(ngModel)]="filtroTexto" (input)="tablePf._filter()" />
                        <p-button icon="pi pi-search" label="Pesquisar" [style]="{ width: '120px' }" [raised]="true" severity="success" (onClick)="tablePf._filter()" />
                    </div>
                    <p-table
                        #tablePf
                        [value]="(pessoaFisicaResponse$ | async)?.content"
                        [loading]="!(pessoaFisicaResponse$ | async)?.content"
                        [lazy]="true"
                        scrollHeight="flex"
                        size="small"
                        dataKey="uuid"
                        [sortField]="sortField"
                        [scrollable]="true"
                        [totalRecords]="(pessoaFisicaResponse$ | async)?.totalElements"
                        [sortOrder]="1"
                        [paginator]="true"
                        paginatorPosition="top"
                        [rows]="(pessoaFisicaResponse$ | async)?.size"
                        [rowsPerPageOptions]="[10, 20, 30]"
                        (onLazyLoad)="loadData($event)"
                        selectionMode="single"
                        [(selection)]="selected"
                        currentPageReportTemplate="Showing {first} to {last} of {totalRecords} entries"
                        showGridlines
                        stripedRows
                    >
                        <ng-template #header>
                            <tr>
                                <th pSortableColumn="nome">Nome<p-sortIcon field="nome" /></th>
                                <th pSortableColumn="cpf">CPF<p-sortIcon field="cpf" /></th>
                                <th>Cidade</th>
                            </tr>
                        </ng-template>
                        <ng-template #body let-pf>
                            <tr [pSelectableRow]="pf">
                                <td>{{ pf.nome }}</td>
                                <td>{{ pf.cpf }}</td>
                                <td>{{ pf.codigoIbge | ibgeCidade | async }}</td>
                            </tr>
                        </ng-template>
                    </p-table>
                </div>
            </div>
        </div>
        <p-toast />
    `,
    styles: ``
})
export class PessoaFisica implements OnInit {
    pessoaFisicaResponse$!: Observable<any>;
    sortField: string = 'nome';
    filtroTexto!: string;
    selected!: any;
    @ViewChild('tablePf') tabela!: Table;

    pfForm: FormGroup;
    estadoResponse$!: Observable<any>;
    estadoSelected: any = 'SP';
    cidadeResponse$!: Observable<any>;
    cidadeSelected!: any;

    constructor(
        private fb: FormBuilder,
        private pessoaFisicaService: PessoaFisicaService,
        private messageService: MessageService
    ) {
        this.pfForm = this.fb.group({
            nome: ['', Validators.required],
            cpf: ['', Validators.required],
            codigoIbge: ['', Validators.required]
        });
    }

    ngOnInit(): void {
        this.estadoResponse$ = this.pessoaFisicaService.getService().getEstados();
    }

    loadPessoas(url: string) {
        this.pessoaFisicaResponse$ = this.pessoaFisicaService.getPessoaResponse(url, this.messageService);
    }

    changeEstadoSelected(estadoSelected: any) {
        this.cidadeResponse$ = this.pessoaFisicaService.getService().getCidadesByUf(estadoSelected);
    }

    loadData(event: TableLazyLoadEvent) {
        const pageSize = event.rows ?? 30;
        const sortField = event.sortField ?? 'nome';
        const sortOrder = event.sortOrder === 1 ? 'asc' : 'desc';
        // garante que page nunca vira NaN
        const page = event.first ? event.first / pageSize : 0;
        const filtro = this.filtroTexto ?? '';
        const url = `?busca=${filtro}&page=${page}&size=${pageSize}&sort=${sortField},${sortOrder}`;
        this.loadPessoas(url);
    }

    onSubmit() {
        if (this.pfForm.valid) {
            const dados = this.pfForm.getRawValue();
            this.pessoaFisicaService.save(dados).subscribe({
                next: (res) => {
                    this.pfForm.reset();
                    Util.showMsgSuccess(this.messageService, 'Sucesso!');
                    this.tabela._filter();
                },
                error: (err) => {
                    Util.showMsgErro(this.messageService, 'Erro ao salvar pessoa física');
                }
            });
        }
    }

    isInvalid(controlName: string) {
        const control = this.pfForm.get(controlName);
        return control?.invalid && (control.touched || this.pfForm);
    }
}
