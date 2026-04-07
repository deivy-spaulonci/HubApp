import { TipoDespesaService } from '@/pages/service/tipo-despesa-service';
import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { Observable } from 'rxjs/internal/Observable';
import { SelectModule } from 'primeng/select';
import { AsyncPipe } from '@angular/common';
import { FormsModule } from '@angular/forms';

@Component({
    selector: 'app-select-tipo-despesa',
    standalone: true,
    imports: [SelectModule, AsyncPipe, FormsModule],
    template: `<p-select [options]="(content$ | async) ?? []"
    optionLabel="nome"
    filter="true"
    [(ngModel)]="value"
    (onChange)="valueChange.emit($event)"
    style="width: 100%"
    placeholder="Tipo de Despesa"
    [showClear]="showC"
                         size="large"
    scrollHeight="400px" /> `,
    styles: ``
})
export class SelectTipoDespesa implements OnInit {
    @Input() showC: boolean = false;
    @Input() value!: any;
    content$!: Observable<[]>;
    @Output() valueChange = new EventEmitter<any>();

    constructor(private tipoDespesaService: TipoDespesaService) {}

    ngOnInit(): void {
        this.content$ = this.tipoDespesaService.getTipos();
    }
}
