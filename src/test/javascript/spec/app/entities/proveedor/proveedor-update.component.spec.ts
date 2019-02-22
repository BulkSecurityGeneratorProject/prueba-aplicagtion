/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { PruebaAplicationTestModule } from '../../../test.module';
import { ProveedorUpdateComponent } from 'app/entities/proveedor/proveedor-update.component';
import { ProveedorService } from 'app/entities/proveedor/proveedor.service';
import { Proveedor } from 'app/shared/model/proveedor.model';

describe('Component Tests', () => {
    describe('Proveedor Management Update Component', () => {
        let comp: ProveedorUpdateComponent;
        let fixture: ComponentFixture<ProveedorUpdateComponent>;
        let service: ProveedorService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [PruebaAplicationTestModule],
                declarations: [ProveedorUpdateComponent]
            })
                .overrideTemplate(ProveedorUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(ProveedorUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ProveedorService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new Proveedor(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.proveedor = entity;
                    // WHEN
                    comp.save();
                    tick(); // simulate async

                    // THEN
                    expect(service.update).toHaveBeenCalledWith(entity);
                    expect(comp.isSaving).toEqual(false);
                })
            );

            it(
                'Should call create service on save for new entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new Proveedor();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.proveedor = entity;
                    // WHEN
                    comp.save();
                    tick(); // simulate async

                    // THEN
                    expect(service.create).toHaveBeenCalledWith(entity);
                    expect(comp.isSaving).toEqual(false);
                })
            );
        });
    });
});
