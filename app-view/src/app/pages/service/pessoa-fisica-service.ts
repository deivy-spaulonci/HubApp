import { Injectable } from '@angular/core';
import {Observable, of} from 'rxjs';
import {MessageService} from 'primeng/api';
import {catchError, map} from 'rxjs/operators';
import { Util } from '@/util/util';
import { DefaultService } from './default-service';


@Injectable({
  providedIn: 'root',
})
export class PessoaFisicaService {
  readonly ROOT: string = "pessoa-fisica";

  constructor(private defaultService: DefaultService) {
  }

  getPessoaResponse(url:string, messageService:MessageService): Observable<any> {
    return this.defaultService.get(this.ROOT + url)
      .pipe(
        map(res => ({
          totalElements: res.totalElements ?? 0,
          content: res.content ?? [],
          size: res.pageable.pageSize ?? 20
        })),
        catchError(err => {
          Util.showMsgErro(messageService, 'Erro ao carregar pessoas físicas');
          return of([]);
        }),
      );
  }

  getService(): DefaultService {
    return this.defaultService;
  }

  save(pessoFisica: any) {
    return this.defaultService.save(pessoFisica, this.ROOT);
  }
}
