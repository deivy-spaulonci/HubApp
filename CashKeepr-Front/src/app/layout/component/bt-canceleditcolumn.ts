
import { Component, EventEmitter, Output } from '@angular/core';
import { ButtonModule } from 'primeng/button';
import { RippleModule } from 'primeng/ripple';
import { TableModule } from 'primeng/table';

@Component({
  selector: 'app-bt-canceleditcolumn',
  imports: [ButtonModule, RippleModule, TableModule],
  template: `<button 
    pButton 
    pRipple 
    type="button" 
    pCancelEditableRow 
    icon="pi pi-times" 
    size="small" 
    text 
    severity="danger" 
    [style]="{margin:0, padding:0}" 
    (click)="onClick()">
  </button>
  `,
  styles: ``,
})
export class BtCancelEditColumn {
  @Output() acao = new EventEmitter<void>();
  
  onClick(): void {
    this.acao.emit();
  }

}
