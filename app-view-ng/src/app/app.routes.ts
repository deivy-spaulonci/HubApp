import { Routes } from '@angular/router';
import { Dashboard } from './view/dashboard/dashboard';
import { DespesaList } from './view/despesa-view/despesa-list/despesa-list';

export const routes: Routes = [
  {
    path: '',
    component: Dashboard,
  },
  {
    path: 'dashboard',
    component: Dashboard,
  },
  {
    path: 'despesas',
    component: DespesaList
  }
];
