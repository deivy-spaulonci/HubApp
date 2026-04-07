import { Injectable } from '@angular/core';
import {Observable, of} from 'rxjs';
import {catchError, map} from 'rxjs/operators';
import {MessageService} from 'primeng/api';
import { DefaultService } from './default-service';
import { FormaPagamento } from '@/model/forma-pagamento';
import { TipoFormaPagamento } from '@/model/tipo-forma-pagamento';
import { Util } from '@/util/util';

@Injectable({
  providedIn: 'root',
})
export class FormaPagamentoService {
  ROOT: string = "forma-pagamento";

  constructor(private defaultService: DefaultService) {}

  getFormasPagamento(messageService:MessageService): Observable<[]> {
    return this.defaultService.get(this.ROOT).pipe(      
      catchError(err => {
        Util.showMsgErro(messageService, 'Erro ao carregar formas de pagamento');
        return of([] as []);
      }),
    );
  }

  getTipos(): Observable<[]> {
    return this.defaultService.get(this.ROOT+'/tipos');
  }
}
