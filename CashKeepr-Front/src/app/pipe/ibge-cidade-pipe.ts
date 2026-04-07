import { Pipe, PipeTransform } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable, of } from 'rxjs';
import { map, catchError } from 'rxjs/operators';

@Pipe({
  name: 'ibgeCidade',
  pure: true  // Pipe puro para evitar chamadas desnecessárias
})
export class IbgeCidadePipe implements PipeTransform {

  constructor(private http: HttpClient) {}

  transform(codigoIbge: number | string): Observable<string> {
    if (!codigoIbge) {
      return of('');
    }

    const url = `https://servicodados.ibge.gov.br/api/v1/localidades/municipios/${codigoIbge}`;

    return this.http.get<any>(url).pipe(
      map(res => res.nome || 'Desconhecido'),
      catchError(() => of('Cidade não encontrada'))
    );
  }

}
