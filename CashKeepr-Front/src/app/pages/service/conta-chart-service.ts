import { map, Observable } from 'rxjs';
import { ContaService } from './conta-service';
import { Injectable } from '@angular/core';

@Injectable({
    providedIn: 'root'
})
export class ContaChartService {
    constructor(private contaService: ContaService) {}

    // getChartTotaisStatusConfig(): Observable<any> {
    //     const documentStyle = getComputedStyle(document.documentElement);
    //     return this.contaService.totaisStatus().pipe(
    //         map((response: any[]) => {
    //             return {
    //                 labels: response.map((item) => item.status),
    //                 datasets: [
    //                     {
    //                         backgroundColor: documentStyle.getPropertyValue('--p-cyan-500'),
    //                         borderColor: documentStyle.getPropertyValue('--p-cyan-500'),
    //                         data: response.map((item) => item.total)
    //                     }
    //                 ]
    //             };
    //         })
    //     );
    // }

    getChartByTipoConfig(): Observable<any> {
        const documentStyle = getComputedStyle(document.documentElement);
        return this.contaService.gastoTotalTipo().pipe(
            map((response: any[]) => {
                return {
                    labels: response.map((item) => item.tipoConta),
                    datasets: [
                        {
                            label: 'Gastos de Contas por Tipo de Conta',
                            backgroundColor: documentStyle.getPropertyValue('--p-orange-500'),
                            borderColor: documentStyle.getPropertyValue('--p-orange-500'),
                            data: response.map((item) => item.total)
                        }
                    ]
                };
            })
        );
    }

    // getChartOptionsVertical() {
    //     const documentStyle = getComputedStyle(document.documentElement);
    //     const textColor = documentStyle.getPropertyValue('--p-text-color');
    //     const textColorSecondary = documentStyle.getPropertyValue('--p-text-muted-color');
    //     const surfaceBorder = documentStyle.getPropertyValue('--p-content-border-color');
    //     return {
    //         indexAxis: 'y',
    //         maintainAspectRatio: false,
    //         aspectRatio: 0.8,
    //         plugins: {
    //             legend: {
    //                 labels: {
    //                     color: textColor
    //                 }
    //             }
    //         },
    //         scales: {
    //             x: {
    //                 ticks: {
    //                     color: textColorSecondary,
    //                     font: {
    //                         weight: 500
    //                     }
    //                 },
    //                 grid: {
    //                     color: surfaceBorder,
    //                     drawBorder: false
    //                 }
    //             },
    //             y: {
    //                 ticks: {
    //                     color: textColorSecondary
    //                 },
    //                 grid: {
    //                     color: surfaceBorder,
    //                     drawBorder: false
    //                 }
    //             }
    //         }
    //     };
    // }

    getChartOptions() {
        const documentStyle = getComputedStyle(document.documentElement);
        const textColor = documentStyle.getPropertyValue('--p-text-color');
        const textColorSecondary = documentStyle.getPropertyValue('--p-text-muted-color');
        const surfaceBorder = documentStyle.getPropertyValue('--p-content-border-color');

        return {
            maintainAspectRatio: false,
            aspectRatio: 0.8,
            plugins: {
                legend: {
                    labels: {
                        color: textColor
                    }
                }
            },
            scales: {
                x: {
                    ticks: {
                        color: textColorSecondary
                        // font: {
                        //   weight: 500
                        // }
                    },
                    grid: {
                        color: surfaceBorder,
                        drawBorder: false,                        
                    }
                },
                y: {
                    ticks: {
                        color: textColorSecondary
                    },
                    grid: {
                        color: surfaceBorder,
                        drawBorder: false
                    }
                }
            }
        };
    }
}
