import { Injectable } from '@angular/core';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import {catchError, Observable, throwError} from 'rxjs';
import {map} from 'rxjs/operators';

@Injectable({
    providedIn: 'root'
})
export class DefaultService {
    readonly ROOT: string = 'http://localhost:8081/api/v1/';
    readonly RECEITA_CLOUD: string = 'https://publica.cnpj.ws/cnpj/';

    constructor(private http: HttpClient) {}

    httpOptions = {
        headers: new HttpHeaders({ 'Content-Type': 'application/json' })
    };

    getHttp() {
        return this.http;
    }

    get(api: string): Observable<any> {
        console.log(`default service ${this.ROOT}${api}`);
        return this.http.get<any>(`${this.ROOT}${api}`, this.httpOptions).pipe(map((res) => res));
    }

    getBlob(api: string, parametro: {}): Observable<string> {
        const url = `${this.ROOT}${api}`;
        return this.http
            .get(url, {
                params: parametro,
                responseType: 'blob'
            })
            .pipe(
                // Converte o arquivo baixado em uma URL interna: blob:http://localhost...
                map((blob) => URL.createObjectURL(blob)),
                catchError((err) => {
                    console.error(err);
                    return throwError(() => new Error('Erro!! ao carregar o blob'));
                })
            );
    }

    save(obj: any, api: string): Observable<any> {
        console.log(`${this.ROOT}${api}`);
        return this.http.post<any>(this.ROOT + api, obj, this.httpOptions);
    }

    update(obj: any, api: string): Observable<any> {
        console.log(`${this.ROOT}${api}`);
        return this.http.put<any>(this.ROOT + api, obj, this.httpOptions);
    }

    deleteByUuid(uuid: any, api: string): Observable<any> {
        const url = `${this.ROOT + api}/${uuid}`;
        return this.http.delete<any>(url, this.httpOptions);
    }

    getEstados(): Observable<any[]> {
        let url = 'https://servicodados.ibge.gov.br/api/v1/localidades/estados?orderBy=nome';
        console.log(url);
        return this.http.get<any>(url, this.httpOptions);
    }

    getCidadesByUf(uf: string): Observable<any[]> {
        let url = `https://servicodados.ibge.gov.br/api/v1/localidades/estados/${uf}/municipios?orderBy=nome`;
        console.log(url);
        return this.http.get<any>(url, this.httpOptions);
    }

    getPjByCnpj(cnpj: string): Observable<any> {
        console.log(this.RECEITA_CLOUD + cnpj);
        return this.http.get<any>(this.RECEITA_CLOUD + cnpj, this.httpOptions).pipe(map((res) => res));
    }
}
