import { SelectFormaPagamento } from '@/layout/component/inputs/select-forma-pagamento';
import { ContaService } from '@/pages/service/conta-service';
import { ImportsModule } from '@/util/imports';
import { Util } from '@/util/util';
import { Component, OnInit, ViewChild } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { ConfirmationService, MenuItem, MessageService } from 'primeng/api';
import { Table, TableLazyLoadEvent } from 'primeng/table';
import { Observable } from 'rxjs';

@Component({
    selector: 'app-conta-list',
    imports: [ImportsModule],
    providers: [MessageService, ConfirmationService],
    template: `
        <div class="card">
            <div class="grid grid-cols-12">
                <div class="lg:col-span-5">
                    Registros:
                    <div class="text-primary text-3xl font-bold">{{ (contaResp$ | async)?.totalElements }}</div>
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
            <div class="grid grid-cols-12">
                <div class="lg:col-span-7">
                    <p-table
                        #tableConta
                        [value]="(contaResp$ | async)?.content"
                        [loading]="!(contaResp$ | async)?.content"
                        [lazy]="true"
                        scrollHeight="flex"
                        dataKey="uuid"
                        [sortField]="sortField"
                        editMode="row"
                        [totalRecords]="(contaResp$ | async)?.totalElements"
                        [sortOrder]="-1"
                        [paginator]="true"
                        [rowHover]="true"
                        [rows]="(contaResp$ | async)?.size"
                        [rowsPerPageOptions]="[10, 20, 30, 40]"
                        (onLazyLoad)="loadData($event)"
                        selectionMode="single"
                        [(selection)]="selected"
                        [(contextMenuSelection)]="selected"
                        paginatorPosition="top"
                        [contextMenu]="cm"
                        currentPageReportTemplate="Showing {first} to {last} of {totalRecords} entries"
                        showGridlines
                        id="tableConta"
                        stripedRows
                    >
                        <ng-template #header>
                            <tr>
                                <th pSortableColumn="tipoConta">Tipo <p-sortIcon field="tipoConta" /></th>
                                <th pSortableColumn="vencimento" [style]="{ width: '120px' }">Vencimento <p-sortIcon field="vencimento" /></th>
                                <th>Pacelas</th>
                                <th pSortableColumn="valor" [style]="{ width: '110px' }">Valor <p-sortIcon field="valor" /></th>
                                <th>Status</th>
                                <th [width]="40"></th>
                                <th [width]="40"></th>
                                <th [width]="40"></th>
                            </tr>
                        </ng-template>
                        <ng-template #body let-conta let-ri="rowIndex">
                            <tr [pSelectableRow]="conta" [pContextMenuRow]="conta" [ngStyle]="rowStyle(conta)">
                                <td>{{ conta.tipoConta.nome }}</td>
                                <td style="text-align: center;">{{ conta.vencimento | date: 'dd/MM/yyyy' }}</td>
                                <td style="text-align: center;">{{ conta.parcela + '/' + conta.totalParcela }}</td>
                                <td style="text-align: right;">{{ conta.valor | currency }}</td>
                                <td style="text-align: center;">
                                    @switch (conta.intStatus) {
                                        @case (1) {
                                            <p-message severity="info" icon="pi pi-calendar-clock" size="small">{{ conta.status }} </p-message>
                                        }
                                        @case (0) {
                                            <p-message severity="warn" icon="pi pi-bell" size="small">{{ conta.status }} </p-message>
                                        }
                                        @case (-1) {
                                            <p-message severity="error" icon="pi pi-exclamation-circle" size="small">{{ conta.status }} </p-message>
                                        }
                                        @case (2) {
                                            <p-message severity="success" icon="pi pi-check-square" size="small">{{ conta.status }} </p-message>
                                        }
                                    }
                                </td>
                                <td>
                                    <i class="pi pi-trash" pTooltip="Excluir" (click)="deleteConta(conta)" style="color: var(--p-red-600)"></i>
                                </td>
                                <td style="text-align: center;">
                                    @if (conta.intStatus != 2) {
                                        <i class="pi pi-check-square text-blue-600" pTooltip="Pagar Conta" (click)="pagarConta(conta)"></i>
                                    }
                                </td>
                                <td>
                                    <i class="pi pi-pencil text-primary" pTooltip="Editar" (click)="editar(conta)"></i>
                                </td>
                            </tr>
                        </ng-template>
                    </p-table>
                </div>
                <div class="lg:col-span-5">
                    @if (selected) {
                        <div class="card flex flex-col gap-6 w-full" [class.tipoContaInativa]="!selected.tipoConta.ativo">
                            <div class="font-semibold text-xl">Conta Selecionada</div>
                            @if (!selected.tipoConta.ativo) {
                                <span class="text-5xl text-red-600"> <i class="pi pi-exclamation-circle" style="font-size: 2.5rem;"></i> Encerrada ou Cancelada </span>
                            }

                            @switch (selected.intStatus) {
                                @case (1) {
                                    <p-message severity="info" icon="pi pi-calendar-clock" size="large">{{ selected.status }} </p-message>
                                }
                                @case (0) {
                                    <p-message severity="warn" icon="pi pi-bell" size="large">{{ selected.status }} </p-message>
                                }
                                @case (-1) {
                                    <p-message severity="error" icon="pi pi-exclamation-circle" size="large">{{ selected.status }} </p-message>
                                }
                                @case (2) {
                                    <p-message severity="success" icon="pi pi-check-square" size="large">{{ selected.status }} </p-message>
                                }
                            }

                            <div class="flex flex-col gap-3">
                                <div class="flex flex-wrap">Código de Barras :</div>
                                <div class="flex flex-wrap font-bold text-xl text-green-600 italic">
                                    {{ selected.codigoBarras }}
                                </div>
                                <div class="flex flex-wrap">Tipo Conta :</div>
                                <div class="flex flex-wrap font-bold text-xl text-green-600 italic">
                                    {{ selected.tipoConta.nome }}
                                </div>
                                <div class="flex flex-wrap gap-6">
                                    <div class="flex flex-col grow basis-0 gap-2">
                                        Emissão:
                                        <span class=" font-bold text-xl text-green-600 italic">
                                            {{ selected.emissao | date: 'dd/MM/yyyy' }}
                                        </span>
                                    </div>
                                    <div class="flex flex-col grow basis-0 gap-2">
                                        Vencimento:
                                        <span class=" font-bold text-xl text-green-600 italic">
                                            {{ selected.vencimento | date: 'dd/MM/yyyy' }}
                                        </span>
                                    </div>
                                </div>

                                <div class="flex flex-wrap gap-6">
                                    <div class="flex flex-col grow basis-0 gap-2">
                                        Parcelas:
                                        <span class=" font-bold text-xl text-green-600 italic">
                                            {{ selected.parcela + '/' + selected.totalParcela }}
                                        </span>
                                    </div>
                                    <div class="flex flex-col grow basis-0 gap-2">
                                        Valor:
                                        <span class=" font-bold text-xl text-green-600 italic">
                                            {{ selected.valor | currency }}
                                        </span>
                                    </div>
                                </div>

                                @if (selected.formaPagamento && selected.dataPagamento) {
                                    <p-divider align="center" type="solid">
                                        <b>Forma Pagamento</b>
                                    </p-divider>
                                    <div class="flex flex-wrap">Forma do Pagamento :</div>
                                    <div class="flex flex-wrap font-bold text-xl text-green-600 italic">
                                        {{ selected.formaPagamento.modalidade.descricao + (selected.formaPagamento.instituicao ? ' '+selected.formaPagamento.instituicao : '')}}
                                    </div>
                                    <div class="flex flex-wrap gap-6">
                                        <div class="flex flex-col grow basis-0 gap-2">
                                            Data Pagamento:
                                            <span class=" font-bold text-xl text-green-600 italic">
                                                {{ selected.dataPagamento | date: 'dd/MM/yyyy' }}
                                            </span>
                                        </div>
                                        <div class="flex flex-col grow basis-0 gap-2">
                                            Valor Pago:
                                            <span class=" font-bold text-xl text-green-600 italic">
                                                {{ selected.valorPago | currency }}
                                            </span>
                                        </div>
                                    </div>
                                }
                                @if (selected.observacao) {
                                    <p-divider align="center" type="solid">
                                        <b>Observação</b>
                                    </p-divider>
                                    <span class=" font-bold text-xl text-green-600 italic">
                                        {{ selected.observacao }}
                                    </span>
                                }
                            </div>
                        </div>
                    }
                </div>
            </div>
        </div>

        <p-drawer header="Filtros" [(visible)]="filtros" position="right" [style]="{ width: '600px' }">
            <div style="row-gap: 25px; padding-top: 25px; display:grid;">
                <app-select-tipo-conta [showC]="true" [value]="tipoSelected" (valueChange)="tipoSelected = $event.value" />

                <div class="font-semibold text-xl">Vencimento Período:</div>
                <p-datepicker [(ngModel)]="rangeDates" selectionMode="range" [readonlyInput]="true" dateFormat="dd/mm/yy" [numberOfMonths]="2" class="w-6/12" />

                <div class="font-semibold text-xl">Especificar Vencimento por Mês:</div>
                <p-datepicker [(ngModel)]="dateByMonth"
                              class="w-6/12"
                              view="month"
                              dateFormat="mm/yy"
                              [readonlyInput]="true" />

                <div class="font-semibold text-xl">Status</div>
                <div class="card flex">
                    <div class="flex flex-col gap-4">
                        <div class="flex items-center gap-3" *ngFor="let st of arrStatus">
                            <p-checkbox [inputId]="st.value" [value]="st.value" [(ngModel)]="statusSelected" size="large" />
                            <label for="st.value" class="ml-2 text-2xl">
                                <i [class]="st.icon" [style]="st.color" style="font-size: 1.5rem;"></i> {{ st.name }}
                            </label>
                        </div>
                    </div>
                </div>
                <p-button icon="pi pi-filter" label="Filtrar" [style]="{ width: '120px' }" [raised]="true" severity="success" size="large" (click)="tableConta._filter()" />
            </div>
        </p-drawer>

        <p-dialog header="Pagamento Conta." [modal]="true" [(visible)]="pgContaShow" [style]="{ width: '50rem' }">
            <div class="flex items-center gap-4 mb-8">
                <label for="username" class="font-semibold w-35">Forma Pagamento</label>
                <app-select-forma-pagamento [value]="contaPaga?.formaPagamento" (valueChange)="contaPaga.formaPagamento = $event.value" scrHeight="400px" style="width: 50%;" />
            </div>
            <div class="flex items-center gap-4 mb-8">
                <label for="email" class="font-semibold w-35">Data Pagamento</label>
                <app-input-data [value]="contaPaga?.dataPagamento" (valueChange)="contaPaga.dataPagamento = $event" />
            </div>
            <div class="flex items-center gap-4 mb-8">
                <label for="email" class="font-semibold w-35">Valor Pagamento</label>
                <app-input-moeda [value]="contaPaga?.valorPago" (valueChange)="contaPaga.valorPago = $event" />
            </div>

            <div class="flex justify-end gap-2">
                <p-button label="Cancel" severity="secondary" (click)="pgContaShow = false" />
                <p-button label="Save" (click)="savePagarConta()" />
            </div>
        </p-dialog>

        <p-toast />
        <p-confirmdialog />
    `,
    styles: [
        `
            .tipoContaInativa {
                background-color: var(--p-rose-100);
            }
        `
    ]
})
export class ContaList implements OnInit {
    contaResp$!: Observable<any>;
    valorTotal$!: Observable<any>;
    sortField: string = 'vencimento';
    selected!: any;
    @ViewChild('tableConta') tabela!: Table;
    items!: MenuItem[];
    filtros: boolean = false;
    tipoSelected!: any;
    statusSelected!: any;
    arrStatus!: any[];
    pgContaShow: boolean = false;
    contaPaga: any = {};

