// Import PrimeNG modules
import { AutoFocusModule } from 'primeng/autofocus';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { NgModule } from '@angular/core';
import { OverlayBadgeModule } from 'primeng/overlaybadge';
import { TabsModule } from 'primeng/tabs';
import { AvatarModule } from 'primeng/avatar';
import { AvatarGroupModule } from 'primeng/avatargroup';
import { AnimateOnScrollModule } from 'primeng/animateonscroll';
import { AccordionModule } from 'primeng/accordion';
import { AutoCompleteModule } from 'primeng/autocomplete';
import { BadgeModule } from 'primeng/badge';
import { BreadcrumbModule } from 'primeng/breadcrumb';
import { BlockUIModule } from 'primeng/blockui';
import { ButtonModule } from 'primeng/button';
import { DatePickerModule } from 'primeng/datepicker';
import { CarouselModule } from 'primeng/carousel';
import { CascadeSelectModule } from 'primeng/cascadeselect';
import { ChartModule } from 'primeng/chart';
import { CheckboxModule } from 'primeng/checkbox';
import { ChipModule } from 'primeng/chip';
import { ColorPickerModule } from 'primeng/colorpicker';
import { ConfirmDialogModule } from 'primeng/confirmdialog';
import { ConfirmPopupModule } from 'primeng/confirmpopup';
import { ContextMenuModule } from 'primeng/contextmenu';
import { DataViewModule } from 'primeng/dataview';
import { DialogModule } from 'primeng/dialog';
import { DividerModule } from 'primeng/divider';
import { DockModule } from 'primeng/dock';
import { DragDropModule } from 'primeng/dragdrop';
import { SelectModule } from 'primeng/select';
import { DynamicDialogModule } from 'primeng/dynamicdialog';
import { FieldsetModule } from 'primeng/fieldset';
import { FileUploadModule } from 'primeng/fileupload';
import { FocusTrapModule } from 'primeng/focustrap';
import { GalleriaModule } from 'primeng/galleria';
import { IftaLabelModule } from 'primeng/iftalabel';
import { InplaceModule } from 'primeng/inplace';
import { InputMaskModule } from 'primeng/inputmask';
import { InputTextModule } from 'primeng/inputtext';
import { TextareaModule } from 'primeng/textarea';
import { InputNumberModule } from 'primeng/inputnumber';
import { InputGroupModule } from 'primeng/inputgroup';
import { InputGroupAddonModule } from 'primeng/inputgroupaddon';
import { InputOtpModule } from 'primeng/inputotp';
import { ImageModule } from 'primeng/image';
import { ImageCompareModule } from 'primeng/imagecompare';
import { KnobModule } from 'primeng/knob';
import { ListboxModule } from 'primeng/listbox';
import { MegaMenuModule } from 'primeng/megamenu';
import { MenuModule } from 'primeng/menu';
import { MenubarModule } from 'primeng/menubar';
import { MessageModule } from 'primeng/message';
import { MultiSelectModule } from 'primeng/multiselect';
import { MeterGroupModule } from 'primeng/metergroup';
import { OrganizationChartModule } from 'primeng/organizationchart';
import { OrderListModule } from 'primeng/orderlist';
import { PaginatorModule } from 'primeng/paginator';
import { PanelModule } from 'primeng/panel';
import { PanelMenuModule } from 'primeng/panelmenu';
import { PasswordModule } from 'primeng/password';
import { PickListModule } from 'primeng/picklist';
import { ProgressSpinnerModule } from 'primeng/progressspinner';
import { ProgressBarModule } from 'primeng/progressbar';
import { RadioButtonModule } from 'primeng/radiobutton';
import { RatingModule } from 'primeng/rating';
import { SelectButtonModule } from 'primeng/selectbutton';
import { ScrollerModule } from 'primeng/scroller';
import { ScrollPanelModule } from 'primeng/scrollpanel';
import { ScrollTopModule } from 'primeng/scrolltop';
import { SkeletonModule } from 'primeng/skeleton';
import { SliderModule } from 'primeng/slider';
import { SpeedDialModule } from 'primeng/speeddial';
import { SplitterModule } from 'primeng/splitter';
import { StepperModule } from 'primeng/stepper';
import { SplitButtonModule } from 'primeng/splitbutton';
import { StepsModule } from 'primeng/steps';
import { TableModule } from 'primeng/table';
import { TagModule } from 'primeng/tag';
import { TerminalModule } from 'primeng/terminal';
import { TieredMenuModule } from 'primeng/tieredmenu';
import { TimelineModule } from 'primeng/timeline';
import { ToastModule } from 'primeng/toast';
import { ToggleButtonModule } from 'primeng/togglebutton';
import { ToggleSwitchModule } from 'primeng/toggleswitch';
import { ToolbarModule } from 'primeng/toolbar';
import { TooltipModule } from 'primeng/tooltip';
import { TreeModule } from 'primeng/tree';
import { TreeSelectModule } from 'primeng/treeselect';
import { TreeTableModule } from 'primeng/treetable';
import { CardModule } from 'primeng/card';
import { RippleModule } from 'primeng/ripple';
import { StyleClassModule } from 'primeng/styleclass';
import { FloatLabelModule } from 'primeng/floatlabel';
import { IconFieldModule } from 'primeng/iconfield';
import { InputIconModule } from 'primeng/inputicon';
import { DrawerModule } from 'primeng/drawer';
import { KeyFilterModule } from 'primeng/keyfilter';
import { RouterLink, RouterOutlet } from '@angular/router';
import { Popover, PopoverModule } from 'primeng/popover';
import { InputMoeda } from '@/layout/component/inputs/input-moeda';
import { AutocompleteFornecedor } from '@/layout/component/inputs/autocomplete-fornecedor';
import { AutocompleteFornecedorForm } from '@/layout/component/forms/autocomplete-fornecedor-form';
import { SelectTipoDespesa } from '@/layout/component/inputs/select-tipo-despesa';
import { SelectTipoDespesaForm } from '@/layout/component/forms/select-tipo-despesa-form';
import { InputData } from '@/layout/component/inputs/input-data';
import { SelectFormaPagamento } from '@/layout/component/inputs/select-forma-pagamento';
import { BtInitEditColumn } from '@/layout/component/bt-initeditcolumn';
import { BtSaveEditColumn } from '@/layout/component/bt-saveeditcolumn';
import { BtCancelEditColumn } from '@/layout/component/bt-canceleditcolumn';
import { InputDataForm } from '@/layout/component/forms/input-data-form';
import { SelectFormaPagamentoForm } from '@/layout/component/forms/select-forma-pagamento-form';
import { InputMoedaForm } from '@/layout/component/forms/input-moeda-form';
import { IbgeCidadePipe } from '@/pipe/ibge-cidade-pipe';
import { SelectTipoConta } from '@/layout/component/inputs/select-tipo-conta';
import { SelectTipoContaForm } from '@/layout/component/forms/select-tipo-conta-form';

