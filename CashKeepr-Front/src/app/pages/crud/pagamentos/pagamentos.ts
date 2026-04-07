import {Component, inject, model, OnInit, signal} from "@angular/core";
import {ImportsModule} from "@/util/imports";
import {PagamentoService} from "@/pages/service/pagamento-service";
import {MenuItem, MessageService, TreeNode} from "primeng/api";
import {DomSanitizer, SafeResourceUrl} from "@angular/platform-browser";
import {ArquivoService} from "@/pages/service/arquivo.service";


@Component({
    selector: 'app-pagamentos',
    imports: [ImportsModule],
    template: `
        <div class="grid grid-cols-12 gap-5">
            <div class="col-span-full lg:col-span-5">
                <div class="card" style="height: 100%; display: grid; grid-template-rows: auto auto 1fr;">
                    <h3><i class="pi pi-fw pi-copy" style="font-size: 1.5rem"></i> Arquivos de Pagamento</h3>
                    <div class="flex flex-wrap gap-2 mb-6">
                        <p-button icon="pi pi-plus" label="Expand all" (click)="expandAll()" />
                        <p-button icon="pi pi-minus" label="Collapse all" (click)="collapseAll()" />
                    </div>
                    <p-tree [value]="files()"
                            [scrollHeight]="'flex'"
                            [style]="{'height': '100%'}"
                            (onNodeSelect)="nodeSelect($event)"
                            selectionMode="single"
                            [(selection)]="selectedFile"
                            [virtualScroll]="true"
                            [contextMenu]="cm"
                            contextMenuSelectionMode="separate"
                            [virtualScrollItemSize]="35"/>
                    <p-contextmenu #cm [model]="items" />
                </div>
            </div>
            <div class="col-span-full lg:col-span-7">
                <div class="card">
                    <div style="flex: 1; height: 80vh; border: 1px solid #ccc;">
                        @if (urlSegura()) {
                            <iframe [src]="urlSegura()!" width="100%" height="100%" frameborder="0"></iframe>
                        } @else {
                            <div class="p-5 text-center">Selecione um arquivo para visualizar</div>
                        }
                    </div>
                </div>
            </div>
            <p-toast />
        </div>
        <p-dialog header="Renomear Arquivo" [(visible)]="displayRename" [modal]="true" [style]="{width: '550px'}">
            <div class="field flex flex-column gap-2"
                 style="display: grid; grid-template-columns: auto 1fr auto; align-items: baseline">
                <label for="nome">Novo Nome:</label>
                <div class="p-inputgroup">
                    <input
                        id="nome"
                        pInputText
                        [(ngModel)]="novoNome"
                        class="w-full"
                        placeholder="Ex: Pagamento_Marco" />
                </div>
                <span class="p-inputgroup-addon">{{ extensaoOriginal }}</span>
            </div>

            <ng-template pTemplate="footer">
                <p-button label="Cancelar" icon="pi pi-times" (click)="displayRename=false" styleClass="p-button-text"></p-button>
                <p-button label="Salvar" icon="pi pi-check" (click)="confirmarRenomear()" [disabled]="!novoNome"></p-button>
            </ng-template>
        </p-dialog>

    `,
    providers: [PagamentoService, MessageService],
    styles: ``
})

export class Pagamentos implements OnInit {

    private nodeService = inject(PagamentoService);
    files = signal<TreeNode[]>([]);
    selectedFile!: TreeNode;
    urlSegura = signal<SafeResourceUrl | null>(null);
    private sanitizer = inject(DomSanitizer);
    selectedNode: any = model<TreeNode | null>(null);
    contextMenuNode: any = model<TreeNode | null>(null);
    items!: MenuItem[];

    displayRename: boolean = false;
    novoNome: string = '';
    extensaoOriginal: string = '';

    constructor(private pagamentoService: PagamentoService,
                private arquivoService: ArquivoService,
                private messageService: MessageService) {
    }

    ngOnInit(): void {
        this.carregarArvoreArquivos();
        this.items = [
            { label: 'Renomear', icon: 'pi pi-pencil', command: () => this.openRenameModal() },
            { label: 'Toggle', icon: 'pi pi-sort', command: () => alert('teste1') }
        ];
    }

    carregarArvoreArquivos(){
        this.pagamentoService.getPastaPagamentos(this.messageService).subscribe({
            next: (data: TreeNode[]) => {
                this.files.set(data);
            }
        });
    }

    expandAll() {
        const nodes = this.files();
        const updatedFiles = this.files().map((node) => this.expandRecursive(node, true));
        this.files.set([...nodes]);
    }

    collapseAll() {
        const nodes = this.files();
        const updatedFiles = this.files().map((node) => this.expandRecursive(node, false));
        this.files.set([...nodes]);
    }

    private expandRecursive(node: TreeNode, isExpand: boolean) {
        node.expanded = isExpand;
        if (node.children) {
            node.children.forEach((childNode) => {
                this.expandRecursive(childNode, isExpand);
            });
        }
    }

    openRenameModal() {
        // Regex para separar o nome da extensão
        const nomeOriginal = this.selectedFile.label!;
        const lastDotIndex = this.selectedFile.label!.lastIndexOf('.');

        if (lastDotIndex !== -1) {
            // Sugere apenas o que vem antes do ponto
            this.novoNome = nomeOriginal.substring(0, lastDotIndex);
            this.extensaoOriginal = nomeOriginal.substring(lastDotIndex); // opcional mostrar na tela
        } else {
            this.novoNome = nomeOriginal;
            this.extensaoOriginal = '';
        }

        this.displayRename = true;
    }

    confirmarRenomear() {
        if (!this.novoNome || this.novoNome === this.selectedFile.label) {
            this.displayRename = false;
            return;
        }

        this.arquivoService.renomearArquivo(this.selectedFile.data, this.novoNome).subscribe({
            next: () => {
                this.messageService.add({severity:'success', summary:'Sucesso', detail:'Arquivo renomeado'});
                this.displayRename = false;
                this.selectedFile.label = this.novoNome;
                // Dica: Chame o método que recarrega a árvore aqui para atualizar a tela
               // this.carregarArvoreArquivos();
            },
            error: (err) => {
                console.log(err);
                this.messageService.add({severity:'error', summary:'Erro', detail: err.error || 'Erro ao renomear'});
            }
        });
    }

    nodeSelect(event: any) {
        const node: TreeNode = event.node;
        if(node.icon == 'pi pi-file') {
            this.urlSegura.set(null); // Limpa o visualizador anterior
            this.pagamentoService.getUrlVisualizacao(node.data, this.messageService).subscribe({
                next: (urlBlob) => {
                    this.urlSegura.set(this.sanitizer.bypassSecurityTrustResourceUrl(urlBlob));
                    //this.loading.set(false); // Desativa o spinner
                },
                error: (err) => {
                    // this.loading.set(false);
                    this.messageService.add({
                        severity: 'error',
                        summary: 'Erro',
                        detail: 'Não foi possível carregar o arquivo'
                    });
                }
            });
        }
    }
}