    rangeDates: Date[] | undefined;
    @ViewChild(SelectFormaPagamento) selectFormaPagamento!: SelectFormaPagamento;

    dateByMonth: Date | any;

    constructor(
        private confirmationService: ConfirmationService,

        private contaService: ContaService,
        private router: Router,
        private messageService: MessageService
    ) {}

    ngOnInit(): void {
        this.items = [{ label: 'Excluir', icon: 'pi pi-fw pi-times', command: () => this.deleteConta(this.selected) }];

        this.arrStatus = [
            { name: 'Pago', icon: 'pi pi-check-square', color: 'color: var(--p-green-600)', value: 'PAGO' },
            { name: 'Em Aberto', icon: 'pi pi-calendar-clock', color: 'color: var(--p-blue-600)', value: 'ABERTO' },
            { name: 'Vencimento Hoje', icon: 'pi pi-bell', color: 'color: var(--p-yellow-600)', value: 'HOJE' },
            { name: 'Atrasado', icon: 'pi pi-exclamation-circle', color: 'color: var(--p-red-600)', value: 'ATRASADO' }
        ];
    }

    loadData(event: TableLazyLoadEvent) {
        const pageSize = event.rows ?? 20;
        const sortField = event.sortField ?? 'vencimento';
        const sortOrder = event.sortOrder === 1 ? 'asc' : 'desc';
        // garante que page nunca vira NaN
        const page = event.first ? event.first / pageSize : 0;
        let filtro = '';

        if (this.statusSelected) filtro += `&contaStatus=${this.statusSelected}`;
        const intervalo = this.obterIntervaloMensal();
        if (intervalo) {
            filtro += `&vencimentoInicial=${intervalo.inicio}&vencimentoFinal=${intervalo.fim}`;
        } else{
            if (this.rangeDates?.[0]) filtro += `&vencimentoInicial=${Util.dataBRtoDataIso(this.rangeDates?.[0].toLocaleDateString('pt-BR') + '')}`;
            if (this.rangeDates?.[1]) filtro += `&vencimentoFinal=${Util.dataBRtoDataIso(this.rangeDates?.[1].toLocaleDateString('pt-BR') + '')}`;
        }
        if (this.tipoSelected) filtro += `&tipoConta.uuid=${this.tipoSelected.uuid}`;

        const url = `?page=${page}&size=${pageSize}&sort=${sortField},${sortOrder}` + filtro;
        this.loadContas(url);
    }

