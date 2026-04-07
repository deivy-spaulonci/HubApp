import { Component, OnInit } from '@angular/core';
import { ImportsModule } from '@/util/imports';
import { MessageService } from 'primeng/api';
import { ParametroService } from '@/pages/service/parametro-service';
import { Observable } from 'rxjs';

@Component({
    selector: 'app-parametro',
    standalone: true,
    imports: [ImportsModule],
    providers: [MessageService],
    template: `
        <div class="grid grid-cols-12 gap-5">
            <div class="col-span-full lg:col-span-6">
                <div class="card">
                    <h3><i class="pi pi-fw pi-cog" style="font-size: 1.5rem"></i> Parametros</h3>

                    <p-toast />
                </div>
            </div>
            <div class="col-span-full lg:col-span-6">
                <div class="card"></div>
            </div>
        </div>
    `,
    styles: ``
})
export class Parametro implements OnInit {
    parametros$!: Observable<any[]>;

    constructor(
        private messageService: MessageService,
        private parametroService: ParametroService
    ) {}

    ngOnInit(): void {
        this.parametros$ = this.parametroService.all();
    }
}
