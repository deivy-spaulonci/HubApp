import { PessoaService } from '@/pages/service/pessoa-service';
import { AsyncPipe } from '@angular/common';
import { Component, EventEmitter, Input, Output } from '@angular/core';
import { AutoComplete, AutoCompleteCompleteEvent } from 'primeng/autocomplete';
import { Observable } from 'rxjs/internal/Observable';
import { FormsModule } from '@angular/forms';

@Component({
    selector: 'app-autocomplete-fornecedor',
    imports: [AutoComplete, AsyncPipe, FormsModule],
    template: `<p-autocomplete
        [(ngModel)]="value"
        [suggestions]="(content$ | async) ?? []"
        (completeMethod)="search($event)"
        (onSelect)="valueChange.emit($event)"
        optionLabel="nome"
        placeholder="Fornecedor"
        scrollHeight="400px"
        [inputStyle]="{ width: '100%' }"
        style="width: 100%;"
        required="true"
        size="large"
    /> `,
    styles: ``
})
export class AutocompleteFornecedor {
    @Input() value!: any;
    content$!: Observable<[]>;
    @Output() valueChange = new EventEmitter<any>();

    constructor(private pessoaService: PessoaService) {}

    search(event: AutoCompleteCompleteEvent) {
        this.content$ = this.pessoaService.get(event.query);
    }
}
