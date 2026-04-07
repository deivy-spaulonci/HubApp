import {Injectable} from "@angular/core";
import {DefaultService} from "@/pages/service/default-service";
import {Observable} from "rxjs";

@Injectable({
    providedIn: 'root'
})
export class ArquivoService {
    readonly ROOT: string = 'arquivo';

    constructor(private defaultService: DefaultService) {
    }

    renomearArquivo(pathCompleto: string, novoNome: string): Observable<string> {
        const params = { pathCompleto, novoNome };
        alert(JSON.stringify(params));
        const url = this.defaultService.ROOT+this.ROOT+'/renomear';
        return this.defaultService.getHttp().put(url, null, {
            params,
            responseType: 'text'
        });
    }
}
