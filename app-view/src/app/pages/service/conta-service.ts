import { Injectable } from '@angular/core';
import { DefaultService } from './default-service';
import { catchError, map, Observable, of } from 'rxjs';
import { Util } from '@/util/util';
import { MessageService } from 'primeng/api';

@Injectable({
    providedIn: 'root'
})
export class ContaService {
    readonly ROOT: string = 'conta';

    constructor(private defaultService: DefaultService) {}

    getContasResponse(url: string, messageService: MessageService): Observable<any> {
        return this.defaultService.get(this.ROOT + url).pipe(
            map((res) => ({
                totalElements: res.totalElements ?? 0,
                content: res.content ?? [],
                size: res.pageable.pageSize ?? 20
            })),
            catchError((err) => {
                Util.showMsgErro(messageService, 'Erro ao carregar contas');
                return of([]);
            })
        );
    }

    getListContasResponse(url: string, messageService: MessageService): Observable<any> {
        return this.defaultService.get(this.ROOT + '/list' + url).pipe(
            map((res) => ({
                content: res ?? []
            })),
            catchError((err) => {
                Util.showMsgErro(messageService, 'Erro ao carregar contas');
                return of([]);
            })
        );
    }

    delete(uuid: string) {
        return this.defaultService.deleteByUuid(uuid, this.ROOT);
    }

    save(conta: any) {
        if (conta.uuid) return this.defaultService.update(conta, this.ROOT);
        return this.defaultService.save(conta, this.ROOT);
    }

    deleteByUuuid(uuid: string) {
        return this.defaultService.deleteByUuid(uuid, this.ROOT);
    }

    getValorTotal(url: string): Observable<any> {
        return this.defaultService.get(this.ROOT + '/valorTotal' + url);
    }

    count(): Observable<any> {
        return this.defaultService.get(this.ROOT + '/count');
    }

    gastoTotalTipo(): Observable<[]> {
        return this.defaultService.get(this.ROOT + '/totalPorTipo');
    }

    totaisStatus(): Observable<[]> {
        return this.defaultService.get(this.ROOT + '/totaisStatus');
    }

    getContaByUuuid(uuid: string): Observable<any> {
        return this.defaultService.get(this.ROOT + '/uuid?uuid=' + uuid);
    }
}
