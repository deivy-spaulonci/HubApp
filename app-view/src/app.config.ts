import {provideHttpClient, withFetch} from '@angular/common/http';
import {
    ApplicationConfig,
    DEFAULT_CURRENCY_CODE,
    LOCALE_ID,
    provideBrowserGlobalErrorListeners,
    provideZoneChangeDetection
} from '@angular/core';
import {provideAnimationsAsync} from '@angular/platform-browser/animations/async';
import {provideRouter, withEnabledBlockingInitialNavigation, withInMemoryScrolling} from '@angular/router';
import Nora from '@primeuix/themes/nora';
import {providePrimeNG} from 'primeng/config';
import {appRoutes} from './app.routes';
import localePt from '@angular/common/locales/pt';
import {registerLocaleData} from '@angular/common';

registerLocaleData(localePt);

export const appConfig: ApplicationConfig = {
    providers: [
        {
            provide: LOCALE_ID,
            useValue: 'pt-BR'
        },
        {
            provide: DEFAULT_CURRENCY_CODE,
            useValue: 'BRL'
        },
        provideRouter(appRoutes, withInMemoryScrolling({
            anchorScrolling: 'enabled',
            scrollPositionRestoration: 'enabled'
        }), withEnabledBlockingInitialNavigation()),
        provideHttpClient(withFetch()),
        provideAnimationsAsync(),
        providePrimeNG({
            theme: {preset: Nora, options: {darkModeSelector: '.app-dark'}},
            translation: {
                dayNames: ["Domingo", "Segunda", "Terça", "Quarta", "Quinta", "Sexta", "Sábado"],
                dayNamesShort: ["Dom", "Seg", "Ter", "Qua", "Qui", "Sex", "Sáb"],
                dayNamesMin: ["D", "S", "T", "Q", "Q", "S", "S"],
                monthNames: ["Janeiro", "Fevereiro", "Março", "Abril", "Maio", "Junho", "Julho", "Agosto", "Setembro", "Outubro", "Novembro", "Dezembro"],
                monthNamesShort: ["Jan", "Fev", "Mar", "Abr", "Mai", "Jun", "Jul", "Ago", "Set", "Out", "Nov", "Dez"],
                today: 'Hoje',
                clear: 'Limpar',
                dateFormat: 'dd/mm/yy'
            }
        })

    ]
};
