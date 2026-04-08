import { Injectable } from '@angular/core';
import { Observable, of } from 'rxjs';
import { catchError, map } from 'rxjs/operators';
import { MessageService } from 'primeng/api';
import { DefaultService } from './default-service';
import { Util } from '@/util/util';

@Injectable({
    providedIn: 'root'
})
export class DespesaService {
    readonly ROOT: string = 'despesa';

    constructor(private defaultService: DefaultService) {}

    gastoTotalAnual(): Observable<[]> {
        return this.defaultService.get(this.ROOT + '/totalPorAno');
    }

    gastoTotalTipo(): Observable<[]> {
        return this.defaultService.get(this.ROOT + '/totalPorTipo');
    }

    gastoTotalMes(ano: number): Observable<[]> {
        return this.defaultService.get(this.ROOT + '/totalPorMes?ano=' + ano);
    }

    getValorTotal(url: string): Observable<any> {
        return this.defaultService.get(this.ROOT + '/valorTotal' + url);
    }

    count(): Observable<any> {
        return this.defaultService.get(this.ROOT + '/count');
    }

    getDespesasResponse(url: string, messageService: MessageService): Observable<any> {
        return this.defaultService.get(this.ROOT + url).pipe(
            map((res) => ({
                totalElements: res.totalElements ?? 0,
                content: res.content ?? [],
                size: res.pageable.pageSize ?? 20
            })),
            catchError((err) => {
                Util.showMsgErro(messageService, 'Erro ao carregar despesas');
                return of([]);
            })
        );
    }

    save(despesa: any) {
        return this.defaultService.save(despesa, this.ROOT);
    }

    update(despesa: any) {
        return this.defaultService.update(despesa, this.ROOT);
    }

    delete(uuid: string) {
        return this.defaultService.deleteByUuid(uuid,this.ROOT)
    }
}
