import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { PruebaAplicationSharedModule } from 'app/shared';
import {
    ArticuloComponent,
    ArticuloDetailComponent,
    ArticuloUpdateComponent,
    ArticuloDeletePopupComponent,
    ArticuloDeleteDialogComponent,
    articuloRoute,
    articuloPopupRoute
} from './';

const ENTITY_STATES = [...articuloRoute, ...articuloPopupRoute];

@NgModule({
    imports: [PruebaAplicationSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        ArticuloComponent,
        ArticuloDetailComponent,
        ArticuloUpdateComponent,
        ArticuloDeleteDialogComponent,
        ArticuloDeletePopupComponent
    ],
    entryComponents: [ArticuloComponent, ArticuloUpdateComponent, ArticuloDeleteDialogComponent, ArticuloDeletePopupComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class PruebaAplicationArticuloModule {}