@NgModule({
  imports: [
    SelectTipoContaForm,
    SelectTipoConta,
    IbgeCidadePipe,
    InputMoedaForm,
    SelectFormaPagamentoForm,
    InputDataForm,
    InputMoeda,
    AutocompleteFornecedor,
    SelectTipoDespesa,
    SelectTipoDespesaForm,
    AutocompleteFornecedorForm,
    InputData,
    SelectFormaPagamento,
    BtInitEditColumn,
    BtSaveEditColumn,
    BtCancelEditColumn,
    AvatarModule,
    KeyFilterModule,
    AvatarGroupModule,
    AnimateOnScrollModule,
    TabsModule,
    FormsModule,
    RouterLink,
    RouterOutlet,
    ReactiveFormsModule,
    AccordionModule,
    AutoCompleteModule,
    BadgeModule,
    BreadcrumbModule,
    BlockUIModule,
    ButtonModule,
    DatePickerModule,
    CarouselModule,
    CascadeSelectModule,
    ChartModule,
    CheckboxModule,
    ChipModule,
    ColorPickerModule,
    ConfirmDialogModule,
    ConfirmPopupModule,
    ContextMenuModule,
    DataViewModule,
    DialogModule,
    DividerModule,
    DrawerModule,
    DockModule,
    DragDropModule,
    SelectModule,
    DynamicDialogModule,
    FieldsetModule,
    FileUploadModule,
    FocusTrapModule,
    GalleriaModule,
    IftaLabelModule,
    InplaceModule,
    InputMaskModule,
    InputTextModule,
    TextareaModule,
    InputNumberModule,
    InputGroupModule,
    InputGroupAddonModule,
    InputOtpModule,
    ImageModule,
    ImageCompareModule,
    KnobModule,
    ListboxModule,
    MegaMenuModule,
    MenuModule,
    MenubarModule,
    MessageModule,
    MultiSelectModule,
    MeterGroupModule,
    OrganizationChartModule,
    OrderListModule,
    PaginatorModule,
    PanelModule,
    PanelMenuModule,
    PasswordModule,
    PickListModule,
    ProgressSpinnerModule,
    ProgressBarModule,
    RadioButtonModule,
    RatingModule,
    SelectButtonModule,
    ScrollerModule,
    ScrollPanelModule,
    ScrollTopModule,
    SkeletonModule,
    SliderModule,
    SpeedDialModule,
    SplitterModule,
    StepperModule,
    SplitButtonModule,
    StepsModule,
    TableModule,
    TagModule,
    TerminalModule,
    TieredMenuModule,
    TimelineModule,
    ToastModule,
    ToggleButtonModule,
    ToggleSwitchModule,
    ToolbarModule,
    TooltipModule,
    TreeModule,
    TreeSelectModule,
    TreeTableModule,
    CardModule,
    RippleModule,
    StyleClassModule,
    FloatLabelModule,
    IconFieldModule,
    InputIconModule,
    AutoFocusModule,
    OverlayBadgeModule,
    Popover,
    PopoverModule
  ],
  exports: [
    SelectTipoContaForm,
    SelectTipoConta,
    IbgeCidadePipe,
    InputMoedaForm,
    SelectFormaPagamentoForm,
    InputDataForm,
    InputMoeda,
    AutocompleteFornecedor,
    SelectTipoDespesa,
    SelectTipoDespesaForm,
    AutocompleteFornecedorForm,
    InputData,
    SelectFormaPagamento,
    BtInitEditColumn,
    BtSaveEditColumn,
    BtCancelEditColumn,
    TabsModule,
    AvatarModule,
    AvatarGroupModule,
    AnimateOnScrollModule,
    FormsModule,
    RouterLink,
    RouterOutlet,
    ReactiveFormsModule,
    AccordionModule,
    AutoCompleteModule,
    BadgeModule,
    BreadcrumbModule,
    BlockUIModule,
    ButtonModule,
    DatePickerModule,
    CarouselModule,
    KeyFilterModule,
    CascadeSelectModule,
    ChartModule,
    CheckboxModule,
    ChipModule,
    ColorPickerModule,
    ConfirmDialogModule,
    ConfirmPopupModule,
    ContextMenuModule,
    DataViewModule,
    DialogModule,
    DividerModule,
    DrawerModule,
    DockModule,
    DragDropModule,
    SelectModule,
    DynamicDialogModule,
    FieldsetModule,
    FileUploadModule,
    FocusTrapModule,
    GalleriaModule,
    IftaLabelModule,
    InplaceModule,
    InputMaskModule,
    InputTextModule,
    TextareaModule,
    InputNumberModule,
    InputGroupModule,
    InputGroupAddonModule,
    InputOtpModule,
    ImageModule,
    ImageCompareModule,
    KnobModule,
    ListboxModule,
    MegaMenuModule,
    MenuModule,
    MenubarModule,
    MessageModule,
    MultiSelectModule,
    MeterGroupModule,
    OrganizationChartModule,
    OrderListModule,
    PaginatorModule,
    PanelModule,
    PanelMenuModule,
    PasswordModule,
    PickListModule,
    ProgressSpinnerModule,
    ProgressBarModule,
    RadioButtonModule,
    RatingModule,
    SelectButtonModule,
    ScrollerModule,
    ScrollPanelModule,
    ScrollTopModule,
    SkeletonModule,
    SliderModule,
    SpeedDialModule,
    SplitterModule,
    StepperModule,
    SplitButtonModule,
    StepsModule,
    TableModule,
    TagModule,
    TerminalModule,
    TieredMenuModule,
    TimelineModule,
    ToastModule,
    ToggleButtonModule,
    ToggleSwitchModule,
    ToolbarModule,
    TooltipModule,
    TreeModule,
    TreeSelectModule,
    TreeTableModule,
    CardModule,
    RippleModule,
    StyleClassModule,
    FloatLabelModule,
    IconFieldModule,
    InputIconModule,
    AutoFocusModule,
    OverlayBadgeModule,
    Popover,
    PopoverModule
  ],
  providers: []
})
export class ImportsModule { }

