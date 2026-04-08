import { ImportsModule } from '@/util/imports';
import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

@Component({
    selector: 'app-conta',
    imports: [ImportsModule],
    template: `
        <div class="card">
            <h3><i class="pi pi-fw pi-barcode" style="font-size: 1.5rem"></i> Contas</h3>
            <p-tabs [value]="activeIndex">
                <p-tablist>
                    <p-tab [value]="0" [routerLink]="'/pages/contas/conta-list'" [queryParams]="{ tab: 0 }"> <i class="pi pi-list"></i> <span> Consulta</span> </p-tab>
                    <p-tab [value]="1" [routerLink]="'/pages/contas/conta-form'" [queryParams]="{ tab: 1 }"> <i class="pi pi-file-edit"></i> <span> Cadastro</span> </p-tab>
                    <p-tab [value]="2" [routerLink]="'/pages/contas/conta-chart'" [queryParams]="{ tab: 2 }"> <i class="pi pi-chart-bar"></i> <span> Gráficos</span> </p-tab>
                    <p-tab [value]="3" [routerLink]="'/pages/contas/tipo-conta'" [queryParams]="{ tab: 3 }"> <i class="pi pi-bookmark"></i> <span> Tipo Conta</span> </p-tab>
                </p-tablist>
            </p-tabs>
            <router-outlet />
        </div>
    `,
    styles: ``
})
export class Conta implements OnInit {
    activeIndex = 0;

    constructor(private route: ActivatedRoute) {}

    ngOnInit(): void {
        this.route.queryParamMap.subscribe((params) => {
            const tab = Number(params.get('tab'));
            this.activeIndex = isNaN(tab) ? 0 : tab;
        });
    }
}
