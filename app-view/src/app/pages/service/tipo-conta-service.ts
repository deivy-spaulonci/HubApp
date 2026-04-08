import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { DefaultService } from './default-service';

@Injectable({
    providedIn: 'root'
})
export class TipoContaService {
    ROOT: string = 'tipo-conta';

    constructor(private defaultService: DefaultService) {}

    getTipos(): Observable<[]> {
        return this.defaultService.get(this.ROOT);
    }

    save(tipoConta: any) {
        if (tipoConta.uuid) return this.defaultService.update(tipoConta, this.ROOT);
        return this.defaultService.save(tipoConta, this.ROOT);
    }
}
