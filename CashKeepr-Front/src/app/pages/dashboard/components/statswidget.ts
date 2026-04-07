import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { DespesaService } from '@/pages/service/despesa-service';
import { map, Observable } from 'rxjs';
import { ContaService } from '@/pages/service/conta-service';
import { MessageService } from 'primeng/api';
import { Util } from '@/util/util';

@Component({
    standalone: true,
    selector: 'app-stats-widget',
    imports: [CommonModule],
    providers: [MessageService],
    template: `<div class="col-span-12 lg:col-span-6 xl:col-span-3">
            <div class="card mb-0">
                <div class="flex justify-between mb-4">
                    <div>
                        <span class="block text-muted-color font-medium mb-4">Despesas</span>
                        <div class="text-primary font-medium text-xl">{{ totalDespesaResp$ | async | currency }}</div>
                    </div>
                    <div class="flex items-center justify-center bg-blue-100 dark:bg-blue-400/10 rounded-border" style="width: 2.5rem; height: 2.5rem">
                        <i class="pi pi-money-bill text-blue-500 text-xl!"></i>
                    </div>
                </div>
                <span class="text-primary font-medium">{{ qtdDespesas }} </span>
                <span class="text-muted-color">despesas</span>
            </div>
        </div>
        <div class="col-span-12 lg:col-span-6 xl:col-span-3">
            <div class="card mb-0">
                <div class="flex justify-between mb-4">
                    <div>
                        <span class="block text-muted-color font-medium mb-4">Contas</span>
                        <div class="text-primary font-medium text-xl">{{ totalContaResp$ | async | currency }}</div>
                    </div>
                    <div class="flex items-center justify-center bg-orange-100 dark:bg-orange-400/10 rounded-border" style="width: 2.5rem; height: 2.5rem">
                        <i class="pi pi-barcode text-orange-500 text-xl!"></i>
                    </div>
                </div>
                <span class="text-primary font-medium">{{ qtdContas }} </span>
                <span class="text-muted-color"> contas</span>
            </div>
        </div>
        <div class="col-span-12 lg:col-span-6 xl:col-span-3">
            <div class="card mb-0">
                <div class="flex justify-between mb-4">
                    <div>
                        <span class="block text-muted-color font-medium mb-4">Total de Contas em Atraso</span>
                        <div class="text-red-500 dark:text-surface-0 font-medium text-xl">{{ (totalAtrasado$ | async | currency) ?? 0 }}</div>
                    </div>
                    <div class="flex items-center justify-center bg-red-100 dark:bg-red-400/10 rounded-border" style="width: 2.5rem; height: 2.5rem">
                        <i class="pi pi-exclamation-circle text-red-500 text-xl!"></i>
                    </div>
                </div>
                <span class="text-primary font-medium">{{ qtdAtrasado ?? 0 }} </span>
                <span class="text-muted-color">quantidade de registros</span>
            </div>
        </div>
        <div class="col-span-12 lg:col-span-6 xl:col-span-3">
            <div class="card mb-0">
                <div class="flex justify-between mb-4">
                    <div>
                        <span class="block text-muted-color font-medium mb-4">Total de Contas em Aberto</span>
                        <div class="text-blue-500 dark:text-surface-0 font-medium text-xl">{{ (totalEmAberto$ | async | currency) ?? 0 }}</div>
                    </div>
                    <div class="flex items-center justify-center bg-blue-100 dark:bg-blue-400/10 rounded-border" style="width: 2.5rem; height: 2.5rem">
                        <i class="pi pi-calendar-clock text-blue-500 text-xl!"></i>
                    </div>
                </div>
                <span class="text-primary font-medium">{{ qtdEmAberto ?? 0 }} </span>
                <span class="text-muted-color">quatidade de registros</span>
            </div>
        </div>`
})
export class StatsWidget implements OnInit {
    totalContaResp$!: Observable<any>;
    totalDespesaResp$!: Observable<any>;
    listAtrasado$!: Observable<any>;
    listAbertas$!: Observable<any>;
    totalAtrasado$!: Observable<any>;
    totalEmAberto$!: Observable<any>;
    qtdAtrasado!: any;
    qtdEmAberto!: any;
    qtdContas!: any;
    qtdDespesas!: any;

    constructor(
        private messageService: MessageService,
        private despesaService: DespesaService,
        private contaService: ContaService
    ) {}

    ngOnInit(): void {
        this.totalDespesaResp$ = this.despesaService.getValorTotal('');
        this.totalContaResp$ = this.contaService.getValorTotal('');
        this.listAtrasado$ = this.contaService.getListContasResponse('?contaStatus=ATRASADO', this.messageService);
        this.totalAtrasado$ = this.listAtrasado$.pipe(map((lista) => lista.content.reduce((acc: any, item: { valor: any }) => acc + item.valor, 0)));
        this.listAtrasado$.subscribe(item => this.qtdAtrasado = item.content.length);

        this.listAbertas$ = this.contaService.getListContasResponse('?contaStatus=ABERTO', this.messageService);
        this.totalEmAberto$ = this.listAbertas$.pipe(map((lista) => lista.content.reduce((acc: any, item: { valor: any }) => acc + item.valor, 0)));
        this.listAbertas$.subscribe(item => this.qtdEmAberto = item.content.length);

        this.contaService.count().subscribe(qtd => this.qtdContas = qtd);
        this.despesaService.count().subscribe(qtd => this.qtdDespesas = qtd);
    }
}
