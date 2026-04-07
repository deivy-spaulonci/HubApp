
import { Component, EventEmitter, Output } from '@angular/core';
import { ButtonModule } from 'primeng/button';
import { RippleModule } from 'primeng/ripple';
import { TableModule } from 'primeng/table';

@Component({
  selector: 'app-bt-saveeditcolumn',
  imports: [ButtonModule, RippleModule, TableModule],
  template: `<button 
    pButton 
    pRipple 
    type="button" 
    pSaveEditableRow 
    icon="pi pi-check" 
    size="small" 
    text 
    severity="secondary" 
    [style]="{margin:0, padding:0}" 
    (click)="onClick()">
  </button>
  `,
  styles: ``,
})
export class BtSaveEditColumn {
  @Output() acao = new EventEmitter<void>();
  
  onClick(): void {
    this.acao.emit();
  }

}
