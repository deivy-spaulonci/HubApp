import { Component } from '@angular/core';
import { RippleModule } from 'primeng/ripple';
import { TableModule } from 'primeng/table';
import { ButtonModule } from 'primeng/button';
import { CommonModule } from '@angular/common';
import { Product, ProductService } from '../../service/product.service';
import { ContaService } from '@/pages/service/conta-service';
import { Observable } from 'rxjs';
import { MessageService } from 'primeng/api';
import { ImportsModule } from '@/util/imports';

@Component({
    standalone: true,
    selector: 'app-recent-sales-widget',
    imports: [CommonModule, ImportsModule],
    providers: [MessageService],
    template: `<div class="card mb-8!">
        <div class="font-semibold text-xl mb-4">Contas em Aberto</div>
        <p-table [value]="(contaResp$ | async)?.content" [paginator]="true" [rows]="5">
            <ng-template #header>
                <tr>
                    <th>Conta</th>
                    <th>Vencimento</th>
                    <th>Valor</th>
                    <th>Status</th>
                </tr>
            </ng-template>
            <ng-template #body let-conta>
                <tr>
                    <td>{{ conta.tipoConta.nome }}</td>
                    <td style="text-align: center;">{{ conta.vencimento | date: 'dd/MM/yyyy' }}</td>
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
                </tr>
            </ng-template>
        </p-table>
    </div>`,
})
export class RecentSalesWidget {
    contaResp$!: Observable<any>;

    constructor(private contaService: ContaService,
        private messageService: MessageService
    ) {}

    ngOnInit() {
        this.contaResp$ = this.contaService.getListContasResponse('?contaStatus=ABERTO,ATRASADO,ABERTO', this.messageService);
    }
}
