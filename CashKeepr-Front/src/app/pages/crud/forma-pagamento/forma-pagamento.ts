import { FormaPagamentoService } from '@/pages/service/forma-pagamento-service';
import { ImportsModule } from '@/util/imports';
import { Component, OnInit } from '@angular/core';
import { MessageService } from 'primeng/api';
import { Observable } from 'rxjs';

@Component({
    selector: 'app-forma-pagamento',
    standalone: true,
    imports: [ImportsModule],
    providers: [MessageService],
    template: `
        <div class="grid grid-cols-12 gap-5">
            <div class="col-span-full lg:col-span-6">
                <div class="card">
                    <h3><i class="pi pi-fw pi-bookmark" style="font-size: 1.5rem"></i> Forma Pagamento</h3>
                    <p-table [value]="(formasPgto$ | async) ?? []" [loading]="!(formasPgto$ | async)" scrollHeight="flex" id="tableFormaPgto" size="small" sortField="modalidade.descricao" showGridlines selectionMode="single" stripedRows>
                        <ng-template #header>
                            <tr>
                                <th class="backGreeen" pSortableColumn="modalidade.descricao">Descrição <p-sortIcon field="modalidade.descricao" /></th>
                                <th class="backGreeen" pSortableColumn="instituicao">Instituição <p-sortIcon field="instituicao" /></th>
                            </tr>
                        </ng-template>
                        <ng-template #body let-forma>
                            <tr [pSelectableRow]="forma">
                                <td>{{ forma.modalidade.descricao }}</td>
                                <td>{{ forma.instituicao }}</td>
                            </tr>
                        </ng-template>
                    </p-table>
                    <p-toast />
                </div>
            </div>
            <div class="col-span-full lg:col-span-6">
                <div class="card"></div>
            </div>
        </div>
    `
})
export class FormaPagamento implements OnInit {
    formasPgto$!: Observable<any[]>;

    constructor(
        private formaPagamentoService: FormaPagamentoService,
        private messageService: MessageService
    ) {}

    ngOnInit(): void {
        this.formasPgto$ = this.formaPagamentoService.getFormasPagamento(this.messageService);
    }

}
