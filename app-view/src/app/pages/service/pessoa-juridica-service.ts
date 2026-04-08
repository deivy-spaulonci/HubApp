import {Injectable} from '@angular/core';
import {Observable, of} from 'rxjs';
import {catchError, map} from 'rxjs/operators';
import {MessageService} from 'primeng/api';
import { DefaultService } from './default-service';
import { Util } from '@/util/util';

@Injectable({
  providedIn: 'root',
})
export class PessoaJuridicaService {
  readonly ROOT: string = "pessoa-juridica";

  constructor(private defaultService: DefaultService) {
  }

  getPessoaJuridicaResponse(url:string, messageService:MessageService): Observable<any> {
    return this.defaultService.get(this.ROOT + url).pipe(
      map((res:any) => ({
        totalElements: res.totalElements ?? 0,
        content: res.content ?? [],
        size: res.pageable.pageSize ?? 20
      })),
      catchError(err => {
        Util.showMsgErro(messageService, 'Erro ao carregar pessoas juridicas');
        return of([]);
      }),
    );
  }

  getDespesaByFornecedorUuid(uuid: string): Observable<any> {
    return this.defaultService.get(`${this.ROOT}/despesasPorFornecedor?uuid=${uuid}`);
  }

  getService(): DefaultService {
    return this.defaultService;
  }

  save(pessoJuridica: any) {
    return this.defaultService.save(pessoJuridica, this.ROOT);
  }
}
