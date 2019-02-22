import { IArticulo } from 'app/shared/model/articulo.model';

export interface IProveedor {
    id?: number;
    idProveedor?: string;
    nombre?: string;
    direccion?: string;
    idProveedors?: IArticulo[];
}

export class Proveedor implements IProveedor {
    constructor(
        public id?: number,
        public idProveedor?: string,
        public nombre?: string,
        public direccion?: string,
        public idProveedors?: IArticulo[]
    ) {}
}
