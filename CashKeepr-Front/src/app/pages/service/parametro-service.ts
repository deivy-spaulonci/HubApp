import { Injectable } from '@angular/core';
import { DefaultService } from '@/pages/service/default-service';
import { Observable } from 'rxjs';

@Injectable({
    providedIn: 'root'
})
export class ParametroService {
    readonly ROOT: string = 'parametro';

    constructor(private defaultService: DefaultService) {}

    all(): Observable<any> {
        return this.defaultService.get(this.ROOT);
    }
}
