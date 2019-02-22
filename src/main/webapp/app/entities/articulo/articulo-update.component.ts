import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { IArticulo } from 'app/shared/model/articulo.model';
import { ArticuloService } from './articulo.service';
import { IProveedor } from 'app/shared/model/proveedor.model';
import { ProveedorService } from 'app/entities/proveedor';

@Component({
    selector: 'jhi-articulo-update',
    templateUrl: './articulo-update.component.html'
})
export class ArticuloUpdateComponent implements OnInit {
    articulo: IArticulo;
    isSaving: boolean;

    proveedors: IProveedor[];

    constructor(
        protected jhiAlertService: JhiAlertService,
        protected articuloService: ArticuloService,
        protected proveedorService: ProveedorService,
        protected activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ articulo }) => {
            this.articulo = articulo;
        });
        this.proveedorService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<IProveedor[]>) => mayBeOk.ok),
                map((response: HttpResponse<IProveedor[]>) => response.body)
            )
            .subscribe((res: IProveedor[]) => (this.proveedors = res), (res: HttpErrorResponse) => this.onError(res.message));
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.articulo.id !== undefined) {
            this.subscribeToSaveResponse(this.articuloService.update(this.articulo));
        } else {
            this.subscribeToSaveResponse(this.articuloService.create(this.articulo));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<IArticulo>>) {
        result.subscribe((res: HttpResponse<IArticulo>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    protected onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    protected onSaveError() {
        this.isSaving = false;
    }

    protected onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }

    trackProveedorById(index: number, item: IProveedor) {
        return item.id;
    }
}
