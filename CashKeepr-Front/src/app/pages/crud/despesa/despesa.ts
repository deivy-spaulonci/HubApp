import { ImportsModule } from '@/util/imports';
import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

@Component({
    selector: 'app-despesa',
    standalone: true,
    imports: [ImportsModule],
    template: `<div class="card">
        <h3><i class="pi pi-fw pi-money-bill" style="font-size: 1.5rem"></i> Gestão Despesas</h3>
        <p-tabs [value]="activeIndex">
            <p-tablist onchange="update">
                <p-tab [value]="0" [routerLink]="'/pages/despesas/despesa-list'" [queryParams]="{ tab: 0 }"> <i class="pi pi-list"></i> <span> Consulta</span> </p-tab>
                <p-tab [value]="1" [routerLink]="'/pages/despesas/despesa-form'" [queryParams]="{ tab: 1 }"> <i class="pi pi-file-edit"></i> <span> Cadastro</span> </p-tab>
                <p-tab [value]="2" [routerLink]="'/pages/despesas/despesa-chart'" [queryParams]="{ tab: 2 }"> <i class="pi pi-chart-bar"></i> <span> Gráficos</span> </p-tab>
            </p-tablist>
        </p-tabs>
        <router-outlet />
    </div> `
})
export class Despesa implements OnInit {
    activeIndex = 0;

    constructor(private route: ActivatedRoute) {}

    ngOnInit(): void {
        this.update();
    }

    update(){
        this.route.queryParamMap.subscribe((params) => {
            const tab = Number(params.get('tab'));
            this.activeIndex = isNaN(tab) ? 0 : tab;
        });
    }
}
