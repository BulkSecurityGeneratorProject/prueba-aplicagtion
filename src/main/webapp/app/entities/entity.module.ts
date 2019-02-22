import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
    imports: [
        RouterModule.forChild([
            {
                path: 'proveedor',
                loadChildren: './proveedor/proveedor.module#PruebaAplicationProveedorModule'
            },
            {
                path: 'articulo',
                loadChildren: './articulo/articulo.module#PruebaAplicationArticuloModule'
            }
            /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
        ])
    ],
    declarations: [],
    entryComponents: [],
    providers: [],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class PruebaAplicationEntityModule {}
