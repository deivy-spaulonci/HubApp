import { DespesaChartService } from '@/pages/service/despesa-chart-service';
import { ImportsModule } from '@/util/imports';
import { Component, OnInit } from '@angular/core';
import { Observable } from 'rxjs';

@Component({
    selector: 'app-despesa-chart',
    standalone: true,
    imports: [ImportsModule],
    template: `<div class="card">
            <p-chart type="bar" [data]="dataChartYear$ | async" [options]="optionsChart" />
        </div>
        <div class="card">
            Ano: <p-select placeholder="Selecione o ano" [options]="(dataChartYear$ | async)?.labels" [(ngModel)]="ano" (onChange)="updateYear($event)" />
            <p-chart type="bar" [data]="dataChartMonth$ | async" [options]="optionsChart" />
        </div>
        <div class="card">
            <p-chart type="bar" [data]="dataChartTipo$ | async" [options]="optionsChart" />
        </div> `
})
export class DespesaChart implements OnInit {
    dataChartYear$!: Observable<any>;
    dataChartMonth$!: Observable<any>;
    dataChartTipo$!: Observable<any>;
    optionsChart: any;
    ano: number = 2025;

    constructor(private despesaChartService: DespesaChartService) {}

    ngOnInit(): void {
        this.dataChartYear$ = this.despesaChartService.getChartByYearConfig();
        this.dataChartMonth$ = this.despesaChartService.getChartByMonthConfig(this.ano);
        this.dataChartTipo$ = this.despesaChartService.getChartByTipoConfig();
        this.optionsChart = this.despesaChartService.getChartOptions();
    }

    updateYear(event: any) {
        console.log(this.ano);
        this.dataChartMonth$ = this.despesaChartService.getChartByMonthConfig(this.ano);
    }
}
