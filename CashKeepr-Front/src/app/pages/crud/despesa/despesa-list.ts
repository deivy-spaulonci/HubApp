import { Component, OnInit, ViewChild } from '@angular/core';
import { ImportsModule } from '@/util/imports';
import { DespesaService } from '@/pages/service/despesa-service';
import { Observable } from 'rxjs';
import { Table, TableLazyLoadEvent } from 'primeng/table';
import { ConfirmationService, MenuItem, MessageService } from 'primeng/api';
import { Util } from '@/util/util';

@Component({
    selector: 'app-despesa-list',
    standalone: true,
    imports: [ImportsModule],
    providers: [MessageService, ConfirmationService],
    template: `
        <div class="card">
            <div class="grid grid-cols-12">
                <div class="lg:col-span-5">
                    Registros:
                    <div class="text-primary text-3xl font-bold">{{ (despesaResp$ | async)?.totalElements }}</div>
                </div>
                <div class="lg:col-span-5">
                    Total:
                    <div class="text-primary text-3xl font-bold">{{ valorTotal$ | async | currency }}</div>
                </div>
                <div class="lg:col-span-1">
                    <p-button icon="pi pi-filter" label="Filtros" size="large" severity="info" (click)="filtros = true" />
                </div>
                <div class="lg:col-span-1">
                    <p-button icon="pi pi-filter-slash" label="Limpar" size="large" severity="secondary" (click)="clearFilter()" />
                </div>
            </div>

            <p-contextmenu #cm [model]="items" (onHide)="selected = null" />
            <p-table
                #tableDespesa
                [value]="(despesaResp$ | async)?.content"
                [loading]="!(despesaResp$ | async)?.content"
                [lazy]="true"
                scrollHeight="flex"
                dataKey="uuid"
                [sortField]="sortField"
                editMode="row"
                [totalRecords]="(despesaResp$ | async)?.totalElements"
                [sortOrder]="-1"
                [paginator]="true"
                [loading]="loading"
                [rowHover]="true"
                [rows]="(despesaResp$ | async)?.size"
                [rowsPerPageOptions]="[10, 20, 30, 40]"
                (onLazyLoad)="loadData($event)"
                selectionMode="single"
                [(selection)]="selected"
                [(contextMenuSelection)]="selected"
                paginatorPosition="top"
                [contextMenu]="cm"
                currentPageReportTemplate="Showing {first} to {last} of {totalRecords} entries"
                showGridlines
                id="tableDespesa"
                stripedRows
            >
                <ng-template #header>
                    <tr>
                        <th pSortableColumn="tipoDespesa">Tipo <p-sortIcon field="tipoDespesa" /></th>
                        <th pSortableColumn="fornecedor.nome">Fornecedor <p-sortIcon field="fornecedor.nome" /></th>
                        <th pSortableColumn="dataPagamento">Data <p-sortIcon field="dataPagamento" /></th>
                        <th pSortableColumn="formaPagamento.modalidade">Forma Pgamento <p-sortIcon field="formaPagamento" /></th>
                        <th pSortableColumn="valor" style="width: 140px;">Valor <p-sortIcon field="valor" /></th>
                        <th [width]="40"></th>
                        <th [width]="40"></th>
                        <th [width]="40"></th>
                    </tr>
                </ng-template>
                <ng-template #body let-despesa let-expanded="expanded" let-ri="rowIndex" let-editing="editing">
                    <tr [pSelectableRow]="despesa" [pContextMenuRow]="despesa" [pEditableRow]="despesa">
                        <td>
                            <p-cellEditor>
                                <ng-template #input>
                                    <app-select-tipo-despesa [value]="despesa.tipoDespesa" (valueChange)="despesa.tipoDespesa = $event.value" />
                                </ng-template>
                                <ng-template #output>{{ despesa.tipoDespesa.nome }}</ng-template>
                            </p-cellEditor>
                        </td>
                        <td>
                            <p-cellEditor>
                                <ng-template #input>
                                    <app-autocomplete-fornecedor [value]="despesa.fornecedor" (valueChange)="despesa.fornecedor = $event.value" />
                                </ng-template>
                                <ng-template #output>
                                    <div [pTooltip]="despesa.fornecedor.razaoSocial" tooltipPosition="top">{{ despesa.fornecedor.nome }}</div>
                                </ng-template>
                            </p-cellEditor>
                        </td>
                        <td style="text-align: center;">
                            <p-cellEditor>
                                <ng-template #input>
                                    <app-input-data [value]="despesa.dataPagamento" (valueChange)="despesa.dataPagamento = $event" />
                                </ng-template>
                                <ng-template #output>{{ despesa.dataPagamento | date: 'dd/MM/yyyy' }}</ng-template>
                            </p-cellEditor>
                        </td>
                        <td>
                            <p-cellEditor>
                                <ng-template #input>
                                    <app-select-forma-pagamento [value]="despesa.formaPagamento" (valueChange)="despesa.formaPagamento = $event.value" scrHeight="500px" />
                                </ng-template>
                                <ng-template #output>
                                    {{ despesa.formaPagamento.modalidade.descricao + ' - ' + (despesa.formaPagamento.instituicao ?? '') }}
                                </ng-template>
                            </p-cellEditor>
                        </td>
                        <td style="text-align: right;">
                            <p-cellEditor>
                                <ng-template #input>
                                    <app-input-moeda [value]="despesa.valor" (valueChange)="despesa.valor = $event" />
                                </ng-template>
                                <ng-template #output>{{ despesa.valor | currency }}</ng-template>
                            </p-cellEditor>
                        </td>
                        <td>
                            <p-cellEditor>
                                <ng-template #input>
                                    <i class="pi pi-exclamation-circle" style="color: blue; font-size: 1.0rem;" (click)="op.toggle($event)"></i>
                                    <p-popover #op>
                                        <div class="flex flex-col gap-4">
                                            Observação:<br />
                                            <textarea rows="5" cols="30" pTextarea [autoResize]="true" [(ngModel)]="despesa.observacao"></textarea>
                                        </div>
                                    </p-popover>
                                </ng-template>
                                <ng-template #output>
                                    @if (despesa.observacao) {
                                        <i class="pi pi-exclamation-circle" style="color: blue; font-size: 1.0rem;" (click)="op.toggle($event)"></i>
                                    }
                                    <p-popover #op>
                                        <div class="flex flex-col gap-4">{{ despesa.observacao }}</div>
                                    </p-popover>
                                </ng-template>
                            </p-cellEditor>
                        </td>
                        <td>
                            <div class="flex items-center justify-center gap-2">
                                @if (!editing) {
                                    <app-bt-initeditcolumn (acao)="onInitEdit(despesa)" />
                                } @else {
                                    <app-bt-saveeditcolumn (acao)="onSaveEdit(despesa)" />
                                    <app-bt-canceleditcolumn (acao)="onRowEditCancel(despesa, ri)" />
                                }
                            </div>
                        </td>
                        <td class="text-red-600">
                            <i class="pi pi-trash iconDelet" (click)="deleteDespesa(despesa)"></i>
                        </td>
                    </tr>
                </ng-template>
            </p-table>
        </div>

        <p-drawer header="Filtros" [(visible)]="filtros" position="right" [style]="{ width: '600px' }">
            <div style="row-gap: 25px; padding-top: 25px; display:grid;">
                <app-select-tipo-despesa [showC]="true" [value]="tipoSelected" (valueChange)="tipoSelected = $event.value" />
                <app-autocomplete-fornecedor [value]="fornecedorSelected" (valueChange)="fornecedorSelected = $event.value" />
                <app-select-forma-pagamento [showC]="true" [value]="formaSelected" (valueChange)="formaSelected = $event.value" />
                <p-datepicker  [showWeek]="true" [(ngModel)]="rangeDates" selectionMode="range" dateFormat="dd/mm/yy" [numberOfMonths]="2" class="w-6/12"></p-datepicker>
                <p-button icon="pi pi-filter" label="Filtrar" [style]="{ width: '120px' }" [raised]="true" severity="success" size="large" (click)="tableDespesa._filter()" />
            </div>
        </p-drawer>

        <p-toast />
        <p-confirmdialog />
    `
})
export class DespesaList implements OnInit {
    loading: boolean = true;
    despesaResp$!: Observable<any>;
    valorTotal$!: Observable<any>;
    sortField: string = 'dataPagamento';
    selected!: any;
    tipoSelected!: any;
    fornecedorSelected!: any;
    formaSelected!: any;
    filtros: boolean = false;
    @ViewChild('tableDespesa') tabela!: Table;
    items!: MenuItem[];
    clonedDespesas: { [s: string]: any } = {};
    rangeDates: Date[] | undefined;

