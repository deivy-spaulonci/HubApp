import { PessoaJuridicaService } from '@/pages/service/pessoa-juridica-service';
import { ImportsModule } from '@/util/imports';
import { Util } from '@/util/util';
import { Component, OnInit, ViewChild } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { MessageService } from 'primeng/api';
import { Table, TableLazyLoadEvent } from 'primeng/table';
import { Observable } from 'rxjs';

@Component({
    selector: 'app-pessoa-juridica',
    imports: [ImportsModule],
    providers: [MessageService],
    template: `
        <div class="grid grid-cols-12 gap-5">
            <div class="col-span-full lg:col-span-12">
                <div class="card">
                    <h3><i class="pi pi-fw pi-building" style="font-size: 1.5rem"></i> Pessoa Jurídica</h3>
                    <form [formGroup]="pjForm" (ngSubmit)="onSubmit()">
                        <div class="card flex flex-col">
                            <div class="flex flex-wrap gap-5">
                                <div class="flex flex-col gap-2 md:w-2/12">
                                    <label for="name2">CNPJ: </label>
                                    <div style="display: grid; grid-template-columns: 1fr auto; gap:5px; ">
                                        <p-inputmask [mask]="maskCnpj" placeholder="CNPJ" [style]="{ width: '100%' }" formControlName="cnpj" [unmask]="true" [invalid]="isInvalid('cnpj')" />
                                        <p-button icon="pi pi-cloud" [raised]="true" severity="success" (onClick)="getPjByCnpj()" />
                                    </div>
                                    @if (isInvalid('cnpj')) {
                                        <p-message severity="error" size="small" variant="simple">CNPJ obrigatório!</p-message>
                                    }
                                </div>

                                <div class="flex flex-col gap-2 md:w-4/12">
                                    <label for="email2">Nome: </label>
                                    <input type="text" pInputText placeholder="Nome" formControlName="nome" [invalid]="isInvalid('nome')" />
                                    @if (isInvalid('nome')) {
                                        <p-message severity="error" size="small" variant="simple">Nome obrigatório!</p-message>
                                    }
                                </div>

                                <div class="flex flex-col gap-2 md:w-4/12">
                                    <label for="email2">Razão Social: </label>
                                    <input type="text" pInputText placeholder="Razão Social" formControlName="razaoSocial" [invalid]="isInvalid('razaoSocial')" />
                                    @if (isInvalid('razaoSocial')) {
                                        <p-message severity="error" size="small" variant="simple">Razão Social obrigatório|</p-message>
                                    }
                                </div>
                                <div class="flex flex-col gap-2 " style="text-align: right;">
                                    <span>&nbsp;</span>
                                    <p-button icon="pi pi-save" label="Salvar" [style]="{ width: '120px' }" [raised]="true" severity="success" [disabled]="pjForm.invalid" type="submit" />
                                </div>
                            </div>
                            @if (pjForm.get('nome')?.value) {
                                <h5>
                                    <i>{{ pjForm.get('cidade')?.value + ' - ' + pjForm.get('estado')?.value }}</i>
                                </h5>
                            }
                        </div>
                        
                    </form>
                </div>
            </div>
            <div class="col-span-full lg:col-span-12">
                <div class="card">
                    <div class="w-full flex items-center justify-center gap-3">
                        <input type="text" pInputText placeholder="busca..." [(ngModel)]="filtroTexto" (input)="tablePj._filter()" class="w-150" />
                        <p-button icon="pi pi-search" label="Pesquisar" [style]="{ width: '120px' }" [raised]="true" severity="success" (onClick)="tablePj._filter()" />
                    </div>
                    <p-table
                        #tablePj
                        [value]="(pessoaJuridicaResponse$ | async)?.content"
                        [loading]="!(pessoaJuridicaResponse$ | async)?.content"
                        [lazy]="true"
                        scrollHeight="flex"
                        size="small"
                        dataKey="uuid"
                        [sortField]="sortField"
                        [scrollable]="true"
                        [totalRecords]="(pessoaJuridicaResponse$ | async)?.totalElements"
                        [sortOrder]="1"
                        [paginator]="true"
                        paginatorPosition="top"
                        [rows]="(pessoaJuridicaResponse$ | async)?.size"
                        [rowsPerPageOptions]="[10, 20, 30]"
                        (onLazyLoad)="loadData($event)"
                        selectionMode="single"
                        [(selection)]="selected"
                        currentPageReportTemplate="Showing {first} to {last} of {totalRecords} entries"
                        showGridlines
                        stripedRows
                    >
                        <ng-template #header>
                            <tr>
                                <th pSortableColumn="nome">Nome<p-sortIcon field="nome" /></th>
                                <th pSortableColumn="razaoSocial">Razao Social<p-sortIcon field="razaoSocial" /></th>
                                <th pSortableColumn="cnpj">CNPJ<p-sortIcon field="cnpj" /></th>
                                <th>Cidade</th>
                            </tr>
                        </ng-template>
                        <ng-template #body let-pj>
                            <tr [pSelectableRow]="pj">
                                <td>{{ pj.nome }}</td>
                                <td>{{ pj.razaoSocial }}</td>
                                <td>{{ pj.cnpj }}</td>
                                <td>{{ pj.codigoIbge | ibgeCidade | async }}</td>
                            </tr>
                        </ng-template>
                    </p-table>
                    <p-toast />
                </div>
            </div>
        </div>
    `,
    styles: ``
})
export class PessoaJuridica implements OnInit {
    pessoaJuridicaResponse$!: Observable<any>;
    sortField: string = 'nome';
    filtroTexto!: string;
    selected!: any;
    @ViewChild('tablePj') tabela!: Table;

