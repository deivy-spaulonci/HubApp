import { Component, EventEmitter, Output } from '@angular/core';
import { ButtonModule } from 'primeng/button';
import { RippleModule } from 'primeng/ripple';
import { TableModule } from 'primeng/table';

@Component({
  selector: 'app-bt-initeditcolumn',
  
  imports: [ButtonModule, RippleModule, TableModule],
  template: `<button 
    pButton 
    pRipple 
    type="button" 
    pInitEditableRow 
    icon="pi pi-pencil" 
    size="small" 
    text     
    severity="secondary" 
    [style]="{margin:0, padding:0}" 
    (click)="onClick()">
  </button>
  `,
  styles: ``,
})
export class BtInitEditColumn {
  @Output() acao = new EventEmitter<void>();
  
  onClick(): void {
    this.acao.emit();
  }

}
