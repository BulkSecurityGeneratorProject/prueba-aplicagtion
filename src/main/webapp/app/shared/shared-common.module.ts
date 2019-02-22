import { NgModule } from '@angular/core';

import { PruebaAplicationSharedLibsModule, JhiAlertComponent, JhiAlertErrorComponent } from './';

@NgModule({
    imports: [PruebaAplicationSharedLibsModule],
    declarations: [JhiAlertComponent, JhiAlertErrorComponent],
    exports: [PruebaAplicationSharedLibsModule, JhiAlertComponent, JhiAlertErrorComponent]
})
export class PruebaAplicationSharedCommonModule {}
