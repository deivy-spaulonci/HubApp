import { Component } from '@angular/core';
import { Button } from 'primeng/button';
import { RouterOutlet } from '@angular/router';
import { TabsModule } from 'primeng/tabs';

@Component({
  selector: 'app-despesa-view',
  imports: [TabsModule],
  templateUrl: './despesa-view.html',
  styleUrl: './despesa-view.css',
})
export class DespesaView {}