    loadContas(url: string) {
        this.contaResp$ = this.contaService.getContasResponse(url, this.messageService);
        this.valorTotal$ = this.contaService.getValorTotal(url);
    }

    obterIntervaloMensal() {
        // 1. Pega a data (trata se for array ou objeto único)
        const dataBase = Array.isArray(this.dateByMonth) ? this.dateByMonth[0] : this.dateByMonth;

        if (!dataBase) return;

        const ano = dataBase.getFullYear();
        const mes = dataBase.getMonth(); // 0-11 (Janeiro é 0)

        // 2. Primeiro dia: sempre dia 01
        const primeiroDiaStr = `${ano}-${(mes + 1).toString().padStart(2, '0')}-01`;

        // 3. Último dia: dia '0' do mês seguinte retorna o último do mês atual
        const ultimoObjeto = new Date(ano, mes + 1, 0);
        const ultimoDiaStr = `${ano}-${(mes + 1).toString().padStart(2, '0')}-${ultimoObjeto.getDate().toString().padStart(2, '0')}`;

        return { inicio: primeiroDiaStr, fim: ultimoDiaStr };
    }

    deleteConta(conta: any) {
        this.confirmationService.confirm({
            message: 'Tem certeza excluir conta?',
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
                this.contaService.deleteByUuuid(conta.uuid).subscribe({
                    next: (res) => {
                        Util.showMsgSuccess(this.messageService, 'Sucesso! conta excluída');
                        this.tabela._filter();
                    },
                    error: (err) => Util.showMsgErro(this.messageService, 'Erro ao excluir conta')
                });
            }
        });
    }

    clearFilter() {
        this.tipoSelected = null;
        this.rangeDates = [];
        this.dateByMonth = null;
        this.tabela._filter();
    }

    rowStyle(conta: any) {
        return conta.tipoConta.ativo ? { 'text-decoration': 'none' } : { 'text-decoration': 'line-through', color: 'red' };
    }

    editar(conta: any) {
        this.router.navigate(['/pages/contas/conta-form'], { queryParams: { uuid: conta.uuid, tab: 1 } });
    }

    async pagarConta(conta: any) {
        this.pgContaShow = true;
        this.contaPaga = { ...conta };
        this.contaPaga.dataPagamento = Util.dateToDataBR(conta.vencimento);
        this.contaPaga.valorPago = Util.maskMoeda(conta.valor + '');
        this.contaPaga.formaPagamento = await this.selectFormaPagamento.setSelected('7af343d1-598f-4750-b5da-749c4ed7b926');
    }

    savePagarConta() {
        if (['', undefined, null].includes(this.contaPaga.dataPagamento)) Util.showMsgErro(this.messageService, 'Data Pagamento inválida!');
        else if (['', undefined, null].includes(this.contaPaga.formaPagamento)) Util.showMsgErro(this.messageService, 'Forma Pagamento inválida!');
        else if (['', undefined, null].includes(this.contaPaga.valorPago)) Util.showMsgErro(this.messageService, 'Valor Pago inválido!');
        else {
            this.contaPaga.valorPago = this.contaPaga.valorPago.replace(/\D/g, '') / 100;
            this.contaPaga.dataPagamento = Util.dataBRtoDataIso(this.contaPaga.dataPagamento);

            this.contaService.save(this.contaPaga).subscribe({
                next: (res) => {
                    Util.showMsgSuccess(this.messageService, 'Sucesso!');
                    this.tabela._filter();
                },
                error: (err) => Util.showMsgErro(this.messageService, 'Erro ao salvar conta')
            });
            this.pgContaShow = false;
        }
    }
}
