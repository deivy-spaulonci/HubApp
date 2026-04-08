import {Component, OnInit} from '@angular/core';
import {ImportsModule} from "@/util/imports";
import {ContaService} from "@/pages/service/conta-service";
import {MenuItem, MessageService} from "primeng/api";

@Component({
    standalone: true,
    selector: 'app-best-selling-widget',
    imports: [ImportsModule],
    providers: [MessageService],
    template: `
        <div class="card">
            <div class="flex justify-between items-center mb-6">
                <div class="font-semibold text-xl">Compromissos</div>
                <div>
                    <button pButton type="button" icon="pi pi-ellipsis-v"
                            class="p-button-rounded p-button-text p-button-plain"
                            (click)="menu.toggle($event)"></button>
                    <p-menu #menu [popup]="true" [model]="items"></p-menu>
                </div>
            </div>

            <p-datepicker class="max-w-full"
                          [(ngModel)]="date"
                          [inline]="true"
                          [showWeek]="true"
                          [numberOfMonths]="2">
                <ng-template #date let-date>
                    @if (checkAtrasado(date)) {
                        <strong><span class="text-red-500" [pTooltip]="date | json">{{ date.day }}</span></strong>
                    } @else if (checkAberto(date)){
                        <strong><span class="text-green-500" [pTooltip]="date | json">{{ date.day }}</span></strong>
                    } @else {
                        {{ date.day }}
                    }
                </ng-template>
            </p-datepicker>

            <ul class="list-none p-5 m-0">
                <li class="flex flex-col md:flex-row md:items-center md:justify-between mb-6">
                    <div>
                        <span class="text-surface-900 dark:text-surface-0 font-medium mr-2 mb-1 md:mb-0">Legenda</span>
                    </div>
                </li>
                <li class="flex flex-col md:flex-row md:items-center md:justify-between mb-3">
                    <div class="mt-2 md:mt-0 flex items-center text-yellow-500">
                        <i class="pi pi-bell"></i>&nbsp; Vencimento Hoje</div>
                    <div class="mt-2 md:mt-0 flex items-center text-green-500">
                        <i class="pi pi-calendar-clock"></i>&nbsp; Em Abertos</div>
                    <div class="mt-2 md:mt-0 flex items-center text-red-500">
                        <i class="pi pi-exclamation-circle"></i>&nbsp; Atrasados</div>
                </li>
            </ul>
        </div>`
})
export class BestSellingWidget implements OnInit {
    menu = null;
    date: Date[] | undefined;

    items: MenuItem[] | undefined;
    vencAbertos: string[] = [];
    vencAtrasad: string[] = [];
    vencHoje: string[] = [];

    constructor(private contaService: ContaService,
                private messageService: MessageService) {
        this.items = [
            {label: 'Add New', icon: 'pi pi-fw pi-plus'},
            {label: 'Remove', icon: 'pi pi-fw pi-trash'}
        ];
    }

    ngOnInit() {
        this.contaService.getListContasResponse('?contaStatus=ABERTO', this.messageService)
            .subscribe(res => {
                if (res && res.content) {
                    this.vencAbertos = res.content.map((item: any) => item.vencimento);
                }
            });

        this.contaService.getListContasResponse('?contaStatus=ATRASADO', this.messageService)
            .subscribe(res => {
                if (res && res.content) {
                    this.vencAtrasad = res.content.map((item: any) => item.vencimento);
                }
            });

        this.contaService.getListContasResponse('?contaStatus=HOJE', this.messageService)
            .subscribe(res => {
                if (res && res.content) {
                    this.vencHoje = res.content.map((item: any) => item.vencimento);
                }
            });

    }

    returnDateString(date: any): string {
        // 3. Formata a data do calendário para YYYY-MM-DD
        const ano = date.year;
        const mes = (date.month + 1).toString().padStart(2, '0');
        const dia = date.day.toString().padStart(2, '0');
        return `${ano}-${mes}-${dia}`;
    }

    checkAberto(date: any): boolean {
        if (!date) return false;
        return this.vencAbertos.includes(this.returnDateString(date));
    }

    checkAtrasado(date: any): boolean {
        if (!date) return false;
        return this.vencAtrasad.includes(this.returnDateString(date));
    }

    checkHoje(date: any): boolean {
        if (!date) return false;
        return this.vencHoje.includes(this.returnDateString(date));
    }

}
