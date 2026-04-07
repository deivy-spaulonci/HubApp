import { ContaChartService } from '@/pages/service/conta-chart-service';
import { ContaService } from '@/pages/service/conta-service';
import { ImportsModule } from '@/util/imports';
import { Component, OnInit } from '@angular/core';
import { Observable } from 'rxjs';

@Component({
    selector: 'app-conta-chart',
    standalone: true,
    imports: [ImportsModule],
    template: `
        <div class="card">
            <p-chart type="bar" [data]="dataChartTipo$ | async" [options]="optionsChart" [style]="{height:'600px'}"/>
        </div>
    `,
    styles: ``
})
export class ContaChart implements OnInit {
    dataChartTipo$!: Observable<any>;    
    optionsChart: any;

    constructor(private contaChartService: ContaChartService) {}

    ngOnInit(): void {
        this.dataChartTipo$ = this.contaChartService.getChartByTipoConfig();
        this.optionsChart = this.contaChartService.getChartOptions();
    }
}