    constructor(
        private despesaService: DespesaService,
        private confirmationService: ConfirmationService,
        private messageService: MessageService
    ) {}

    ngOnInit(): void {
        this.items = [{ label: 'Excluir', icon: 'pi pi-fw pi-times', command: () => this.deleteDespesa(this.selected) }];
    }

    onInitEdit(despesa: any) {
        despesa.dataPagamento = Util.dateToDataBR(despesa.dataPagamento);
        this.clonedDespesas[despesa.uuid] = { ...despesa };
    }

    onRowEditCancel(despesa: any, index: number) {
        Object.keys(this.clonedDespesas[despesa.uuid]).forEach((key) => {
            despesa[key] = this.clonedDespesas[despesa.uuid][key];
        });
        despesa.dataPagamento = Util.dataStringBrToDateString(despesa.dataPagamento);
        delete this.clonedDespesas[despesa.uuid as string];
    }

    onSaveEdit(despesa: any) {
        if (typeof despesa.valor !== 'number') {
            var valorSemFormato = despesa.valor.replace(/\D/g, '') / 100;
            despesa.valor = valorSemFormato;
        }
        despesa.dataPagamento = Util.dataStringBrToDateString(despesa.dataPagamento);

        this.despesaService.update(despesa).subscribe({
            next: (res) => Util.showMsgSuccess(this.messageService, 'Alterado Sucesso!'),
            error: (err) => Util.showMsgErro(this.messageService, 'Erro ao salvar despesa')
        });
    }