    maskCnpj: string = '99.999.999/9999-99';
    pjForm: FormGroup;

    constructor(
        private pessoaJuridicaService: PessoaJuridicaService,
        private fb: FormBuilder,
        private messageService: MessageService
    ) {
        this.pjForm = this.fb.group({
            nome: ['', Validators.required],
            razaoSocial: ['', Validators.required],
            cnpj: ['', Validators.required],
            cidade: [''],
            estado: [''],
            codigoIbge: ['', Validators.required]
        });
    }

    ngOnInit(): void {}

    loadPessoas(url: string) {
        this.pessoaJuridicaResponse$ = this.pessoaJuridicaService.getPessoaJuridicaResponse(url, this.messageService);
    }

    loadData(event: TableLazyLoadEvent) {
        const pageSize = event.rows ?? 20;
        const sortField = event.sortField ?? 'nome';
        const sortOrder = event.sortOrder === 1 ? 'asc' : 'desc';
        // garante que page nunca vira NaN
        const page = event.first ? event.first / pageSize : 0;
        const filtro = this.filtroTexto ?? '';
        const url = `?busca=${filtro}&page=${page}&size=${pageSize}&sort=${sortField},${sortOrder}`;
        this.loadPessoas(url);
    }

    getPjByCnpj() {
        //'27048672000123'
        let cnpj = this.pjForm.get('cnpj')?.value;
        let cnpjLimpo = cnpj.replace(/[^0-9]+/g, '');

        this.pessoaJuridicaService
            .getService()
            .getPjByCnpj(cnpjLimpo)
            .subscribe({
                next: (res) => {
                    this.pjForm.patchValue({
                        nome: res.nome ? Util.capitalizeSentence(res.nome) : Util.capitalizeSentence(res.razao_social),
                        razaoSocial: Util.capitalizeSentence(res.razao_social),
                        estado: res.estabelecimento.estado.sigla,
                        cidade: res.estabelecimento.cidade.nome,
                        codigoIbge: res.estabelecimento.cidade.ibge_id
                    });
                },
                error: (err) => Util.showMsgErro(this.messageService, 'Erro ao consultar CNPJ')
            });
    }

    onSubmit() {
        if (this.pjForm.valid) {
          const dados = this.pjForm.getRawValue();
          this.pessoaJuridicaService.save(dados).subscribe({
            next: (res) => {
              this.pjForm.reset();
              Util.showMsgSuccess(this.messageService, 'Sucesso!');
            },
            error: (err) => {
              Util.showMsgErro(this.messageService, 'Erro ao salvar pessoa juridica');
            }
          })
        }
    }

    isInvalid(controlName: string) {
        const control = this.pjForm.get(controlName);
        return control?.invalid && (control.touched || this.pjForm);
    }
}
