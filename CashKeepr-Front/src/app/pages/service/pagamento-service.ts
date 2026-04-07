import {Injectable} from "@angular/core";
import {DefaultService} from "@/pages/service/default-service";
import {MessageService} from "primeng/api";
import {catchError, Observable, of, throwError} from "rxjs";
import {Util} from "@/util/util";
import {map} from "rxjs/operators";

@Injectable({
    providedIn: 'root'
})
export class PagamentoService {
    readonly ROOT: string = 'pagamentos';

    constructor(private defaultService: DefaultService) {
    }

    getPastaPagamentos(messageService: MessageService): Observable<any> {
        return this.defaultService.get(this.ROOT + '/pasta').pipe(
            catchError((err) => {
                Util.showMsgErro(messageService, 'Erro ao carregar a pasta de arquivos de pagamento');
                return of([]);
            })
        );
    }

    getUrlVisualizacao(pathCompleto: string, messageService: MessageService): Observable<string> {
        return this.defaultService.getBlob(this.ROOT + '/visualizar', {pathCompleto: pathCompleto})
            .pipe(
                catchError((err) => {
                    Util.showMsgErro(messageService, 'Erro ao carregar arquivos de pagamento');
                    return of('Console error: Erro ao carregar arquivos de pagamento');
                })
            );
    }

}
