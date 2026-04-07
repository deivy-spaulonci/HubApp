import { PessoaService } from '@/pages/service/pessoa-service';
import { AsyncPipe } from '@angular/common';
import { Component, EventEmitter, Input, Output } from '@angular/core';
import { AutoComplete, AutoCompleteCompleteEvent } from 'primeng/autocomplete';
import { Observable } from 'rxjs/internal/Observable';
import { FormGroup, FormsModule, ReactiveFormsModule } from '@angular/forms';
import { Message } from 'primeng/message';

@Component({
    selector: 'app-autocomplete-fornecedor-form',
    imports: [AutoComplete, AsyncPipe, FormsModule, ReactiveFormsModule, Message],
    template: `
        <div [formGroup]="form">
            <p-autocomplete
                [suggestions]="(content$ | async) ?? []"
                (completeMethod)="search($event)"
                optionLabel="nome"
                size="large"
                placeholder="Fornecedor"
                formControlName="fornecedor"
                scrollHeight="400px"
                [inputStyle]="{ width: '100%' }"
                style="width: 100%;"
            />
        </div>
        @if (isInvalid() && req) {
            <p-message severity="error" size="small" variant="simple">Fornecedor obrigatório!</p-message>
        }
    `,
    styles: ``
})
export class AutocompleteFornecedorForm {
    @Input({ required: true }) form!: FormGroup;
    content$!: Observable<[]>;
    @Input() req: boolean = true;

    constructor(private pessoaService: PessoaService) {}

    search(event: AutoCompleteCompleteEvent) {
        this.content$ = this.pessoaService.get(event.query);
    }

    isInvalid() {
        const control = this.form.get('fornecedor');
        return control?.invalid && (control.touched || this.form);
    }
}
