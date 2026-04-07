import { Component } from '@angular/core';
import { MessageModule } from 'primeng/message';

@Component({
  selector: 'app-despesa-list',
  imports: [MessageModule],
  templateUrl: './despesa-list.html',
  styleUrl: './despesa-list.css',
})
export class DespesaList {}
