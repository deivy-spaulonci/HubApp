import { Component, OnInit, signal } from '@angular/core';
import { RouterLink, RouterOutlet } from '@angular/router';
import { ButtonModule } from 'primeng/button';
import { PanelMenuModule } from 'primeng/panelmenu';
import { MenuItem, MessageService } from 'primeng/api';
import { MenuModule } from 'primeng/menu';
import { RippleModule } from 'primeng/ripple';
@Component({
  selector: 'app-root',
  imports: [RouterOutlet, ButtonModule, PanelMenuModule, MenuModule, RippleModule, RouterLink],
  templateUrl: './app.html',
  styleUrl: './app.css',
  providers: [MessageService],
})
export class App implements OnInit {
  items!: MenuItem[];

  ngOnInit() {
    this.items = [
      {
        label: 'Home',
        items: [{ label: 'Dashboard', icon: 'pi pi-gauge', routerLink: '/dashboard' }],
      },
      {
        label: 'Financeiro',
        items: [
          { label: 'Despesas', icon: 'pi pi-dollar', routerLink: '/despesas' },
          { label: 'Contas', icon: 'pi pi-barcode' },
          { label: 'Fonrecedores', icon: 'pi pi-box' },
        ],
      },
      {
        label: 'Documentos',
        items: [{ label: 'Arquivos', icon: 'pi pi-file-o' }],
      },
      {
        label: 'Sistema',
        items: [
          { label: 'Senha', icon: 'pi pi-key' },
          { label: 'Sair', icon: 'pi pi-sign-out' },
        ],
      },
    ];
  }
}
