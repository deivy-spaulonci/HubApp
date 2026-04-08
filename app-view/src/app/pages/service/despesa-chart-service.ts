import {Injectable} from '@angular/core';
import {Observable} from 'rxjs';
import {map} from 'rxjs/operators';
import { DespesaService } from './despesa-service';

@Injectable({
  providedIn: 'root',
})
export class DespesaChartService {
  
  constructor(private despesaService: DespesaService) {
  }

  getChartByTipoConfig():Observable<any> {
    const documentStyle = getComputedStyle(document.documentElement);
    return this.despesaService.gastoTotalTipo().pipe(
      map((response:any[]) => {
        return {
          labels: response.map(item => item.tipo),
          datasets: [
            {
              label: 'Gastos de Despesas por Tipo de Despesa',
              backgroundColor: documentStyle.getPropertyValue('--p-orange-500'),
              borderColor: documentStyle.getPropertyValue('--p-orange-500'),
              data: response.map(item => item.total)
            }
          ],
        };
      }));
  }

  getChartByMonthConfig(ano:number):Observable<any> {
    const documentStyle = getComputedStyle(document.documentElement);
    return this.despesaService.gastoTotalMes(ano).pipe(
      map((response:any[]) => {
        return {
          labels: response.map(item => item.nomeMes.toUpperCase()),
          datasets: [
            {
              label: 'Gastos de Despesas por Mês',
              backgroundColor: documentStyle.getPropertyValue('--p-blue-500'),
              borderColor: documentStyle.getPropertyValue('--p-blue-500'),
              data: response.map(item => item.total)
            }
          ],
        };
      }));
  }

  // Função que retorna a configuração do gráfico
  getChartByYearConfig():Observable<any> {
    const documentStyle = getComputedStyle(document.documentElement);
    return this.despesaService.gastoTotalAnual().pipe(
      map((response:any[]) => {        
        return {
          labels: response.map(item => item.ano),
          datasets: [
            {
              label: 'Gastos de Despesas por Ano',
              backgroundColor: documentStyle.getPropertyValue('--p-green-500'),
              borderColor: documentStyle.getPropertyValue('--p-green-500'),
              data: response.map(item => item.total)
            }
          ],
        };
      }));
  }

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
            color: textColorSecondary,
            // font: {
            //   weight: 500
            // }
          },
          grid: {
            color: surfaceBorder,
            drawBorder: false
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
