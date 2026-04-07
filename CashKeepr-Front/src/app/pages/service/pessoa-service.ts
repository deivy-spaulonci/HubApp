import { Injectable } from '@angular/core';
import {Observable} from 'rxjs';
import { DefaultService } from './default-service';

@Injectable({
  providedIn: 'root',
})
export class PessoaService {
  readonly ROOT: string = "pessoa"

  constructor(private defaultService: DefaultService) {
  }
  get(url: string): Observable<any> {
    return this.defaultService.get(this.ROOT+'?busca='+url);
  }
}
