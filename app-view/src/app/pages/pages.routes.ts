import { Routes } from '@angular/router';
import { Documentation } from './documentation/documentation';
import { Crud } from './crud/crud';
import { Empty } from './empty/empty';
import { Despesa } from './crud/despesa/despesa';
import { DespesaList } from './crud/despesa/despesa-list';
import { DespesaForm } from './crud/despesa/despesa-form';
import { DespesaChart } from './crud/despesa/despesa-chart';
import { FormaPagamento } from './crud/forma-pagamento/forma-pagamento';
import { PessoaFisica } from './crud/pessoa-fisica/pessoa-fisica';
import { PessoaJuridica } from './crud/pessoa-juridica/pessoa-juridica';
import { Conta } from './crud/conta/conta';
import { ContaList } from './crud/conta/conta-list';
import { ContaForm } from './crud/conta/conta-form';
import { ContaChart } from './crud/conta/conta-chart';
import { TipoConta } from './crud/conta/tipo-conta';
import { Parametro } from '@/pages/aplicacao/parametro';
import {Pagamentos} from "@/pages/crud/pagamentos/pagamentos";

export default [
    { path: 'documentation', component: Documentation },
    { path: 'crud', component: Crud },
    { path: 'despesas', component: Despesa,
        children: [
            { path: 'despesa-list', component: DespesaList },
            { path: 'despesa-form', component: DespesaForm },
            { path: 'despesa-chart', component: DespesaChart },
        ]
    },
    { path: 'forma-pagamento', component: FormaPagamento},
    { path: 'pagamento', component: Pagamentos},
    { path: 'pessoa-fisica', component: PessoaFisica},
    { path: 'pessoa-juridica', component: PessoaJuridica},
    { path: 'contas', component: Conta,
        children:[
            { path: 'conta-list', component: ContaList},
            { path: 'conta-form', component: ContaForm},
            { path: 'conta-chart', component: ContaChart},
            { path: 'tipo-conta', component: TipoConta },
        ]
    },
    { path: 'parametros', component: Parametro},
    { path: 'empty', component: Empty },
    { path: '**', redirectTo: '/notfound' },

] as Routes;
