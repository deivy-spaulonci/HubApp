import { Injectable } from '@angular/core';
import {Observable} from 'rxjs';
import { DefaultService } from './default-service';

@Injectable({
  providedIn: 'root',
})
export class TipoDespesaService {
  ROOT: string = "tipo-despesa";

  constructor(private defaultService: DefaultService) {}

  getTipos():Observable<[]>{
    return this.defaultService.get(this.ROOT);
  }

}
