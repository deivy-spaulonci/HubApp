import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { Observable } from 'rxjs/internal/Observable';
import { SelectModule } from 'primeng/select';
import { AsyncPipe } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { TipoContaService } from '@/pages/service/tipo-conta-service';

@Component({
    selector: 'app-select-tipo-conta',
    standalone: true,
    imports: [SelectModule, AsyncPipe, FormsModule],
    template: `<p-select [options]="(content$ | async) ?? []"
    optionLabel="nome"
    filter="true"
    [(ngModel)]="value"
    (onChange)="valueChange.emit($event)"
    style="width: 100%"
    placeholder="Tipo de Conta"
    [showClear]="showC"
                         size="large"
    scrollHeight="500px" /> `,
    styles: ``
})
export class SelectTipoConta implements OnInit {
    @Input() showC: boolean = false;
    @Input() value!: any;
    content$!: Observable<[]>;
    @Output() valueChange = new EventEmitter<any>();

    constructor(private tipoContaService: TipoContaService) {}

    ngOnInit(): void {
        this.content$ = this.tipoContaService.getTipos();
    }
}
