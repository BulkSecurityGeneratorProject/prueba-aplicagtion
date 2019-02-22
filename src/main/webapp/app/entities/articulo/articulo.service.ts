import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IArticulo } from 'app/shared/model/articulo.model';

type EntityResponseType = HttpResponse<IArticulo>;
type EntityArrayResponseType = HttpResponse<IArticulo[]>;

@Injectable({ providedIn: 'root' })
export class ArticuloService {
    public resourceUrl = SERVER_API_URL + 'api/articulos';

    constructor(protected http: HttpClient) {}

    create(articulo: IArticulo): Observable<EntityResponseType> {
        return this.http.post<IArticulo>(this.resourceUrl, articulo, { observe: 'response' });
    }

    update(articulo: IArticulo): Observable<EntityResponseType> {
        return this.http.put<IArticulo>(this.resourceUrl, articulo, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<IArticulo>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IArticulo[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }
}
