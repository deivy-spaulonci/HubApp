import { FormaPagamentoService } from '@/pages/service/forma-pagamento-service';
import { AsyncPipe } from '@angular/common';
import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { MessageService } from 'primeng/api';
import { SelectModule } from 'primeng/select';
import { firstValueFrom, map } from 'rxjs';
import { Observable } from 'rxjs/internal/Observable';

@Component({
    selector: 'app-select-forma-pagamento',
    imports: [SelectModule, AsyncPipe, FormsModule],
    template: `<p-select
        [options]="(content$ | async) ?? []"
        [(ngModel)]="value"
        appendTo="body"
        (onChange)="valueChange.emit($event)"
        style="width: 100%;"
        [showClear]="showC"
        dataKey="uuid"
        selectOnFocus="true"
        placeholder="Forma de Pagamento"
        size="large"
        [scrollHeight]="scrHeight"
    >
        <ng-template #selectedItem let-formaSelected>
            @if (formaSelected) {
                <span>{{ formaSelected.modalidade.descricao + ' ' + (formaSelected.instituicao ?? '') }}</span>
            }
        </ng-template>
        <ng-template let-forma #item>
            <span>{{ forma.modalidade.descricao + ' ' + (forma.instituicao ?? '') }}</span>
        </ng-template>
    </p-select> `,
    styles: ``
})
export class SelectFormaPagamento implements OnInit {
    @Input() value!: any;
    @Input() showC: boolean = false;
    content$!: Observable<any[]>;
    @Output() valueChange = new EventEmitter<any>();
    @Input() scrHeight: string = '300px';

    constructor(
        private formaPagamentoService: FormaPagamentoService,
        private messageService: MessageService
    ) {}

    ngOnInit(): void {
        this.content$ = this.formaPagamentoService.getFormasPagamento(this.messageService);
    }

    async setSelected(uuid:String): Promise<any>{
        const lista = await firstValueFrom(this.content$);
        const item = lista.find(opt => opt.uuid === uuid);
        this.value = item;
        return item;
    }
}