    loadData(event: TableLazyLoadEvent) {
        const pageSize = event.rows ?? 20;
        const sortField = event.sortField ?? 'dataPagamento';
        const sortOrder = event.sortOrder === 1 ? 'asc' : 'desc';
        // garante que page nunca vira NaN
        const page = event.first ? event.first / pageSize : 0;
        let filtro = '';

        if (this.tipoSelected) filtro += `&tipoDespesa=${this.tipoSelected.value}`;
        if (this.fornecedorSelected) filtro += `&fornecedor.uuid=${this.fornecedorSelected.uuid}`;
        if (this.rangeDates?.[0]) filtro += `&inicio=${Util.dataBRtoDataIso(this.rangeDates?.[0].toLocaleDateString('pt-BR') + '')}`;
        if (this.rangeDates?.[1]) filtro += `&termino=${Util.dataBRtoDataIso(this.rangeDates?.[1].toLocaleDateString('pt-BR') + '')}`;
        if (this.formaSelected) filtro += `&formaPagamento.uuid=${this.formaSelected.uuid}`;

        const url = `?page=${page}&size=${pageSize}&sort=${sortField},${sortOrder}` + filtro;
        this.loadDespesas(url);
    }

    loadDespesas(url: string) {
        this.despesaResp$ = this.despesaService.getDespesasResponse(url, this.messageService);
        this.valorTotal$ = this.despesaService.getValorTotal(url);
    }

    clearFilter() {
        this.tipoSelected = null;
        this.fornecedorSelected = null;
        this.rangeDates = [];
        this.formaSelected = null;
        this.tabela._filter();
    }

    deleteDespesa(despesa: any) {
        this.confirmationService.confirm({
            message: 'Tem certeza excluir despesa?',
            header: 'Confirmação',
            closable: true,
            closeOnEscape: true,
            icon: 'pi pi-exclamation-triangle',
            rejectButtonProps: {
                label: 'Cancelar',
                severity: 'secondary',
                outlined: true
            },
            acceptButtonProps: { label: 'Excluir' },
            accept: () => {
                this.despesaService.delete(despesa.uuid);
                this.messageService.add({ severity: 'info', summary: 'Confirmado', detail: 'Despesa excluída!' });
            },
            reject: () => {
                this.messageService.add({ severity: 'error', summary: 'Cancelado', detail: 'Ação cancelado!' });
            }
        });
    }
}
